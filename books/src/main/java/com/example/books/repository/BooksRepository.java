package com.example.books.repository;

import com.example.books.entities.BookEntity;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Component
public class BooksRepository {
    private List<BookEntity> books;

    public BooksRepository(){
        books = new ArrayList<>();
    }

    public void addBooks(Collection<BookEntity> books){
        this.books.addAll(books);
    }

    public void addBook(BookEntity book){
        books.add(book);
    }

    public void clear() {
        books.clear();
    }
}
