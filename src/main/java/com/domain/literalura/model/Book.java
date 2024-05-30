package com.domain.literalura.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "book_author", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;
    private List<String> languages;
    private int downloads;

    public Book() { }

    public Book(BookData bookData) {
        this.title = bookData.title();
        this.languages = bookData.languages();
        this.downloads = bookData.downloads();
        this.authors = bookData.author().stream().map(Author::new).toList();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    @Override
    public String toString() {
        String author = authors.get(0).getName();
        return "\n---------------------" +
                "\nTitle: " + title +
                "\nAuthor: " + author +
                "\nLanguages: " + languages +
                "\nDownloads: " + downloads +
                "\n---------------------";
    }
}
