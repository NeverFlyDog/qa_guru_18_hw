package com.demoqa.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.demoqa.config.BrowserConfig;
import com.demoqa.utils.AllureAttachments;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.WebDriverRunner.hasWebDriverStarted;

public class BaseTest {

    @BeforeAll
    static void setUp() {
        Configuration.browser = BrowserConfig.BROWSER;
        Configuration.browserVersion = BrowserConfig.BROWSER_VERSION;
        Configuration.browserSize = BrowserConfig.BROWSER_SIZE;
        Configuration.remote = BrowserConfig.REMOTE_URL;
        Configuration.pageLoadStrategy = "eager";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;

        Configuration.baseUrl = "https://demoqa.com";
        RestAssured.baseURI = "https://demoqa.com";
    }

    @BeforeEach
    void addListener() {
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
