package com.quizapp.service.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quizapp.service.util.enums.Difficulty;
import com.quizapp.service.util.enums.Types;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
  @Column(name = "question_id")
  private Long id;

  @Column(unique = true, nullable = false)
  private String text;

  @Column(nullable = false, length = 2000)
  private String answer;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Difficulty difficulty;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Types type = Types.OPEN_ENDED;

  @ManyToOne
  @JoinColumn(name = "quiz_id")
  @JsonIgnore
  private Quiz quiz;
}
