package com.example.rest;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @DeleteMapping("/{id}")
    public RsData deleteStudent(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        studentService.delete(student);

        return new RsData(
                "200-1",
                "%d번 글을 삭제했습니다.".formatted(id)
        );
    }

    record StudentModifyBody(
            @NotBlank
            @Length(min = 2)
            String name,

            @Min(8)
            Integer age
    ) {
    }

    @PutMapping("/{id}")
    @Transactional
    public RsData updateStudent(@PathVariable Long id, @RequestBody @Valid StudentModifyBody body) {
        Student student = studentService.getStudentById(id);
        studentService.modify(student, body.name, body.age);

        return new RsData(
                "200-1",
                "%d번 글을 수정했습니다.".formatted(id)
        );
    }
}
