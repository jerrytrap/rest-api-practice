package com.example.rest.domain.student;

import com.example.rest.global.RsData;
import com.example.rest.global.ServiceException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public List<StudentDto> getStudents() {
        return studentService.getAllStudents()
                .stream()
                .map(StudentDto::new)
                .toList();
    }

    @GetMapping("/{id}")
    public StudentDto getStudent(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        return new StudentDto(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RsData<Void>> deleteStudent(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        studentService.delete(student);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new RsData<>(
                        "200-1",
                        "%d번 글이 삭제되었습니다.".formatted(id)
                ));
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
    public RsData<StudentDto> updateStudent(@PathVariable Long id, @RequestBody @Valid StudentModifyBody body) {
        Student student = studentService.getStudentById(id);
        studentService.modify(student, body.name, body.age);

        return new RsData<>(
                "200-1",
                "%d번 학생을 수정했습니다.".formatted(id),
                new StudentDto(student)
        );
    }

    record StudentCreateReqBody(
            @NotBlank
            @Length(min = 2)
            String name,

            @Min(8)
            Integer age,

            @NotNull
            String password
    ) {
    }

    record StudentCreateResBody(
            StudentDto studentDto,
            long totalCount
    ) {
    }

    record StudentLoginReqBody(
            @NotBlank
            @Length(min = 4)
            String username,
            @NotBlank
            @Length(min = 4)
            String password
    ) {
    }

    record StudentLoginResBody(
            StudentDto item,
            String apiKey
    ) {
    }

    @PostMapping
    public RsData<StudentCreateResBody> createStudent(@RequestBody @Valid StudentController.StudentCreateReqBody body) {
        Student student = studentService.createStudent(body.name, body.age, body.password);

        return new RsData<>(
                "200-1",
                "%d번 학생을 추가했습니다.".formatted(student.getId()),
                new StudentCreateResBody(
                        new StudentDto(student),
                        studentService.getCount()
                )
        );
    }

    @PostMapping("/login")
    public RsData<StudentLoginResBody> login(@RequestBody @Valid StudentLoginReqBody body) {
        Student student = studentService.findStudentByName(body.username)
                .orElseThrow(() -> new ServiceException("401-1", "해당 회원은 존재하지않습니다."));

        if (!student.getPassword().equals(body.password)) {
            throw new ServiceException("401-2", "비밀번호가 일치하지 않습니다.");
        }

        String apiKey = student.getApiKey();

        return new RsData<>(
                "201-1",
                "%s님 환영합니다.".formatted(student.getName()),
                new StudentLoginResBody(
                        new StudentDto(student),
                        apiKey
                )
        );
    }
}
