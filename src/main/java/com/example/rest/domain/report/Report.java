package com.example.rest.domain.report;

import com.example.rest.global.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Report extends BaseTime {
    @Column(unique = true, length = 20)
    private String title;

    @Column(length = 50)
    private String content;
}
