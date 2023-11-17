package com.quizapp.service.data.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizResult {

  private int score;
  private List<QuestionAnswerPair> answerDetails;
}
