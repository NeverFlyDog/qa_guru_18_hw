package com.demoqa.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.demoqa.utils.AllureAttachments;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.NoSuchSessionException;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.WebDriverRunner.hasWebDriverStarted;

public class BaseTest {

    @BeforeAll
    static void setUp() {
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";

        RestAssured.baseURI = "https://demoqa.com";

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void afterEach() {
        if (hasWebDriverStarted()) {
            try {
                attachArtifacts();
            } finally {
                getWebDriver().quit();
            }
        }
    }

    private void attachArtifacts() {
        try {
            AllureAttachments.attachScreenshot("Final state");
            AllureAttachments.attachVideo();
            AllureAttachments.attachPageSource();
            AllureAttachments.attachBrowserConsoleLogs();
        } catch (NoSuchSessionException e) {
            System.out.println("WebDriver session already closed, skipping artifacts");
        }
    }
}
