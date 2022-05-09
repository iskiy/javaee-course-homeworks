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

import java.awt.print.Book;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private int pageSize = 10;

    @GetMapping("/")
    public String index(){
        return "redirect:books";
    }



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
        System.out.println("Create book: " + book);
        bookService.createBook(book.getIsbn(), book.getTitle(), book.getAuthor());
        System.out.println("ALl book: " + bookService.findAllBooks());
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @RequestMapping(value = "/add-favourite", method = RequestMethod.POST)
    public ResponseEntity<BookEntity> addFavourite(
            @RequestBody BookEntity book){
        System.out.println("BOOK: " + book);
        bookService.addBookToFavouriteByISBN(book.getIsbn());
        System.out.println("Book was added to liked");
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @GetMapping("/show-favourites")
    public String showFavourites(Model model){
        List<BookEntity> books = bookService.getFavouriteBooks();
        System.out.println(books);
        System.out.println("BOOKS size: " + books.size());
        model.addAttribute("books",books);
        return "show-favourites";
    }

    @PostMapping("/favourite-delete")
    public ResponseEntity<BookEntity> deleteBookFromFavourite(@RequestBody BookEntity book){
        System.out.println("Delete from favourite book: " + book);
        bookService.removeFromFavourites(book.getIsbn());
        return  ResponseEntity.status(200).body(book);
    }

    @ResponseBody
    @GetMapping("/show-books")
    public List<BookEntity> showBooks(@RequestParam(name = "page", required = false) Integer page,
                                      @RequestParam(name = "keyword", required = false) String keyword){
        if(keyword == null) keyword = "";
        System.out.println(keyword);
        System.out.println(page);

        if(page == null) return bookService.findAllBooks();
        System.out.println(bookService.findBookWhereISBNOrTitleOrAuthorByPage(keyword, PageRequest.of(page - 1, pageSize)));
        return bookService.findBookWhereISBNOrTitleOrAuthorByPage(keyword, PageRequest.of(page - 1, pageSize));
//        return bookService.findPage(PageRequest.of(page - 1, pageSize));
    }

//    @ResponseBody
//    @GetMapping("/find-books")
//    public List<BookEntity> findBooks(@RequestParam("title") final String title){
//        return bookService.findBookWhereISBNOrTitleOrAuthor(title);
//    }

    @ResponseBody
    @GetMapping("/amount")
    public Long getBooksAmount(@RequestParam(name = "keyword", required = false) String keyword){
        System.out.println("getBOokAmount");
        if(keyword == null) keyword = "";
        return (bookService.queryAmount(keyword)/pageSize) + 1;
//        Long pageAmount =(bookService.amount()/pageSize) + 1;
//        return (bookService.amount()/pageSize) + 1;
    }
}
