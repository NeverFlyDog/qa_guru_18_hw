package com.demoqa.data;

import lombok.Getter;

import java.util.List;
import java.util.Random;

@Getter
public class BookstoreData {
    private static final List<String> ISBN_LIST = List.of(
            "9781449325862",
            "9781449331818",
            "9781449337711",
            "9781449365035",
            "9781491904244",
            "9781491950296",
            "9781593275846",
            "9781593277574"
    );

    private final String isbn;

    public BookstoreData() {
        this.isbn = getRandomIsbn();
    }

    private static String getRandomIsbn() {
        return ISBN_LIST.get(new Random().nextInt(ISBN_LIST.size()));
    }
}
