package com.backenddev.novelapplication.dtos;

//for getmapping.

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookResponseDto {
    private Long isbn;
    private String title;
    private String author;
    private Long yearPublished;
    private LocalDate dateUploaded;
    private String category;
    private byte[] pdfContent;
    private String pdfFileName;
    private byte[] bookImage;
    private String imageFileName;
}
