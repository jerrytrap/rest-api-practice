package com.example.rest.domain.report;

import com.example.rest.domain.report.comment.Comment;
import com.example.rest.domain.student.Student;
import com.example.rest.global.BaseTime;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Report extends BaseTime {
    @Column(length = 20)
    private String title;

    @Column(length = 50)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student author;

    @OneToMany(mappedBy = "report", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Student author, String content) {
        Comment comment = Comment.builder()
                .report(this)
                .author(author)
                .content(content)
                .build();

        comments.add(comment);
    }
}
