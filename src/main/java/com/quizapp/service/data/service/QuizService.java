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
  public Quiz saveQuiz(Quiz quiz) {
    quizRepository.save(quiz);
    return quiz;
  }

  public Quiz findQuizById(Long quizId) {
    return quizRepository.findById(quizId).orElse(null);
  }

  /**
   * @Description: This method is used to evaluate the quiz submission and return the result.
   *
   * @param quiz The quiz to be evaluated
   * @param submission The user's submission
   * @return QuizResultDTO
   */
  public QuizResult evaluateQuiz(Quiz quiz, QuizSubmission submission) {
    int score = 0;
    List<QuestionAnswerPair> answerDetails = new ArrayList<>();

    Map<Long, String> submittedAnswers = submission.getAnswers();

    for (Question question : quiz.getQuestions()) {
      Long questionId = question.getId();
      String correctAnswer = question.getAnswer();
      String userAnswer = submittedAnswers.getOrDefault(questionId, "");

      boolean isCorrect = correctAnswer.equals(userAnswer);
      if (isCorrect) {
        score++;
      }

      answerDetails.add(new QuestionAnswerPair(question, userAnswer, isCorrect));
    }

    return new QuizResult(score, answerDetails);
  }

  /**
   * @CurrentStatus: This method is not implemented yet. @Description: This method is used to
   * calculate the score based on the number of correct answers.
   *
   * @param correctAnswers The number of correct answers
   * @param totalQuestions The total number of questions
   * @return int
   */
  private int calculateScore(int correctAnswers, int totalQuestions) {
    return (int) (((double) correctAnswers / totalQuestions) * 100);
  }
}
