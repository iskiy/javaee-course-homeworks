package com.example.books.services;


import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DatabaseSetup("/services/book-service/init.xml")
@DatabaseTearDown("/services/book-service/clean-up.xml")
class BookServiceTest extends AbstractTest{
    @Autowired
    private BookService bookService;

    @Test
    @ExpectedDatabase(value = "/services/book-service/expected-after-create.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    void createBookTest() {
        bookService.createBook("d4", "title4", "author4");
    }

    @Test
    void findAllBooksTest(){
        assertEquals(3, bookService.findAllBooks().size());
    }

    @Test
    void findBookByISBNTest(){
        assertEquals("title1", bookService.findByISBN("a1").getTitle());
    }

    @Test
    void findBookWhereISBNOrTitleOrAuthor(){
        assertEquals(1, bookService.findBookWhereISBNOrTitleOrAuthor("author2").size());
    }
}