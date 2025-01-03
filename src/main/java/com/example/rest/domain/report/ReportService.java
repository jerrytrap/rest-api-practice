package com.example.rest.domain.report;

import com.example.rest.domain.student.Student;
import com.example.rest.global.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;

    public long count() {
        return reportRepository.count();
    }

    public Report create(Student author, String title, String content) {
        reportRepository.findByTitle(title)
                .ifPresent(_ -> {
                    throw new ServiceException("400-1", "Report already exists");
                });

        Report report = Report.builder()
                .author(author)
                .title(title)
                .content(content)
                .build();

        return reportRepository.save(report);
    }

    public Optional<Report> findById(long id) {
        return reportRepository.findById(id);
    }

    public void modify(Report report, String title, String content) {
        report.setTitle(title);
        report.setContent(content);
        reportRepository.save(report);
    }

    public void delete(Report report) {
        reportRepository.delete(report);
    }

    public void flush() {
        reportRepository.flush();
    }
}
