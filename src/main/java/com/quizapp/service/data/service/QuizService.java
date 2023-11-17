package com.quizapp.service.data.service;

import com.quizapp.service.data.dto.QuestionAnswerPair;
import com.quizapp.service.data.dto.QuizResult;
import com.quizapp.service.data.dto.QuizSubmission;
import com.quizapp.service.data.entity.Question;
import com.quizapp.service.data.repository.QuestionRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

  @Autowired private QuestionRepository questionRepository;

  public List<Question> getRandomQuestions() {
    List<Question> allQuestions = questionRepository.findAll();
    Collections.shuffle(allQuestions);
    return allQuestions.subList(0, 10);
  }

  public QuizResult evaluateQuiz(QuizSubmission submission) {
    int score = 0;
    List<QuestionAnswerPair> answerDetails = new ArrayList<>();

    for (Map.Entry<Long, String> entry : submission.getAnswers().entrySet()) {
      Long questionId = entry.getKey();
      String userAnswer = entry.getValue();

      Question question = questionRepository.findById(questionId).orElse(null);
      if (question != null) {
        boolean isCorrect = question.getAnswer().equals(userAnswer);
        if (isCorrect) {
          score++;
        }

        answerDetails.add(new QuestionAnswerPair(question, userAnswer, isCorrect));
      }
    }

    int totalScore = calculateScore(score, submission.getAnswers().size());
    return new QuizResult(totalScore, answerDetails);
  }

  private int calculateScore(int correctAnswers, int totalQuestions) {
    return (int) (((double) correctAnswers / totalQuestions) * 100);
  }
}
