package com.example.books.repository;

import com.example.books.dto.BookDto;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Component
public class BooksRepository {
    private List<BookDto> books;

    public BooksRepository(){
        books = new ArrayList<>();
    }

    public void addBooks(Collection<BookDto> books){
        this.books.addAll(books);
    }

    public void addBook(BookDto book){
        books.add(book);
    }

    public void clear() {
        books.clear();
    }
}
