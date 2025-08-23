package com.demoqa.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class ProfilePage {

    @Step("Открываем страницу профиля")
    public ProfilePage open() {
        Selenide.open("/profile");
        return this;
    }

    private SelenideElement getRowByIsbn(String isbn) {
        return $("a[href$='" + isbn + "']").closest(".rt-tr");
    }

    @Step("Нажимаем кнопку удаления для книги с ISBN [{isbn}]")
    public ProfilePage clickDeleteForIsbn(String isbn) {
        getRowByIsbn(isbn)
                .find("span[id^='delete-record']")
                .click();
        return this;
    }

    @Step("Подтверждаем удаление в модальном окне")
    public ProfilePage clickConfirmInModal() {
        $("#closeSmallModal-ok").click();
        return this;
    }

    @Step("Принимаем системный алерт")
    public ProfilePage acceptAlert() {
        switchTo().alert().accept();
        return this;
    }

    @Step("Удаляем книгу с ISBN [{isbn}]")
    public ProfilePage deleteBookByIsbn(String isbn) {
        clickDeleteForIsbn(isbn)
                .clickConfirmInModal()
                .acceptAlert();
        return this;
    }

    @Step("Проверяем, что список книг в профиле пустой")
    public void verifyListIsEmpty() {
        $(".profile-wrapper > .ReactTable").shouldHave(text("No rows found"));
    }
}
