package com.quizapp.service.data.dto;

import com.quizapp.service.data.entity.Question;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QuestionAnswerPair {
  private Question question;
  private String userAnswer;
  private boolean isCorrect;
}
