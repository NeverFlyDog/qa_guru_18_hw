package com.demoqa.tests;

import com.demoqa.api.AuthApi;
import com.demoqa.api.BookstoreApi;
import com.demoqa.assertions.BookstoreAssertions;
import com.demoqa.data.BookstoreData;
import com.demoqa.models.auth.LoginResponseDto;
import com.demoqa.models.bookstore.UserBooksResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Добавление книги пользователю")
public class BookstoreTests extends BaseTest {
    private BookstoreApi bookstoreApi;
    private BookstoreData bookstoreData;

    @BeforeEach
    void setUpUser() {
        LoginResponseDto loginResponseDto = AuthApi.loginWithDefaultUser();

        bookstoreData = new BookstoreData();

        bookstoreApi = new BookstoreApi(loginResponseDto.getToken(), loginResponseDto.getUserId());
        bookstoreApi.deleteAllUserBooks();
    }

    @Test
    @DisplayName("Добавление книги через API")
    void addBookTest() {
        bookstoreApi.addBooks(bookstoreData.getIsbn());

        UserBooksResponseDto userBooksResponseDto = bookstoreApi.getUserBooks();
        BookstoreAssertions.assertUserHasBook(userBooksResponseDto, bookstoreData.getIsbn());

        bookstoreApi.deleteAllUserBooks();
    }
}
