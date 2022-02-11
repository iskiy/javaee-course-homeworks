package com.example.books.controller;

import com.example.books.dto.BookDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class BookController {

    ArrayList<BookDto> books = new ArrayList<>();

    @GetMapping("/create-book")
    public String bookForm(){
        return "create-book";
    }

    @PostMapping("/create-book")
    public String saveBook(final BookDto bookDto, final Model model){
        books.add(bookDto);
        return "redirect:show-books";
    }

    @GetMapping("/show-books")
    public String showBooks(final Model model){
        System.out.println(books);
        model.addAttribute("books", books);
        return "show-books";
    }
}
