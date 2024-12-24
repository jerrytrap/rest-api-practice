package com.example.rest.domain.report;

import com.example.rest.domain.student.Student;
import com.example.rest.domain.student.StudentService;
import com.example.rest.global.RsData;
import com.example.rest.global.ServiceException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;
    private final StudentService studentService;

    record ReportReqBody(
            @NotBlank
            @Length(min = 5)
            String title,
            @NotBlank
            @Length(min = 5)
            String content,
            @NotNull
            Long authorId,
            @NotNull
            @Length(min = 4)
            String password
    ) {
    }

    record ReportResBody(
        ReportDto reportDto,
        long totalCount
    ) {
    }

    @PostMapping("/create")
    public RsData<ReportDto> create(@RequestBody @Valid ReportReqBody reportReqBody) {
        Student author = studentService.findStudentById(reportReqBody.authorId).get();

        if (!author.getPassword().equals(reportReqBody.password)) {
            throw new ServiceException("401-1", "비밀번호가 일치하지 않습니다.");
        }

        Report report = reportService.create(author, reportReqBody.title, reportReqBody.content);
        return new RsData<>(
                "201-1",
                "보고서: %s".formatted(report.getTitle()),
                new ReportDto(report)
        );
    }

    @PutMapping("/{id}")
    @Transactional
    public RsData<ReportDto> modify(@RequestBody @Valid ReportReqBody reportReqBody, @PathVariable long id) {
        Student author = studentService.findStudentById(reportReqBody.authorId).get();

        if (!author.getPassword().equals(reportReqBody.password)) {
            throw new ServiceException("401-1", "비밀번호가 일치하지 않습니다.");
        }

        Report report = reportService.findById(id);

        if (!report.getAuthor().equals(author)) {
            throw new ServiceException("403-1", "작성자만 글을 수정할 권한이 있습니다.");
        }

        reportService.modify(report, reportReqBody.title, reportReqBody.content);

        return new RsData<>(
                "200-1",
                "%d번 글이 수정되었습니다.".formatted(id),
                new ReportDto(report)
        );
    }
}
