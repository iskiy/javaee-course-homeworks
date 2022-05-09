package com.example.books.repository;

import com.example.books.entities.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, String> {
    List<BookEntity> findAll();

    BookEntity saveAndFlush(BookEntity book);

    BookEntity findByIsbn(String isbn);

    long count();

    Page<BookEntity> findAll(Pageable pageable);
    @Query("SELECT b FROM BookEntity b " +
            "where b.isbn LIKE:param" +
            " OR b.title LIKE:param OR b.author LIKE:param")
    List<BookEntity> findBookWhereISBNOrTitleOrAuthor(@Param("param") String param);

    @Query(value = "SELECT b FROM BookEntity b " +
            "where b.isbn LIKE:param" +
            " OR b.title LIKE:param OR b.author LIKE:param")
    Page<BookEntity> findBookWhereISBNOrTitleOrAuthorByPage(@Param("param") String param, Pageable pageable);


    @Query("SELECT count(b) FROM BookEntity b " +
            "where b.isbn LIKE:param" +
            " OR b.title LIKE:param OR b.author LIKE:param")
    Integer findBookWhereISBNOrTitleOrAuthorAmount(@Param("param") String param);
}
