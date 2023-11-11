package com.quizapp.service.data.service;

import com.quizapp.service.data.entity.Question;
import com.quizapp.service.data.repository.QuestionRepository;
import com.quizapp.service.util.enums.Category;
import com.quizapp.service.util.enums.Difficulty;
import com.quizapp.service.util.enums.Types;
import java.util.Collections;
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
    return shuffleQuestions(questionRepository.findAll());
  }

  public List<Question> getQuestionsByCategory(String category) {
    return shuffleQuestions(questionRepository.findByCategory(Category.valueOf(category)));
  }

  public List<Question> getQuestionsByCategoryAndDifficulty(String category, String difficulty) {
    return shuffleQuestions(
        questionRepository.findByCategoryAndDifficulty(
            Category.valueOf(category), Difficulty.valueOf(difficulty)));
  }

  public List<Question> getQuestionsByCategoryAndDifficultyAndType(
      String category, String difficulty, String type) {
    return questionRepository.findByCategoryAndDifficultyAndType(
        Category.valueOf(category), Difficulty.valueOf(difficulty), Types.valueOf(type));
  }

  private List<Question> shuffleQuestions(List<Question> questions) {
    Collections.shuffle(questions);
    return questions;
  }
}
