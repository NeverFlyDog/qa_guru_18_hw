package com.demoqa.api;

import com.demoqa.data.AuthData;
import com.demoqa.models.auth.TokenResponseDto;
import com.demoqa.models.auth.LoginRequestDto;
import com.demoqa.models.auth.LoginResponseDto;
import io.qameta.allure.Step;

import java.time.Instant;
import java.time.format.DateTimeParseException;

import static com.demoqa.specs.BaseSpecs.spec;

public class AuthApi {

    public static LoginResponseDto login(String username, String password) {
        LoginRequestDto requestDto = new LoginRequestDto(username, password);

        LoginResponseDto responseDto = doLogin(requestDto);

        // При запросе логина сервер не создаёт новый токен, если предыдущий истёк.
        // Поэтому сначала вызываем generateToken для обновления токена на сервере,
        // а затем повторно делаем запрос логина для получения действительного токена.
        if (isTokenExpired(responseDto.getExpires())) {
            generateToken(requestDto);
            responseDto = doLogin(requestDto);
        }

        return responseDto;
    }

    @Step("Авторизовываемся через API с дефолтным пользователем")
    public static LoginResponseDto loginWithDefaultUser() {
        return login(AuthData.LOGIN, AuthData.PASSWORD);
    }

    @Step("Запрашиваем генерацию нового токена авторизации")
    public static void generateToken(LoginRequestDto loginRequestDto) {
        spec()
                .body(loginRequestDto)
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .statusCode(200)
                .extract().as(TokenResponseDto.class);
    }

    private static LoginResponseDto doLogin(LoginRequestDto requestDto) {
        return spec()
                .body(requestDto)
                .when()
                .post("/Account/v1/Login")
                .then()
                .statusCode(200)
                .extract().as(LoginResponseDto.class);
    }

    private static boolean isTokenExpired(String date) {
        try {
            Instant expiration = Instant.parse(date);
            return Instant.now().isAfter(expiration);
        } catch (DateTimeParseException e) {
            return true;
        }
    }
}
