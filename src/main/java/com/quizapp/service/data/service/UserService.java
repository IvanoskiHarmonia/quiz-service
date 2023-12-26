package com.quizapp.service.data.service;

import com.quizapp.service.data.entity.User;
import com.quizapp.service.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User createOrUpdateUser(String email, String token, Long expiresAt) {
    User existingUser = userRepository.findByEmail(email).orElse(null);

    if (existingUser != null) {
      existingUser.setToken(token);
      existingUser.setExpiresAt(expiresAt);
      return userRepository.save(existingUser);
    } else {
      User user = new User();
      user.setEmail(email);
      user.setToken(token);
      user.setExpiresAt(expiresAt);

      return userRepository.save(user);
    }
  }
}
