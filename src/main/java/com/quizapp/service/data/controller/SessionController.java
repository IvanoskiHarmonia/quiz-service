package com.quizapp.service.data.controller;

import com.quizapp.service.QuizServiceConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/session")
public class SessionController {

  private static final Logger logger = LoggerFactory.getLogger(SessionController.class);

  @GetMapping("/validate")
  public ResponseEntity<String> validateSession(HttpServletRequest request) {
    boolean isValidSession = false;
    String token = null;

    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if ("authToken".equals(cookie.getName())) {
          token = cookie.getValue();
          break;
        }
      }
    }

    if (token != null && !token.isEmpty()) {
      try {
        Claims claims =
            Jwts.parser()
                .setSigningKey(QuizServiceConstants.SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        if (claims.getExpiration().after(new Date())) {
          isValidSession = true;
        }
      } catch (Exception e) {
        logger.error("Error validating session: {}", e.getMessage());
      }
    }

    return ResponseEntity.ok().body("{\"isValidSession\":" + isValidSession + "}");
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout(HttpServletResponse response) {
    Cookie cookie = new Cookie("authToken", null);
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    cookie.setMaxAge(0);
    response.addCookie(cookie);

    return ResponseEntity.ok().body("{\"message\":\"Successfully logged out.\"}");
  }
}
