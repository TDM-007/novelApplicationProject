package com.backenddev.novelapplication.Books;

import com.backenddev.novelapplication.execption.BadRequestException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Book;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private BookRepository repo;
    public BookService(BookRepository repo) {
        this.repo = repo;
    }

    public List<Books> getAllBooks() {
        List<Books> books = repo.findAll();
        return books;
    }

    public void addBook(Books book, MultipartFile pdf, MultipartFile image){
        //validate the title
        if (book.getTitle() == null || book.getTitle().equals("")) {
            throw new RuntimeException("title cannot be empty");
        }
        if (book.getISBN() == null || book.getISBN().equals("")) {
            throw new BadRequestException("ISBN cannot be empty");
        }

        //validate and set pdf
        if(pdf.isEmpty()){
            throw new BadRequestException("file cannot be empty");
        }
        if (!pdf.getContentType().equals("application/pdf")) {
            throw new BadRequestException("file type should be pdf");
        }

        //validate image
        if (image.isEmpty()){
            throw new BadRequestException("image cannot be empty");
        }

        //check already existing files

        try {
            book.setPdfContent(pdf.getBytes()); //convert file to pdf
            book.setBookImage(image.getBytes());//convert file to image
            book.setPdfFileName(pdf.getOriginalFilename());//
            book.setImageFileName(image.getOriginalFilename());
        }catch (Exception e){
            throw new BadRequestException("file type not supported", e);
        }

        repo.save(book);
    }

    public Books getBooksByTitle(String title) {
        if (title == null || title.equals("")) {
            throw new BadRequestException("title cannot be empty");
        }
        if (repo.existsByTitle(title)){
            throw new BadRequestException("title already exists");
        }

        return repo.getByTitle(title);
    }
}
