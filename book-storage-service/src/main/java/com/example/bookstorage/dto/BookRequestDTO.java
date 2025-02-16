package com.example.bookstorage.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BookRequestDTO {
    @NotBlank(message = "ISBN cannot be empty!")
    private String ISBN;

    @NotBlank(message = "title cannot be empty!")
    private String title;
    private String genre;
    private String description;
    @NotBlank(message = "Author cannot be empty!")
    private String author;
}
