package com.demoqa.api;

import com.demoqa.models.bookstore.*;
import io.qameta.allure.Step;

import java.util.Arrays;

public class BookstoreApi extends BaseApi {
    private final String token;
    private final String userId;

    public BookstoreApi(String token, String userId) {
        this.token = token;
        this.userId = userId;
    }

    @Step("Добавляем пользователю книги с ISBN {isbns}")
    public AddBooksResponseDto addBooks(String... isbns) {
        AddBooksRequestDto requestDto = new AddBooksRequestDto();
        requestDto.setUserId(userId);
        requestDto.setCollectionOfIsbns(Arrays.stream(isbns).map(BookDto::new).toList());

        return spec(token)
                .body(requestDto)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .statusCode(201)
                .extract().as(AddBooksResponseDto.class);
    }

    @Step("Удаляем у пользователя книгу с ISBN [{isbn}]")
    public void deleteBook(String isbn) {
        DeleteBookRequestDto requestDto = new DeleteBookRequestDto();
        requestDto.setUserId(userId);
        requestDto.setIsbn(isbn);

        spec(token)
                .body(requestDto)
                .when()
                .delete("/BookStore/v1/Book")
                .then()
                .statusCode(204);
    }

    @Step("Удаляем все книги пользователя")
    public void deleteAllUserBooks() {
        spec(token)
                .queryParam("UserId", userId)
                .when()
                .delete("/BookStore/v1/Books/")
                .then()
                .statusCode(204);
    }

    @Step("Получаем список книг пользователя")
    public UserBooksResponseDto getUserBooks() {
        return spec(token)
                .when()
                .get("/Account/v1/User/" + userId)
                .then()
                .statusCode(200)
                .extract().as(UserBooksResponseDto.class);
    }
}
