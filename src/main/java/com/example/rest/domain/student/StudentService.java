package com.example.rest.domain.student;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public long getCount() {
        return studentRepository.count();
    }

    public Student createStudent(String name, Integer age) {
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

    public void delete(Student student) {
        studentRepository.delete(student);
    }

    public void modify(Student student, String name, Integer age) {
        student.setName(name);
        student.setAge(age);
    }
}
