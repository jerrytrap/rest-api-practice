package com.example.rest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    private final StudentService studentService;

    @Autowired
    @Lazy
    private BaseInitData self;

    @Bean
    public ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
            self.work();
        };
    }

    @Transactional
    public void work() {
        if (studentService.getCount() > 0) return;

        Student student1 = studentService.createUser("이름1", 20);
        Student student2 = studentService.createUser("이름2", 30);
        Student student3 = studentService.createUser("이름3", 40);
    }
}
