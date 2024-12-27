package com.example.rest.domain.report.comment;

import com.example.rest.domain.report.Report;
import com.example.rest.domain.report.ReportService;
import com.example.rest.domain.student.Student;
import com.example.rest.global.Rq;
import com.example.rest.global.RsData;
import com.example.rest.global.ServiceException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/report/{reportId}/comment")
@RequiredArgsConstructor
public class CommentController {
    private final EntityManager em;
    private final ReportService reportService;
    private final Rq rq;

    @GetMapping
    public List<CommentDto> getComments(@PathVariable Long reportId) {
        Report report = reportService.findById(reportId).orElseThrow(
                () -> new ServiceException("404-1", "%d번 글은 존재하지 않습니다.".formatted(reportId))
        );

        return report
                .getCommentsByOrderByIdDesc()
                .stream()
                .map(CommentDto::new)
                .toList();
    }

    @GetMapping("/{id}")
    public CommentDto getComment(@PathVariable Long reportId, @PathVariable Long id) {
        Report report = reportService.findById(reportId).orElseThrow(
                () -> new ServiceException("404-1", "%d번 글은 존재하지 않습니다.".formatted(reportId))
        );

        return report
                .getCommentById(id)
                .map(CommentDto::new)
                .orElseThrow(
                        () -> new ServiceException("404-2", "%d번 댓글은 존재하지 않습니다.".formatted(id))
                );
    }

    record CommentCreateReqBody(
            @NotBlank
            @Length(min = 2)
            String content
    ) {
    }

    @PostMapping
    @Transactional
    public RsData<Void> createComment(
            @PathVariable Long reportId,
            @RequestBody CommentCreateReqBody body
    ) {
        Student student = rq.checkAuthentication();

        Report report = reportService.findById(reportId).orElseThrow(
                () -> new ServiceException("404-1", "%d번 글은 존재하지 않습니다.".formatted(reportId))
        );

        Comment comment = report.addComment(
                student,
                body.content
        );
        em.flush();

        return new RsData<>(
                "201-1",
                "%d번 댓글이 작성되었습니다.".formatted(comment.getId())
        );
    }
}
