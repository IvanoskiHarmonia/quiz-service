package com.quizapp.service.data.controller;

import com.quizapp.service.data.dto.QuizResult;
import com.quizapp.service.data.dto.QuizSubmission;
import com.quizapp.service.data.entity.Question;
import com.quizapp.service.data.entity.Quiz;
import com.quizapp.service.data.service.QuizService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("api/quizzes")
public class QuizController {

  private final QuizService quizService;

  @Autowired
  public QuizController(QuizService quizService) {
    this.quizService = quizService;
  }

  @GetMapping("/random10")
  public ResponseEntity<List<Question>> getRandom10Questions() {
    List<Question> questions = quizService.getRandomQuestions();
    return ResponseEntity.ok(questions);
  }

  @PostMapping("/random")
  public ResponseEntity<Quiz> createRandomQuiz() {
    List<Question> questions = quizService.getRandomQuestions();
    if (questions.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    Quiz quiz = new Quiz();
    for (Question question : questions) {
      question.setQuiz(quiz);
    }
    quiz.setQuestions(questions);
    quizService.saveQuiz(quiz);
    return new ResponseEntity<>(quiz, HttpStatus.CREATED);
  }

  @GetMapping("/all")
  public ResponseEntity<List<Quiz>> getAllQuizzes() {
    List<Quiz> quizzes = quizService.getAllQuizzes();
    return ResponseEntity.ok(quizzes);
  }

  @PostMapping("/submit")
  public ResponseEntity<QuizResult> submitQuiz(@RequestBody QuizSubmission submission) {
    QuizResult quizResult = quizService.evaluateQuiz(submission);
    return ResponseEntity.ok(quizResult);
  }
}
