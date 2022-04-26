package com.example.books.controller;

import com.example.books.services.BookService;
import com.example.books.entities.BookEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;


    @GetMapping("/book/{isbn}")
    public String bookForm(@PathVariable("isbn") String isbn, Model model){
        try {
            BookEntity book = bookService.findByISBN(isbn);
            model.addAttribute("book", book);
            return "book";
        } catch (NoResultException e){
            model.addAttribute("ISBN", isbn);
            return "book-error";
        }
    }

    @GetMapping("/books")
    public String booksForm(){
        return "books";
    }

    @RequestMapping(value = "/create-book", method = RequestMethod.POST)
    public ResponseEntity<BookEntity> saveBook(
            @RequestBody final BookEntity book){
        bookService.createBook(book.getIsbn(), book.getTitle(), book.getAuthor());
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @ResponseBody
    @GetMapping("/show-books")
    public List<BookEntity> showBooks(){
        return bookService.findAllBooks();
    }

    @ResponseBody
    @GetMapping("/find-books")
    public List<BookEntity> findBooks(@RequestParam("title") final String title){
        return bookService.findBookWhereISBNOrTitleOrAuthor(title);
    }
}
