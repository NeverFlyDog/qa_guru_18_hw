package com.demoqa.models.bookstore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddBooksRequestDto {
    private String userId;
    private List<BookDto> collectionOfIsbns;
}
