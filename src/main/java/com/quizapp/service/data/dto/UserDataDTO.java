package com.quizapp.service.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDataDTO {

  private String token;
  private Long expiresAt;
  private String email;
}
