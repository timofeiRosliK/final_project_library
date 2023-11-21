import entity.Administrator;
import entity.Book;
import entity.User;
import service.*;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class LibraryMain {
    public static void main(String[] args) throws SQLException, NoSuchAlgorithmException, IsNotAllowedToModifyCatalogueException {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        String password = scanner.nextLine();
        UserService userService = new UserService();
        AdministratorService administratorService = new AdministratorService();
        User user = new User(name, password);
        Administrator administrator = new Administrator(name, password);
        String inputHashedPasswordUser = userService.hashingPassword(user);
        String inputHashedPasswordAdmin = administratorService.hashingPassword(administrator);

        if(userService.authorization(user)){
            System.out.println("Authorization is succeed by user " + user.getUsername());
        }else if(administratorService.authorization(administrator)) {
            System.out.println("Authorization is succeed by admin " + administrator.getUsername());
        }else System.out.println("Log or password is invalid");
        AuthorService authorService = new AuthorService();
        BookService bookService = new BookService(authorService);
        List<Book> allBooks = bookService.getAllBooks();
        System.out.println(allBooks);
        boolean isModifyCatalogue = bookService.isModifyCatalogue(administrator, administratorService);
        System.out.println("isModifyCatalogue = " + isModifyCatalogue);
    }
    }

