package com.example.books.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


@Entity
@Table(name = "book")
@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@Data
public class BookEntity {

    @Id
    @Column(name = "isbn")
    @NotEmpty(message = "ISBN can`t be empty")
    @Pattern(regexp="^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})\n" +
            "[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)\n" +
            "(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$", message = "Wrong ISBN format")
    private String isbn;

    @Column(name = "title")
    @NotEmpty(message = "Title can`t be empty")
    private String title;

    @Column(name = "author")
    @NotEmpty(message = "author can`T be empty")
    private String author;
}
