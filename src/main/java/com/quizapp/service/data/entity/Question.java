package com.quizapp.service.data.entity;

import com.quizapp.service.util.enums.Category;
import com.quizapp.service.util.enums.Difficulty;
import com.quizapp.service.util.enums.Types;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true, nullable = false)
  private String text;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Types type;

  @ElementCollection
  @Column(length = 1000)
  private List<String> options;

  @Column(nullable = false, length = 2000)
  private String answer;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Difficulty difficulty;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Category category;
}
