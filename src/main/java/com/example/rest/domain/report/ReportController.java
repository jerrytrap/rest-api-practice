package com.example.rest.domain.report;

import com.example.rest.domain.student.Student;
import com.example.rest.global.Rq;
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
    private final Rq rq;

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
    public RsData<ReportDto> create(
            @RequestBody @Valid ReportReqBody reportReqBody
    ) {
        Student author = rq.checkAuthentication();

        Report report = reportService.create(author, reportReqBody.title, reportReqBody.content);
        return new RsData<>(
                "201-1",
                "보고서: %s".formatted(report.getTitle()),
                new ReportDto(report)
        );
    }

    @PutMapping("/{id}")
    @Transactional
    public RsData<ReportDto> modify(
            @PathVariable long id,
            @RequestBody @Valid ReportReqBody reportReqBody
    ) {
        Student author = rq.checkAuthentication();
        Report report = reportService.findById(id).get();

        report.checkActorCanModify(author);

        reportService.modify(report, reportReqBody.title, reportReqBody.content);

        return new RsData<>(
                "200-1",
                "%d번 글이 수정되었습니다.".formatted(id),
                new ReportDto(report)
        );
    }

    @DeleteMapping("/{id}")
    public RsData<Void> delete(
            @PathVariable long id,
            @RequestHeader("actorId") Long actorId
    ) {
        Student author = rq.checkAuthentication();
        Report report = reportService.findById(id).get();

        report.checkActorCanDelete(author);

        reportService.delete(report);

        return new RsData<>(
                "200-1",
                "%d번 글이 삭제되었습니다.".formatted(id)
        );
    }
}
