package com.example.rest.domain.report.comment;

import com.example.rest.domain.report.Report;
import com.example.rest.domain.student.Student;
import com.example.rest.global.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseTime {
    @ManyToOne(fetch = FetchType.LAZY)
    private Report report;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student author;

    @Column(columnDefinition = "TEXT")
    private String content;
}
