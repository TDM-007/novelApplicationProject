package com.backenddev.novelapplication.Books;

import com.backenddev.novelapplication.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "books")
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long ISBN;

    @Column (nullable = false)
    private String author;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Long yearPublished;

    @Column(nullable = false)
    private LocalDate dateUploaded = LocalDate.now();

    private String category;

    @Lob //marks this as a large object
    private byte[] pdfContent;
    @Column
    private String pdfFileName;

    @Lob
    private byte[] bookImage;

    @Column
    private String imageFileName;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn( name = "user_id", nullable = false)
//    private User user;



}
