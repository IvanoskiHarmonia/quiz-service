package com.quizapp.service.data.controller;

import com.quizapp.service.data.entity.Question;
import com.quizapp.service.data.service.QuestionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/questions")
public class QuestionController {

  private final QuestionService questionService;

  @Autowired
  public QuestionController(QuestionService questionService) {
    this.questionService = questionService;
  }

  @PostMapping("/add")
  public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
    return ResponseEntity.ok(questionService.saveQuestion(question));
  }

  @GetMapping("/all")
  public ResponseEntity<List<Question>> getAllQuestions() {
    return ResponseEntity.ok(questionService.getAllQuestions());
  }

  @GetMapping("/category/{category}")
  public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
    return ResponseEntity.ok(questionService.getQuestionsByCategory(category.toUpperCase()));
  }

  @GetMapping("/category-and-difficulty/{category}/{difficulty}")
  public ResponseEntity<List<Question>> getQuestionsByCategoryAndDifficulty(
      @PathVariable String category, @PathVariable String difficulty) {
    return ResponseEntity.ok(
        questionService.getQuestionsByCategoryAndDifficulty(
            category.toUpperCase(), difficulty.toUpperCase()));
  }
}
