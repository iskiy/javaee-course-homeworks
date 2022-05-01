package com.example.books.controller;

import com.example.books.DemoApplication;
import com.example.books.entities.BookEntity;
import com.example.books.repository.BookRepository;
import com.example.books.repository.OldBooksRepository;
import com.example.books.services.AbstractTest;
import com.example.books.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DatabaseSetup("/controller/controller-init.xml")
@DatabaseTearDown("/services/book-service/clean-up.xml")
class BookControllerIT extends AbstractTest {
    @LocalServerPort
    void savePort(final int port) {
        // save port of locally starter server during test
        RestAssured.port = port;
    }
    @Autowired
    ObjectMapper mapper;

    @Autowired
    private BookService bookService;

    @Test
    void showBooksTest() {
        List<BookEntity> responseList = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                    .get("show-books")
                .then()
                    .statusCode(200)
                    .extract()
                    .body()
                    .jsonPath()
                    .getList(".", BookEntity.class);

        assertEquals(3, responseList.size());
    }

    @Test
    void createBookTest() throws Exception {
        BookEntity book = new BookEntity("isbn4", "title4", "author4");
        final String jsonRequest = mapper.writeValueAsString(book);
        BookEntity responseBook = given()
                    .body(jsonRequest)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                    .post("/create-book")
                .then()
                    .statusCode(200)
                    .extract()
                    .body()
                    .as(BookEntity.class);

        assertEquals(responseBook, book);
        assertTrue(bookService.findAllBooks().contains(book));
    }

    @Test
    void findBookTest() {
        BookEntity book = new BookEntity("isbnaa", "titleaa", "authoraa");
        List<BookEntity> responseBooks = given()
                    .queryParam("title", "titleaa")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                    .get("/find-books")
                .then()
                    .statusCode(200)
                    .extract()
                    .body()
                    .jsonPath()
                    .getList(".", BookEntity.class);

        assertEquals(responseBooks, List.of(book));
    }
}