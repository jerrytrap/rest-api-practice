package com.example.rest.domain.report;

import com.example.rest.global.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    record ReportReqBody(
            @NotBlank
            @Length(min = 5)
            String title,
            @NotBlank
            @Length(min = 5)
            String content
    ) {
    }

    record ReportResBody(
        ReportDto reportDto,
        long totalCount
    ) {
    }

    @PostMapping("/create")
    public RsData<ReportDto> create(@RequestBody @Valid ReportReqBody reportReqBody) {
        Report report = reportService.create(reportReqBody.title, reportReqBody.content);

        return new RsData<>(
                "201-1",
                "보고서: %s".formatted(report.getTitle()),
                new ReportDto(report)
        );
    }
}
