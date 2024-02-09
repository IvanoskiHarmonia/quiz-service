package com.quizapp.service.data.service;

import com.quizapp.service.data.dto.QuestionAnswerPair;
import com.quizapp.service.data.dto.QuizResult;
import com.quizapp.service.data.dto.QuizSubmission;
import com.quizapp.service.data.entity.Question;
import com.quizapp.service.data.entity.Quiz;
import com.quizapp.service.data.repository.QuestionRepository;
import com.quizapp.service.data.repository.QuizRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

  private final QuestionRepository questionRepository;
  private final QuizRepository quizRepository;

  @Autowired
  public QuizService(QuestionRepository questionRepository, QuizRepository quizRepository) {
    this.questionRepository = questionRepository;
    this.quizRepository = quizRepository;
  }

  public List<Question> getRandomQuestions() {
    List<Question> allQuestions = questionRepository.findAll();
    Collections.shuffle(allQuestions);
    return allQuestions.subList(0, 10);
  }

  public List<Quiz> getAllQuizzes() {
    return quizRepository.findAll();
  }

  @Transactional
  public void saveQuiz(Quiz quiz) {
    quizRepository.save(quiz);
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
