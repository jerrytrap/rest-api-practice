package com.example.rest;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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

    @AllArgsConstructor
    @Getter
    public static class StudentModifyBody {
        private String name;
        private Integer age;
    }

    @PutMapping("/{id}")
    @Transactional
    public RsData updateStudent(@PathVariable Long id, @RequestBody StudentModifyBody body) {
        Student student = studentService.getStudentById(id);
        studentService.modify(student, body.getName(), body.getAge());

        return new RsData(
                "200-1",
                "%d번 글을 수정했습니다.".formatted(id)
        );
    }
}
