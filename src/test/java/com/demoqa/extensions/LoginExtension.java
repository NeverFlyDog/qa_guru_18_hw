package com.demoqa.extensions;

import com.demoqa.api.AuthApi;
import com.demoqa.models.auth.LoginResponseDto;
import io.qameta.allure.Step;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class LoginExtension implements BeforeEachCallback {

    @Step("Устанавливаем куки авторизации пользователя")
    @Override
    public void beforeEach(ExtensionContext context) {
        LoginResponseDto loginResponseDto = AuthApi.loginWithDefaultUser();

        open("/favicon.ico");

        getWebDriver().manage().addCookie(new Cookie("token", loginResponseDto.getToken()));
        getWebDriver().manage().addCookie(new Cookie("expires", loginResponseDto.getExpires()));
        getWebDriver().manage().addCookie(new Cookie("userID", loginResponseDto.getUserId()));
    }
}
