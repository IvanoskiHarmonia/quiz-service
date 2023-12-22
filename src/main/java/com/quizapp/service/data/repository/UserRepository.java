package com.quizapp.service.data.repository;

import com.quizapp.service.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
