package com.demoqa.api;

import com.demoqa.data.AuthData;
import com.demoqa.models.auth.LoginRequestDto;
import com.demoqa.models.auth.LoginResponseDto;
import io.qameta.allure.Step;

public class AuthApi extends BaseApi {

    public static LoginResponseDto login(String username, String password) {
        LoginRequestDto loginRequestDto = new LoginRequestDto(username, password);
        return spec()
                .body(loginRequestDto)
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
