package com.demoqa.tests;

import com.demoqa.api.AuthApi;
import com.demoqa.api.BookstoreApi;
import com.demoqa.assertions.BookstoreAssertions;
import com.demoqa.data.BookstoreData;
import com.demoqa.extensions.WithLogin;
import com.demoqa.models.auth.LoginResponseDto;
import com.demoqa.models.bookstore.UserBooksResponseDto;
import com.demoqa.pages.ProfilePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Удаление книги пользователя")
public class ProfileTests extends BaseTest {
    private BookstoreApi bookstoreApi;
    private BookstoreData bookstoreData;

    @BeforeEach
    void setUpUser() {
        LoginResponseDto loginResponseDto = AuthApi.loginWithDefaultUser();

        bookstoreData = new BookstoreData();

        bookstoreApi = new BookstoreApi(loginResponseDto.getToken(), loginResponseDto.getUserId());
        bookstoreApi.deleteAllUserBooks();
        bookstoreApi.addBooks(bookstoreData.getIsbn());
    }

    @Test
    @WithLogin
    @DisplayName("Удаление книги через UI")
    void deleteBookWithUiTest() {
        ProfilePage profilePage = new ProfilePage();
        profilePage.open()
                .deleteBookByIsbn(bookstoreData.getIsbn())
                .verifyListIsEmpty();

        UserBooksResponseDto userBooksResponseDto = bookstoreApi.getUserBooks();
        BookstoreAssertions.assertUserBooksEmpty(userBooksResponseDto);
    }

    @Test
    @DisplayName("Удаление книги через API")
    void deleteBookWithApiTest() {
        bookstoreApi.deleteBook(bookstoreData.getIsbn());

        UserBooksResponseDto userBooksResponseDto = bookstoreApi.getUserBooks();
        BookstoreAssertions.assertUserBooksEmpty(userBooksResponseDto);
    }
}
