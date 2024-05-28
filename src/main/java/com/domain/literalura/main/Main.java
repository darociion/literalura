package com.domain.literalura.main;

import com.domain.literalura.model.*;
import com.domain.literalura.repository.*;
import com.domain.literalura.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private final String BASE_URL = "https://gutendex.com/books/";
    private Scanner keyboard = new Scanner(System.in);
    private ApiConsulter apiConsulter = new ApiConsulter();
    private DataConverter dataConverter = new DataConverter();
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public Main(BookRepository bookRepository, AuthorRepository authorRepository) { this.bookRepository = bookRepository; this.authorRepository = authorRepository; }

    public void start() {
        var option = -1;

        while (option != 0) {
            var menu = """
                    \n
                    1 - Search book by title
                    2 - List registered books
                    3 - List registered authors
                    4 - List authors alive in a given year
                    5 - List books by language
                                        
                    0 - Exit
                    """;

            System.out.println(menu);
            option = keyboard.nextInt();
            keyboard.nextLine();

            switch (option) {
                case 1:
                    searchBookByTitle();
                    break;
                case 2:
                    listRegisteredBooks();
                    break;
                case 3:
                    listRegisteredAuthors();
                    break;
                case 4:
                    ListAuthorsAliveInAGivenYear();
                    break;
                case 5:
                    listBooksByLanguage();
                    break;
                case 0:
                    System.out.println("Closing the app...");
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private void searchBookByTitle() {
        System.out.println("\nEnter the name of the book you want to search:");
        var title = keyboard.nextLine();
        var json = apiConsulter.obtainData(BASE_URL + "?search=" + title.replace(" ", "%20"));
        var data = dataConverter.obtainData(json, Data.class);
        Optional<BookData> searchBook = data.results()
                .stream()
                .filter(b -> b.title().toLowerCase().contains(title.toLowerCase()))
                .findFirst();

        if (searchBook.isPresent()) {
            System.out.println("Libro encontrado");
            BookData bookData = searchBook.get();
            Book book = new Book(bookData);
            Author author = new Author(searchBook.get().author().get(0));
            List<Book> books = new ArrayList<>();
            books.add(book);
            author.setBooks(books);
            authorRepository.save(author);
            bookRepository.save(book);
        } else {
            System.out.println("Libro no encontrado");
        }
    }

    private void listRegisteredBooks() {
        System.out.println("\nRegistered books:");
        List<Book> books = bookRepository.findAll();
        books.forEach(System.out::println);
    }

    private void listRegisteredAuthors() {
        System.out.println("\nRegistered authors:");
        List<Author> authors = authorRepository.findAll();
        authors.forEach(System.out::println);
    }

    private void ListAuthorsAliveInAGivenYear() {

    }

    private void listBooksByLanguage() {

    }

}
