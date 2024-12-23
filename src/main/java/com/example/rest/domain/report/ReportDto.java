package com.example.rest.domain.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReportDto {
    private long id;

    @JsonProperty("createdDateTime")
    private LocalDateTime createDate;

    @JsonProperty("modifiedDateTime")
    private LocalDateTime modifyDate;

    private String title;

    private String content;

    public ReportDto(Report report) {
        this.id = report.getId();
        this.createDate = report.getCreateDate();
        this.modifyDate = report.getModifiedDate();
        this.title = report.getTitle();
        this.content = report.getContent();
    }
}
