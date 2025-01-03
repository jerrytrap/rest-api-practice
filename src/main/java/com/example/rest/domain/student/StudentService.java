package com.example.rest.domain.student;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public long getCount() {
        return studentRepository.count();
    }

    public Student createStudent(String name, Integer age, String password) {
        Student student = Student.builder()
                .name(name)
                .age(age)
                .password(password)
                .apiKey(UUID.randomUUID().toString())
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

    public Optional<Student> findStudentByName(String name) {
        return studentRepository.findByName(name);
    }

    public Optional<Student> findStudentById(long studentId) {
        return studentRepository.findById(studentId);
    }

    public Optional<Student> findStudentByApiKey(String apiKey) {
        return studentRepository.findByApiKey(apiKey);
    }
}
