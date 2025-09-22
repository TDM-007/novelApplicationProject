package com.backenddev.novelapplication.Books;

import com.backenddev.novelapplication.dtos.BookRequestDto;
import com.backenddev.novelapplication.execption.ApiNotFoundException;
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

    public void updateBook(String title, Books book, MultipartFile pdf, MultipartFile image) {
        //search the database for a book first.
        Books foundBook = repo.getByTitle(title);
        //check if the title is null
        if (title.isEmpty()) {
            throw new BadRequestException("title cannot be empty");
        }
        //check if the book exist in the database.
        if (repo.existsByTitle(title)) {
            //convert the files to pdf
            try {
                foundBook.setPdfContent(pdf.getBytes());
                foundBook.setBookImage(image.getBytes());
            }catch (Exception e){
                throw new BadRequestException("file type not supported");
            }
            // update the found book to the new book.
            foundBook.setPdfContent(book.getPdfContent());
            foundBook.setBookImage(book.getBookImage());

            //save the updated books
            Books updatedBook = repo.save(foundBook);

        }
        // other possibilities.
        throw new ApiNotFoundException("unable to update book");
    }

    public void deleteBook(String title) {
        if (title == null || title.equals("")) {
            throw new BadRequestException("title cannot be empty");
        }
        if (repo.existsByTitle(title)) {
            repo.deleteByTitle(title);
        }
        throw new BadRequestException("unable to delete book");
    }
}
