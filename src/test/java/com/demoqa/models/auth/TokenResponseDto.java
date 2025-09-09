package com.demoqa.models.auth;

import lombok.Data;

@Data
public class TokenResponseDto {
    String token;
    String expires;
    String status;
    String result;
}
