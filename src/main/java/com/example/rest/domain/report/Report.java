package com.example.rest.domain.report;

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
public class Report extends BaseTime {
    @Column(length = 20)
    private String title;

    @Column(length = 50)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student author;
}
