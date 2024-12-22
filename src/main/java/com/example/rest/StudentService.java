package com.example.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public long getCount() {
        return studentRepository.count();
    }

    public Student createUser(String name, Integer age) {
        Student student = Student.builder()
                .name(name)
                .age(age)
                .build();

        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAllByOrderByIdDesc();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow();
    }
}
