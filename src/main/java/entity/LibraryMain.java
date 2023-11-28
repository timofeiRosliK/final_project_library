package entity;

import service.*;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class LibraryMain {

    private static Logger log = Logger.getLogger(LibraryMain.class.getName());

    public static void main(String[] args) throws SQLException, NoSuchAlgorithmException, IsNotAllowedToModifyCatalogueException {
        Scanner scanner = new Scanner(System.in);
        log.info("Input name");
        String name = scanner.nextLine();
        log.info("Input password");
        String password = scanner.nextLine();
        UserService userService = new UserService();
        InsertionDataService insertionDataService = new InsertionDataService();
        AdministratorService administratorService = new AdministratorService();
        User user = new User(name, password);
        Administrator administrator = new Administrator(name, password);
        AuthorService authorService = new AuthorService();
        BookService bookService = new BookService(authorService);
        log.info("Input operation");
        String operation = scanner.nextLine();
        switch (operation) {
            case "Email":
                String email = userService.getEmailByUsername(user.getUsername());
                log.info(email);
                break;
            case "Authorization":
                if (userService.authorization(user)) {
                    log.info("Authorization is succeed by user " + user.getUsername());
                } else if (administratorService.authorization(administrator)) {
                    log.info("Authorization is succeed by admin " + administrator.getUsername());
                } else log.info("Log or password is invalid");
                break;
            case "Books":
                List<Book> allBooks = bookService.getAllBooks();
                log.info("All books" + allBooks);
                break;
            case "Notification":
                String message = bookService.sendNotificationToUser(user);
                log.info("message! = " + message);
                break;
            case "Modification":
                boolean isModifyCatalogue = bookService.isModifyCatalogue(administrator, administratorService);
                log.info("isModifyCatalogue = " + isModifyCatalogue);
                break;
            case "InsertionAdmin":
                log.info("Input email");
                String insertEmail = scanner.nextLine();
                Administrator administratorWithEmail = new Administrator(name, insertEmail, password);
                String hashingPassword = insertionDataService.hashingPassword(administratorWithEmail.getPassword());
                insertionDataService.insertDataAdmin(administratorWithEmail.getUsername(), hashingPassword, administratorWithEmail.getEmail());
                break;
            case "InsertionUser":
                log.info("Input email");
                String insertEmail1 = scanner.nextLine();
                User userWithEmail = new User(name, insertEmail1, password);
                String hashingPassword1 = insertionDataService.hashingPassword(userWithEmail.getPassword());
                insertionDataService.insertDataUser(userWithEmail.getUsername(), hashingPassword1, userWithEmail.getEmail());
                break;
            default:
                log.info("Something is wrong");
                break;
        }
    }
}

