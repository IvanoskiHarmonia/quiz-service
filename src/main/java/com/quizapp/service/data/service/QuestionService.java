package com.quizapp.service.data.service;

import com.quizapp.service.data.entity.Question;
import com.quizapp.service.data.repository.QuestionRepository;
import com.quizapp.service.util.enums.Category;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

  @Autowired private QuestionRepository questionRepository;

  public Question saveQuestion(Question question) {
    return questionRepository.save(question);
  }

  public List<Question> getAllQuestions() {
    return questionRepository.findAll();
  }

  public List<Question> getQuestionsByCategory(String category) {
    return questionRepository.findByCategory(Category.valueOf(category));
  }

  public List<Question> getQuestionsByCategoryAndDifficulty(String category, String difficulty) {
    return questionRepository.findByCategoryAndDifficulty(Category.valueOf(category), difficulty);
  }

  public List<Question> getQuestionsByCategoryAndDifficultyAndType(
      String category, String difficulty, String type) {
    return questionRepository.findByCategoryAndDifficultyAndType(
        Category.valueOf(category), difficulty, type);
  }
}
