package com.quizapp.service.data.repository;

import com.quizapp.service.data.entity.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  Optional<Category> findByName(String categoryName);
}
