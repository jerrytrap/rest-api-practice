package com.example.rest.domain.report;

import com.example.rest.domain.student.StudentDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReportDto {
    private long id;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    private StudentDto author;

    private String title;

    private String content;

    public ReportDto(Report report) {
        this.id = report.getId();
        this.createDate = report.getCreateDate();
        this.modifyDate = report.getModifiedDate();
        this.author = new StudentDto(report.getAuthor());
        this.title = report.getTitle();
        this.content = report.getContent();
    }
}
