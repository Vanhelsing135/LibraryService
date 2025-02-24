package com.example.bookstorage.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTO {
    private String ISBN;
    private String title;
    private String genre;
    private String description;
    private String Author;
}
