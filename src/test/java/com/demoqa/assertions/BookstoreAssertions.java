package com.demoqa.assertions;

import com.demoqa.models.bookstore.UserBooksResponseDto;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

public class BookstoreAssertions {

    @Step("Проверяем, что список книг пользователя пустой")
    public static void assertUserBooksEmpty(UserBooksResponseDto responseDto) {
        Assertions.assertTrue(responseDto.getBooks().isEmpty(), "Список книг пользователя должен быть пустым");
    }

    @Step("Проверяем, что в списке книг пользователя есть книга с ISBN [{isbn}]")
    public static void assertUserHasBook(UserBooksResponseDto responseDto, String isbn) {
        Assertions.assertTrue(
                responseDto.getBooks().stream().anyMatch(b -> b.getIsbn().equals(isbn)),
                "Книга с ISBN " + isbn + " должна быть в профиле"
        );
    }
}
