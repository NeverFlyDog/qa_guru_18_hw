package com.demoqa.api;

import com.demoqa.data.AuthData;
import com.demoqa.models.auth.LoginRequestDto;
import com.demoqa.models.auth.LoginResponseDto;
import io.qameta.allure.Step;

import static com.demoqa.specs.BaseSpecs.spec;

public class AuthApi {

    public static LoginResponseDto login(String username, String password) {
        LoginRequestDto requestDto = new LoginRequestDto(username, password);
        return spec()
                .body(requestDto)
                .when()
                .post("/Account/v1/Login")
                .then()
                .statusCode(200)
                .extract().as(LoginResponseDto.class);
    }

    @Step("Авторизовываемся через API с дефолтным пользователем")
    public static LoginResponseDto loginWithDefaultUser() {
        return login(AuthData.LOGIN, AuthData.PASSWORD);
    }
}
