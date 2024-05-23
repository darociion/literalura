package com.domain.literalura.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private LocalDate birth_year;
    private LocalDate death_year;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> books = new ArrayList<>();

    public Author() {}

    public Author(AuthorData authorData) {
        this.name = authorData.name();
        this.birth_year = authorData.birth_year();
        this.death_year = authorData.death_year();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(LocalDate birth_year) {
        this.birth_year = birth_year;
    }

    public LocalDate getDeath_year() {
        return death_year;
    }

    public void setDeath_year(LocalDate death_year) {
        this.death_year = death_year;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = new ArrayList<>();
        books.forEach(b -> {
            b.setAuthors(Collections.singletonList(this));
            this.books.add(b);
        });
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", birth_year=" + birth_year +
                ", death_year=" + death_year +
                ", books= " + books +
                '}';
    }
}
