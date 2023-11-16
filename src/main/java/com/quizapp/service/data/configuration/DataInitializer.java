package com.quizapp.service.data.configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizapp.service.data.entity.Category;
import com.quizapp.service.data.entity.Question;
import com.quizapp.service.data.repository.CategoryRepository;
import com.quizapp.service.data.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import java.io.InputStream;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@Configuration
public class DataInitializer {

  @Bean
  public CommandLineRunner loadCategories(
      CategoryRepository categoryRepository,
      ResourceLoader resourceLoader,
      ObjectMapper objectMapper) {
    return args -> {
      Resource resource = resourceLoader.getResource("classpath:categories.json");
      InputStream inputStream = resource.getInputStream();

      List<Category> categories =
          objectMapper.readValue(inputStream, new TypeReference<List<Category>>() {});

      for (Category category : categories) {
        categoryRepository.save(category);
      }
    };
  }

  @Bean
  public CommandLineRunner loadData(
      CategoryRepository categoryRepository,
      QuestionRepository questionRepository,
      ResourceLoader resourceLoader,
      ObjectMapper objectMapper) {
    return args -> {
      Resource resource = resourceLoader.getResource("classpath:questions.json");
      InputStream inputStream = resource.getInputStream();

      List<Question> questions =
          objectMapper.readValue(inputStream, new TypeReference<List<Question>>() {});

      for (Question question : questions) {
        Category category =
            categoryRepository
                .findByName(question.getCategory().getName())
                .orElseThrow(
                    () ->
                        new EntityNotFoundException(
                            "Category not found: " + question.getCategory().getName()));
        question.setCategory(category);
        questionRepository.save(question);
      }
    };
  }
}
