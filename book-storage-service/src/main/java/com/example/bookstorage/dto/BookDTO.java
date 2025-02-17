package com.example.bookstorage.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BookDTO {
    private String ISBN;
    private String title;
    private String genre;
    private String description;
    private String Author;
}
