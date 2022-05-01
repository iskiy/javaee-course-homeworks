package com.example.books.controller;

import com.example.books.entities.BookEntity;
import com.example.books.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private int pageSize = 10;

    @GetMapping("/book/{isbn}")
    public String bookForm(@PathVariable("isbn") String isbn, Model model){
        BookEntity book = bookService.findByISBN(isbn);
        if(book == null){
            model.addAttribute("isbn", isbn);
            return "book-error";
        }
        model.addAttribute("book", book);
        return "book";
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
    public List<BookEntity> showBooks(@RequestParam(name = "page", required = false) Integer page){

        if(page == null) return bookService.findAllBooks();
        return bookService.findPage(PageRequest.of(page - 1, pageSize));
    }

    @ResponseBody
    @GetMapping("/find-books")
    public List<BookEntity> findBooks(@RequestParam("title") final String title){
        return bookService.findBookWhereISBNOrTitleOrAuthor(title);
    }

    @ResponseBody
    @GetMapping("/amount")
    public Long getBooksAmount(){
        Long pageAmount =(bookService.amount()/pageSize) + 1;
        return (bookService.amount()/pageSize) + 1;
    }
}
