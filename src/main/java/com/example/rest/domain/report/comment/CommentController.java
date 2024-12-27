package com.example.rest.domain.report.comment;

import com.example.rest.domain.report.Report;
import com.example.rest.domain.report.ReportService;
import com.example.rest.global.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/report/{reportId}/comment")
@RequiredArgsConstructor
public class CommentController {
    private final ReportService reportService;

    @GetMapping
    public List<CommentDto> getComments(@PathVariable Long reportId) {
        Report report = reportService.findById(reportId).orElseThrow(
                () -> new ServiceException("404-1", "%d번 글은 존재하지 않습니다.".formatted(reportId))
        );

        return report
                .getComments()
                .reversed()
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
                .getComments()
                .reversed()
                .stream()
                .filter(comment -> comment.getId() == id)
                .map(CommentDto::new)
                .findFirst()
                .orElseThrow(
                        () -> new ServiceException("404-2", "%d번 댓글은 존재하지 않습니다.".formatted(id))
                );
    }
}
