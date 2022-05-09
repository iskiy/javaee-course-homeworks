package com.example.books.services;

import com.example.books.entities.BookEntity;
import com.example.books.entities.UserEntity;
import com.example.books.repository.BookRepository;
import com.example.books.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;


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
    public BookEntity addBookToFavouriteByISBN(String isbn){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BookEntity bookEntity = bookRepository.getById(isbn);
        System.out.println("ISBN: " + isbn);
        System.out.println("book:" + bookEntity);
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            UserEntity user = userRepository.findByLogin(username).orElse(null);
            if(!user.getFavouriteBooks().contains(bookEntity)){
                System.out.println("Favourites size: " + user.getFavouriteBooks().size());
                System.out.println("List of favourite books:" + user.getFavouriteBooks());
                user.getFavouriteBooks().add(bookEntity);
            }

            return bookEntity;

        }
        else return null;
    }

    @Transactional
    public List<BookEntity> findBookWhereISBNOrTitleOrAuthorByPage(String keyword,PageRequest pr) {
        if(keyword.equals("")){
            return findPage(pr);
        }
        return bookRepository.findBookWhereISBNOrTitleOrAuthorByPage(keyword, pr).stream().collect(Collectors.toList());
    }

//    public List<BookEntity> findBookWhereISBNOrTitleOrAuthorAmount(String keyword) {
//        return bookRepository.findBookWhereISBNOrTitleOrAuthor(keyword, pr).stream().collect(Collectors.toList());
//    }

    @Transactional
    public BookEntity findByISBN(String isbn) {
        return bookRepository.findByIsbn(isbn);

    }


    @Transactional
    public BookEntity removeFromFavourites(String isbn){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            UserEntity user = userRepository.findByLogin(username).orElse(null);
            BookEntity bookEntity = bookRepository.getById(isbn);
            if(bookEntity == null) return null;
            if(user.getFavouriteBooks().contains(bookEntity)){
                user.getFavouriteBooks().remove(bookEntity);
                return bookEntity;
            }
            else {
                return null;
            }
        }
        else return null;
    }

    @Transactional
    public List<BookEntity> getFavouriteBooks(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            UserEntity user = userRepository.findByLogin(username).orElse(null);
            return user.getFavouriteBooks();
        }
        return null;
    }

    @Transactional
    public long queryAmount(String keyword){ return bookRepository.findBookWhereISBNOrTitleOrAuthorAmount(keyword);};

    @Transactional
    public long amount(){
        return bookRepository.count();
    }
}