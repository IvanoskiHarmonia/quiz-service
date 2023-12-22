package com.quizapp.service.data.controller;

import com.quizapp.service.data.dto.UserDataDTO;
import com.quizapp.service.data.entity.User;
import com.quizapp.service.data.repository.UserRepository;
import com.quizapp.service.data.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService, UserRepository userRepository) {
    this.userService = userService;
  }

  @Transactional
  @PostMapping("/login")
  public ResponseEntity<User> createUser(@NotNull @RequestBody UserDataDTO userData) {
    User user = new User();
    user.setEmail(userData.getEmail());
    user.setToken(userData.getToken());
    user.setExpiresAt(userData.getExpiresAt());

    User savedUser = userService.saveUser(user);
    return ResponseEntity.ok(savedUser);
  }
}
