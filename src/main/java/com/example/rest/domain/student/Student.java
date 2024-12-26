package com.example.rest.domain.student;

import com.example.rest.global.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student extends BaseTime {
    @Column(length = 10)
    private String name;

    private Integer age;

    private String password;

    @Column(unique = true, length = 50)
    private String apiKey;
}
