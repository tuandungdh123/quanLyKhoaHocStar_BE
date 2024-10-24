package com.example.coursemanagement.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Choices")
public class ChoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer choiceId;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question;

    @Column(name = "choice_text", nullable = false)
    private String choiceText;

    @Column(name = "is_correct", nullable = false)
    private Boolean isCorrect = false;
}
