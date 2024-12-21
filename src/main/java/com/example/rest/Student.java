package com.example.rest;

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
}
