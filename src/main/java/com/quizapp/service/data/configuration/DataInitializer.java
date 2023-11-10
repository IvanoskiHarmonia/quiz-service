package com.quizapp.service.data.configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizapp.service.data.entity.Question;
import com.quizapp.service.data.repository.QuestionRepository;
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
  public CommandLineRunner loadData(
      QuestionRepository questionRepository,
      ResourceLoader resourceLoader,
      ObjectMapper objectMapper) {
    return args -> {
      Resource resource = resourceLoader.getResource("classpath:questions.json");
      InputStream inputStream = resource.getInputStream();

      List<Question> questions =
          objectMapper.readValue(inputStream, new TypeReference<List<Question>>() {});

      for (Question question : questions) {
        questionRepository.save(question);
      }
    };
  }
}
