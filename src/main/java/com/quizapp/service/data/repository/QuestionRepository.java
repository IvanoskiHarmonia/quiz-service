package com.quizapp.service.data.repository;

import com.quizapp.service.data.entity.Category;
import com.quizapp.service.data.entity.Question;
import com.quizapp.service.util.enums.Difficulty;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

  List<Question> findByCategory(Category category);

  List<Question> findByCategoryAndDifficulty(Category category, Difficulty difficulty);
}
