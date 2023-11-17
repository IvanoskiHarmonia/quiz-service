package com.quizapp.service.data.controller;

import com.quizapp.service.data.dto.QuizResult;
import com.quizapp.service.data.dto.QuizSubmission;
import com.quizapp.service.data.entity.Question;
import com.quizapp.service.data.service.QuizService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("api/quizzes")
public class QuizController {

  @Autowired private QuizService quizService;

  @GetMapping("/random10")
  public ResponseEntity<List<Question>> getRandom10Questions() {
    List<Question> questions = quizService.getRandomQuestions();
    return ResponseEntity.ok(questions);
  }

  @PostMapping("/submit")
  public ResponseEntity<QuizResult> submitQuiz(@RequestBody QuizSubmission submission) {
    QuizResult quizResult = quizService.evaluateQuiz(submission);
    return ResponseEntity.ok(quizResult);
  }
}
