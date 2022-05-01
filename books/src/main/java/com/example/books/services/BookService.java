package com.example.books.services;

import com.example.books.entities.BookEntity;
import com.example.books.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.h2.mvstore.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    @Transactional
    public BookEntity createBook(String isbn, String title, String author) {
        return bookRepository.saveAndFlush(new BookEntity(isbn, title, author));
    }

    @Transactional
    public List<BookEntity> findAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    public List<BookEntity> findPage(PageRequest pr){
        return bookRepository.findAll(pr).stream().collect(Collectors.toList());
    }

    @Transactional
    public List<BookEntity> findBookWhereISBNOrTitleOrAuthor(String searchText) {
        return bookRepository.findBookWhereISBNOrTitleOrAuthor(searchText);
    }

    @Transactional
    public List<BookEntity> findBookWhereISBNOrTitleOrAuthorByPage(PageRequest pr, String searchText) {
        return bookRepository.findBookWhereISBNOrTitleOrAuthor(searchText);
    }

    @Transactional
    public BookEntity findByISBN(String isbn) {
        return bookRepository.findByIsbn(isbn);

    }

    @Transactional
    public long amount(){
        return bookRepository.count();
    }
}