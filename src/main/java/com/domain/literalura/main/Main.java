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
            BookData bookData = searchBook.get();

            if (!verifiedBookExistence(bookData)) {
                Book book = new Book(bookData);
                //AuthorData authorData = bookData.author();
                AuthorData authorData = bookData.author().get(0);
                Optional<Author> optionalAuthor = authorRepository.findByName(authorData.name());

                    if (optionalAuthor.isPresent()) {
                        Author existingAuthor = optionalAuthor.get();
                        List<Author> authors = new ArrayList<>();
                        authors.add(existingAuthor);
                        book.setAuthors(authors);
                        existingAuthor.getBooks().add(book);
                        authorRepository.save(existingAuthor);
                        bookRepository.save(book);
                    } else {
                        Author newAuthor = new Author(authorData);
                        List<Author> authors = new ArrayList<>();
                        authors.add(newAuthor);
                        book.setAuthors(authors);
                        List<Book> books = new ArrayList<>();
                        books.add(book);
                        newAuthor.setBooks(books);
                        authorRepository.save(newAuthor);
                        bookRepository.save(book);
                    }

            } else {
                System.out.println("Book already added in DB");
            }

        } else {
            System.out.println("Book not found");
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

    private boolean verifiedBookExistence(BookData bookData) {
        Book book = new Book(bookData);
        return bookRepository.verifiedBDExistence(book.getTitle());
    }

    private void ListAuthorsAliveInAGivenYear() {
        System.out.println("Enter the year you want to consult: ");
        var year = keyboard.nextInt();
        System.out.println("\nRegistered authors alive in " + year);
        List<Author> authors = authorRepository.findAuthorsAlive(year);

        if(!authors.isEmpty()) {
            authors.forEach(System.out::println);
        } else {
            System.out.println("No results, enter another year");
        }
    }

    private void listBooksByLanguage() {
        var option = -1;
        String language = "";


        System.out.println("Select the language you want to consult");
        var languagesMenu = """
               \n
               1 - English
               2 - French
               3 - German
               4 - Portuguese
               5 - Spanish
               """;

        System.out.println(languagesMenu);
        option = keyboard.nextInt();
        keyboard.nextLine();

        switch (option) {
            case 1:
                language = "en";
                break;
            case 2:
                language = "fr";
                break;
            case 3:
                language = "de";
                break;
            case 4:
                language = "pt";
                break;
            case 5:
                language = "es";
                break;
            default:
                System.out.println("Invalid option");
        }

        System.out.println("\nRegistered books:");
        List<Book> books = bookRepository.findBooksByLanguage(language);

        if (!books.isEmpty()) {
            books.forEach(System.out::println);
        } else {
            System.out.println("No results, select another language");
        }
    }

}
