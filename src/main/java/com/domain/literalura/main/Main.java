package com.domain.literalura.main;

import com.domain.literalura.repository.BookRepository;
import com.domain.literalura.service.ApiConsulter;
import java.util.Scanner;

public class Main {
    private final String BASE_URL = "https://gutendex.com/books/";
    private Scanner keyboard = new Scanner(System.in);
    private ApiConsulter apiConsulter = new ApiConsulter();
    private BookRepository bookRepository;
    public Main(BookRepository repository) { this.bookRepository = repository; }
    public void start() {
        var option = -1;
        while (option != 0) {
            var menu = """
                    1 - Buscar series 
                    2 - Buscar episodios
                    3 - Mostrar series buscadas
                    4 - Buscar series por titulo
                    5 - Top 5 mejores series
                    6 - Buscar series por categoria
                    7 - Buscar series por cantidad de temporadas y evaluacion
                    8 - Buscar episodios por titulo
                    9 - Top 5 mejores episodios por serie
                    
                    0 - Salir
                    """;

            System.out.println(menu);
            option = keyboard.nextInt();
            keyboard.nextLine();

            switch (option) {
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                case 6:

                    break;
                case 7:

                    break;
                case 8:

                    break;
                case 9:

                    break;
                case 0:
                    System.out.println("Closing the app...");
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }
}
