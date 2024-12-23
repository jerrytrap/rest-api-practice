package com.example.rest.domain.student;

import com.example.rest.global.RsData;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
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
    public RsData<Void> deleteStudent(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        studentService.delete(student);

        return new RsData<>(
                "200-1",
                "%d번 글이 삭제되었습니다.".formatted(id)
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
            Integer age
    ) {
    }

    record StudentCreateResBody(
            StudentDto studentDto,
            long totalCount
    ) {
    }

    @PostMapping
    public RsData<StudentCreateResBody> createStudent(@RequestBody @Valid StudentController.StudentCreateReqBody body) {
        Student student = studentService.createStudent(body.name, body.age);

        return new RsData<>(
                "200-1",
                "%d번 학생을 추가했습니다.".formatted(student.getId()),
                new StudentCreateResBody(
                        new StudentDto(student),
                        studentService.getCount()
                )
        );
    }
}
