package com.quizapp.service.data.controller;

import com.quizapp.service.data.dto.QuizResult;
import com.quizapp.service.data.dto.QuizSubmission;
import com.quizapp.service.data.entity.Question;
import com.quizapp.service.data.entity.Quiz;
import com.quizapp.service.data.service.QuizService;
import com.quizapp.service.util.enums.Difficulty;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("api/quizzes")
public class QuizController {

  private final QuizService quizService;
  Random random = new Random();

  @Autowired
  public QuizController(QuizService quizService) {
    this.quizService = quizService;
  }

  @GetMapping("/random10")
  public ResponseEntity<List<Question>> getRandom10Questions() {
    List<Question> questions = quizService.getRandomQuestions();
    return ResponseEntity.ok(questions);
  }

  @GetMapping("/all")
  public ResponseEntity<List<Quiz>> getAllQuizzes() {
    List<Quiz> quizzes = quizService.getAllQuizzes();
    return ResponseEntity.ok(quizzes);
  }

  @PostMapping("/create-random10")
  public ResponseEntity<Quiz> createRandomQuiz() {
    List<Question> questions = quizService.getRandomQuestions();
    if (questions.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Calculate difficulty
    Difficulty quizDifficulty = calculateQuizDifficulty(questions);

    // Generate random title and description
    String randomTitle = generateRandomTitle(); // Implement this method
    String randomDescription = generateRandomDescription(); // Implement this method

    Quiz quiz = new Quiz();
    quiz.setTitle(randomTitle);
    quiz.setDescription(randomDescription);
    quiz.setDifficulty(quizDifficulty);

    for (Question question : questions) {
      question.setQuiz(quiz);
    }
    quiz.setQuestions(questions);

    quizService.saveQuiz(quiz);
    return new ResponseEntity<>(quiz, HttpStatus.CREATED);
  }

  private Difficulty calculateQuizDifficulty(List<Question> questions) {
    Map<Difficulty, Long> difficultyCount =
        questions.stream()
            .collect(Collectors.groupingBy(Question::getDifficulty, Collectors.counting()));

    return Collections.max(difficultyCount.entrySet(), Map.Entry.comparingByValue()).getKey();
  }

  private String generateRandomTitle() {
    String[] adjectives = {"Fun", "Challenging", "Interesting", "Intriguing", "Exciting"};
    String[] nouns = {"Quiz", "Challenge", "Test", "Examination", "Assessment"};

    String adjective = adjectives[random.nextInt(adjectives.length)];
    String noun = nouns[random.nextInt(nouns.length)];

    return adjective + " " + noun;
  }

  private String generateRandomDescription() {
    String[] descriptions = {
      "Test your knowledge with this engaging quiz!",
      "Are you ready to take on this exciting challenge?",
      "Explore various questions and broaden your understanding.",
      "A great way to assess your skills and learn new facts.",
      "Dive into this quiz and see how much you know!"
    };

    return descriptions[random.nextInt(descriptions.length)];
  }

  @PostMapping("/submit")
  public ResponseEntity<QuizResult> submitQuiz(@RequestBody QuizSubmission submission) {
    QuizResult quizResult = quizService.evaluateQuiz(submission);
    return ResponseEntity.ok(quizResult);
  }
}
