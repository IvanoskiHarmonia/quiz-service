package com.quizapp.service.data.repository;

import com.quizapp.service.data.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {}
