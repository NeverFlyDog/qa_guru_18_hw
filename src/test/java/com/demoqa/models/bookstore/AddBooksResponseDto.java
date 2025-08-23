package com.demoqa.models.bookstore;

import lombok.Data;

import java.util.List;

@Data
public class AddBooksResponseDto {
    private List<BookDto> books;
}
