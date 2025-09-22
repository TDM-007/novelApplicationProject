package com.backenddev.novelapplication.dtos;

// this dto is for updating a book only.
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookRequestDto {
    private byte[] pdfContent;
    private byte[] bookImage;
}
