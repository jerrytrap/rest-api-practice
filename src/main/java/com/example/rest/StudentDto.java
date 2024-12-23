package com.example.rest;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StudentDto {
    private long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String name;
    private Integer age;

    public StudentDto(Student student) {
        this.id = student.getId();
        this.createDate = student.getCreateDate();
        this.modifyDate = student.getModifiedDate();
        this.name = student.getName();
        this.age = student.getAge();
    }
}
