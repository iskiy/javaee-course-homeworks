package com.example.books.controller;

import com.example.books.DemoApplication;
import com.example.books.dto.BookDto;
import com.example.books.repository.BooksRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
class BookControllerIT {
    @LocalServerPort
    void savePort(final int port) {
        // save port of locally starter server during test
        RestAssured.port = port;
    }
    @Autowired
    ObjectMapper mapper;

    @Autowired
    BooksRepository books;

    private List<BookDto> showBooksList = Arrays.asList(
            new BookDto("isbn1", "title1", "author1"),
            new BookDto("isbnaa", "titleaa", "authoraa"),
            new BookDto("isbnbb", "titlebb", "authorbb"));

    @Test
    void showBooksTest() {
        books.addBooks(showBooksList);

        List<BookDto> responseList = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                    .get("show-books")
                .then()
                    .statusCode(200)
                    .extract()
                    .body()
                    .jsonPath()
                    .getList(".", BookDto.class);

        assertEquals(responseList, showBooksList);
        books.clear();
    }

    @Test
    void createBookTest() throws Exception {
        books.addBooks(showBooksList);
        BookDto book = new BookDto("isbn4", "title4", "author4");
        final String jsonRequest = mapper.writeValueAsString(book);
        BookDto responseBook = given()
                    .body(jsonRequest)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                    .post("/create-book")
                .then()
                    .statusCode(200)
                    .extract()
                    .body()
                    .as(BookDto.class);

        assertEquals(responseBook, book);
        assertTrue(books.getBooks().contains(book));
        books.clear();
    }

    @Test
    void findBookTest() {
        books.addBooks(showBooksList);
        BookDto book = new BookDto("isbnaa", "titleaa", "authoraa");
        List<BookDto> responseBooks = given()
                    .queryParam("title", "titleaa")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                    .get("/find-books")
                .then()
                    .statusCode(200)
                    .extract()
                    .body()
                    .jsonPath()
                    .getList(".", BookDto.class);

        assertEquals(responseBooks, List.of(book));
        books.clear();
    }
}