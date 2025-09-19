package com.backenddev.novelapplication.Books;

//import com.backenddev.novelapplication.Books.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Books, Long> {
    boolean existsByTitle(String title);

    Books getByTitle(String title);
}
