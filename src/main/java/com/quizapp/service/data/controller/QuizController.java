package com.quizapp.service.data.controller;

import com.quizapp.service.data.dto.QuizResult;
import com.quizapp.service.data.dto.QuizSubmission;
import com.quizapp.service.data.entity.Question;
import com.quizapp.service.data.entity.Quiz;
import com.quizapp.service.data.repository.QuestionRepository;
import com.quizapp.service.data.service.QuizService;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("api/quizzes")
public class QuizController {

  private final QuizService quizService;
  private final QuestionRepository questionRepository;
  Random random = new Random();

  @Autowired
  public QuizController(QuizService quizService, QuestionRepository questionRepository) {
    this.quizService = quizService;
    this.questionRepository = questionRepository;
  }

  @GetMapping("/random10")
  public ResponseEntity<List<Question>> getRandom10Questions() {
    List<Question> questions = quizService.getRandomQuestions();
    return ResponseEntity.ok(questions);
  }

  @GetMapping("/all")
  public ResponseEntity<List<Quiz>> getAllQuizzes() {
    List<Quiz> quizzes = quizService.getAllQuizzes();
    return ResponseEntity.ok(quizzes);
  }

  @PostMapping("/create")
  public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) {
    if (quiz.getQuestions() == null || quiz.getQuestions().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    // Fetch existing questions and associate with the new quiz
    List<Question> existingQuestions =
        quiz.getQuestions().stream()
            .map(Question::getId)
            .filter(Objects::nonNull)
            .map(id -> questionRepository.findById(id).orElse(null))
            .collect(Collectors.toList());

    quiz.setQuestions(existingQuestions);
    existingQuestions.forEach(question -> question.setQuiz(quiz)); // Set the back reference

    Quiz savedQuiz = quizService.saveQuiz(quiz);
    return new ResponseEntity<>(savedQuiz, HttpStatus.CREATED);
  }

  @GetMapping("/{quizId}/questions")
  public ResponseEntity<List<Question>> getQuizQuestions(@PathVariable Long quizId) {
    try {
      Quiz quiz = quizService.findQuizById(quizId);
      if (quiz == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
      }
      return ResponseEntity.ok(quiz.getQuestions());
    } catch (Exception e) {
      // Log error and handle exception
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @PostMapping("/{quizId}/submit")
  public ResponseEntity<QuizResult> submitQuiz(
      @PathVariable Long quizId, @RequestBody QuizSubmission submission) {
    try {
      Quiz quiz = quizService.findQuizById(quizId);
      if (quiz == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }

      QuizResult result = quizService.evaluateQuiz(quiz, submission);

      return ResponseEntity.ok(result);
    } catch (Exception e) {
      // Log error and handle exception
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
