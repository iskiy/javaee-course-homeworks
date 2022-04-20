package com.example.books.controller;

import com.example.books.dto.BookDto;
import com.example.books.repository.BooksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BooksRepository books;

    @GetMapping("/books")
    public String booksForm(){
        return "books";
    }

    @RequestMapping(value = "/create-book", method = RequestMethod.POST)
    public ResponseEntity<BookDto> saveBook(
            @RequestBody final BookDto bookDto){
        books.addBook(bookDto);
        return ResponseEntity.status(HttpStatus.OK).body(bookDto);
    }

    @ResponseBody
    @GetMapping("/show-books")
    public List<BookDto> showBooks(){
        return books.getBooks();
    }

    @ResponseBody
    @GetMapping("/find-books")
    public List<BookDto> findbooks(@RequestParam("title") final String title){
        System.out.println(title);
            Predicate<BookDto> byTitle = bookDto -> bookDto.getTitle().contains(title);
            return books.getBooks()
                    .stream()
                    .filter(byTitle)
                    .collect(Collectors.toList());
    }
}
