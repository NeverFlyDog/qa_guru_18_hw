package com.demoqa.models.bookstore;

import lombok.Data;

import java.util.List;

@Data
public class UserBooksResponseDto {
    private String userId;
    private String username;
    private List<BookDto> books;
}
