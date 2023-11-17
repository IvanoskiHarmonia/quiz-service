package com.quizapp.service.data.dto;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuizSubmission {

  private Long quizId;
  private Map<Long, String> answers;
}
