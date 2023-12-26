package com.quizapp.service;

public final class QuizServiceConstants {

  private QuizServiceConstants() {
    throw new IllegalStateException("Utility class");
  }

  public static final String SECRET_KEY = System.getenv("SECRET_KEY_QUIZ_SERVICE");
}
