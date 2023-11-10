package com.quizapp.service.data.controller;

import com.quizapp.service.data.entity.Question;
import com.quizapp.service.data.service.QuestionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions")
public class QuestionController {

  @Autowired private QuestionService questionService;

  @GetMapping("/all")
  public ResponseEntity<List<Question>> getAllQuestions() {
    return ResponseEntity.ok(questionService.getAllQuestions());
  }

  @PostMapping("/add")
  public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
    return ResponseEntity.ok(questionService.saveQuestion(question));
  }

  @GetMapping("/category/{category}")
  public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
    return ResponseEntity.ok(questionService.getQuestionsByCategory(category.toUpperCase()));
  }

  @GetMapping("/category-and-difficulty/{category}/{difficulty}")
  public ResponseEntity<List<Question>> getQuestionsByCategoryAndDifficulty(
      @PathVariable String category, @PathVariable String difficulty) {
    return ResponseEntity.ok(
        questionService.getQuestionsByCategoryAndDifficulty(category, difficulty));
  }

  @GetMapping("/category-and-difficulty-and-type/{category}/{difficulty}/{type}")
  public ResponseEntity<List<Question>> getQuestionsByCategoryAndDifficultyAndType(
      @PathVariable String category, @PathVariable String difficulty, @PathVariable String type) {
    return ResponseEntity.ok(
        questionService.getQuestionsByCategoryAndDifficultyAndType(category, difficulty, type));
  }
}
