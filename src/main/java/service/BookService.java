package service;
import entity.Administrator;
import entity.Author;
import entity.Book;
import entity.BookType;
import entity.Genre;
import entity.User;
import lombok.AllArgsConstructor;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static service.DbConfig.*;


@AllArgsConstructor
public class BookService {


    private final AuthorService authorService;

    public List<Book> getAllBooks() throws SQLException {
        try(Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM books";
            ResultSet resultSet = statement.executeQuery(sql);
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                int bookId = resultSet.getInt("id");
                String title = resultSet.getString("title");
                Genre genre = Genre.valueOf(resultSet.getString("genre"));
                int authorId = resultSet.getInt("author_id");
                BookType bookType = BookType.valueOf(resultSet.getString("type"));
                Author author = authorService.getAuthor(authorId);
                Book book = new Book(bookId, title, author, genre, bookType);
                books.add(book);
            }
            resultSet.close();
            return books;
        }
    }

    public boolean isModifyCatalogue(Administrator administrator, AdministratorService administratorService)
            throws SQLException, NoSuchAlgorithmException, IsNotAllowedToModifyCatalogueException {
        if (!administratorService.authorization(administrator)){
            throw new IsNotAllowedToModifyCatalogueException("This user is not allowed to modify the catalogue");
        }
        return true;
    }

    public String sendNotificationToUser(User user) throws SQLException {
        try(Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from books WHERE title = ?"))
        {
            preparedStatement.setString(1, "Miracle morning");
            ResultSet resultSet = preparedStatement.executeQuery();
            StringBuilder notificationBuilder = new StringBuilder();
            UserService userService = new UserService();
            while (resultSet.next()) {
                notificationBuilder.append("title: ").append(resultSet.getString("title")).append("\n");
                notificationBuilder.append("genre: ").append(Genre.valueOf(resultSet.getString("genre"))).append("\n");
                notificationBuilder.append("author: ").append(authorService.getAuthor(resultSet.getInt("author_id"))).append("\n");
                notificationBuilder.append("bookType: ").append(BookType.valueOf(resultSet.getString("type"))).append("\n");
            }
            resultSet.close();


            return "Hello " + userService.getEmailByUsername(user.getUsername()) + "!" + "\n" + " Here is the the new book which is " +
                    notificationBuilder;
        }
    }
}

