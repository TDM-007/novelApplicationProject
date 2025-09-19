package com.backenddev.novelapplication.Books;

import com.backenddev.novelapplication.execption.ApiNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.management.relation.RelationServiceNotRegisteredException;
import java.awt.print.Book;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private BookService service;

    @Autowired
    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping("allBooks")
    public ResponseEntity<List<Books>> getAllBooks(){
        try {
        return new ResponseEntity<>(service.getAllBooks(), HttpStatus.OK);
        }catch(Exception e){
            throw new ApiNotFoundException("Books not found", e);
        }
    }

    @GetMapping("book/{title}")
    public ResponseEntity<Books> getBookByTitle(@PathVariable String title){
        try {
            return new ResponseEntity<>( service.getBooksByTitle(title),HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/addBook",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addBook(@RequestPart("book") Books book,
                                     @RequestPart("pdf") MultipartFile pdf,
                                     @RequestPart("image") MultipartFile image){
        try{
            service.addBook(book, pdf, image);
            return new ResponseEntity<>("book added successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
