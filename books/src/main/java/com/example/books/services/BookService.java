package com.example.books.services;

import com.example.books.entities.BookEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final EntityManager entityManager;

    @Transactional
    public BookEntity createBook(String isbn, String title, String author) {
        BookEntity book = new BookEntity();
        book.setIsbn(isbn);
        book.setTitle(title);
        book.setAuthor(author);

        return entityManager.merge(book);
    }

    @Transactional
    public List<BookEntity> findAllBooks() {
        return entityManager
                .createQuery("SELECT b FROM BookEntity b", BookEntity.class)
                .getResultList();
    }

    ;

    @Transactional
    public List<BookEntity> findBookWhereISBNOrTitleOrAuthor(String searchText) {
        return entityManager.createQuery("SELECT b FROM BookEntity b " +
                        "where b.isbn LIKE:query" +
                        " OR b.title LIKE:query OR b.author LIKE:query", BookEntity.class)
                .setParameter("query", '%' + searchText + '%')
                .getResultList();
    }

    @Transactional
    public BookEntity findByISBN(String isbn) {
        return entityManager.createQuery("SELECT b FROM BookEntity b WHERE b.isbn = :isbn", BookEntity.class)
                .setParameter("isbn", isbn)
                .getSingleResult();

    }
}