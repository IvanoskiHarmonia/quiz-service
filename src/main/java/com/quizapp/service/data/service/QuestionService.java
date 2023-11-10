package com.quizapp.service.data.service;

import com.quizapp.service.data.entity.Question;
import com.quizapp.service.data.repository.QuestionRepository;
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
}
