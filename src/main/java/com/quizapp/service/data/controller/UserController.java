package com.quizapp.service.data.controller;

import com.quizapp.service.data.dto.UserDataDTO;
import com.quizapp.service.data.entity.User;
import com.quizapp.service.data.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @Transactional
  @PostMapping("/login")
  public ResponseEntity<User> createUser(
      @NotNull @RequestBody UserDataDTO userData, HttpServletResponse response) {

    User user =
        userService.createOrUpdateUser(
            userData.getEmail(), userData.getToken(), userData.getExpiresAt());

    Cookie cookie = new Cookie("token", userData.getToken());
    cookie.setHttpOnly(true);
    cookie.setMaxAge(60 * 60 * 24 * 365); // 1 year
    cookie.setSecure(true);
    cookie.setPath("/");
    response.addCookie(cookie);

    return ResponseEntity.ok(user);
  }
}
