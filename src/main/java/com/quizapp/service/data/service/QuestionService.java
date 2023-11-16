package com.quizapp.service.data.service;

import com.quizapp.service.data.entity.Category;
import com.quizapp.service.data.entity.Question;
import com.quizapp.service.data.repository.CategoryRepository;
import com.quizapp.service.data.repository.QuestionRepository;
import com.quizapp.service.util.enums.Difficulty;
import jakarta.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

  @Autowired private QuestionRepository questionRepository;
  @Autowired private CategoryRepository categoryRepository;

  public Question saveQuestion(Question question) {
    String categoryName = question.getCategory().getName();
    Category category =
        categoryRepository
            .findByName(categoryName)
            .orElseThrow(() -> new EntityNotFoundException("Category not found: " + categoryName));
    question.setCategory(category);
    return questionRepository.save(question);
  }

  public List<Question> getAllQuestions() {
    return shuffleQuestions(questionRepository.findAll());
  }

  public List<Question> getQuestionsByCategory(String categoryName) {
    Category category =
        categoryRepository
            .findByName(categoryName)
            .orElseThrow(() -> new EntityNotFoundException("Category not found: " + categoryName));
    return shuffleQuestions(questionRepository.findByCategory(category));
  }

  public List<Question> getQuestionsByCategoryAndDifficulty(
      String categoryName, String difficulty) {
    Category category =
        categoryRepository
            .findByName(categoryName)
            .orElseThrow(() -> new EntityNotFoundException("Category not found: " + categoryName));
    return shuffleQuestions(
        questionRepository.findByCategoryAndDifficulty(category, Difficulty.valueOf(difficulty)));
  }

  private List<Question> shuffleQuestions(List<Question> questions) {
    Collections.shuffle(questions);
    return questions;
  }
}
