package com.example.rest.domain.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;

    public long count() {
        return reportRepository.count();
    }

    public Report create(String title, String content) {
        reportRepository.findByTitle(title)
                .ifPresent(_ -> {
                    throw new IllegalArgumentException("Report already exists");
                });

        Report report = Report.builder()
                .title(title)
                .content(content)
                .build();

        return reportRepository.save(report);
    }
}
