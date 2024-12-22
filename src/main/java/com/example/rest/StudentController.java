package com.example.rest;

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
    public Map<String, Object> deleteStudent(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        studentService.delete(student);

        Map<String, Object> rsData = new HashMap<>();
        rsData.put("resultCode", "200-1");
        rsData.put("msg", "%d번 글을 삭제했습니다.".formatted(id));

        return rsData;
    }
}
