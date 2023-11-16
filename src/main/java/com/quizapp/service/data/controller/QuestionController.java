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

@CrossOrigin
@RestController
@RequestMapping("/questions")
public class QuestionController {

  @Autowired private QuestionService questionService;

  // http://localhost:8000/questions/add
  @PostMapping("/add")
  public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
    return ResponseEntity.ok(questionService.saveQuestion(question));
  }

  // http://localhost:8000/questions/all
  @GetMapping("/all")
  public ResponseEntity<List<Question>> getAllQuestions() {
    return ResponseEntity.ok(questionService.getAllQuestions());
  }

  // http://localhost:8000/questions/category
  @GetMapping("/category/{category}")
  public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
    return ResponseEntity.ok(questionService.getQuestionsByCategory(category.toUpperCase()));
  }

  // http://localhost:8000/questions/category-and-difficulty/
  @GetMapping("/category-and-difficulty/{category}/{difficulty}")
  public ResponseEntity<List<Question>> getQuestionsByCategoryAndDifficulty(
      @PathVariable String category, @PathVariable String difficulty) {
    return ResponseEntity.ok(
        questionService.getQuestionsByCategoryAndDifficulty(
            category.toUpperCase(), difficulty.toUpperCase()));
  }
}
