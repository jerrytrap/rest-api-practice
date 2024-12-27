package com.example.rest.global;

import com.example.rest.domain.student.Student;
import com.example.rest.domain.student.StudentService;
import com.example.rest.global.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Optional;

@RequestScope
@Component
@RequiredArgsConstructor
public class Rq {
    private final StudentService studentService;
    private final HttpServletRequest request;

    public Student checkAuthentication() {
        String credentials = request.getHeader("Authorization");
        String apiKey = credentials.substring("Bearer ".length());

        Optional<Student> student = studentService.findStudentByApiKey(apiKey);

        if (student.isEmpty()) {
            throw new ServiceException("401-1", "비밀번호가 일치하지 않습니다.");
        }

        return student.get();
    }
}
