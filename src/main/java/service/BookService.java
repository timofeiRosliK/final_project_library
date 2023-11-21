package service;

import entity.*;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class BookService {

    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/library";
    static final String USER = "root";
    static final String PASSWORD = "root";

    private final AuthorService authorService;

    public List<Book> getAllBooks() throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        Statement statement = connection.createStatement();
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
        statement.close();
        connection.close();
        return books;
    }

    public boolean isModifyCatalogue( Administrator administrator, AdministratorService administratorService)
            throws SQLException, NoSuchAlgorithmException, IsNotAllowedToModifyCatalogueException {
//            throws IsNotAllowedToModifyCatalogueException, SQLException {
//        final String DATABASE_URL = "jdbc:mysql://localhost:3306/library";
//        final String USER = "root";
//        final String PASSWORD = "root";
//        Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
//        Statement statement = connection.createStatement();
//        String sql = "SELECT name, hashed_password from administrators ";
//        ResultSet rs = statement.executeQuery(sql);
//        while (rs.next()) {
//            String adminName = rs.getString("name");
//            String hashedPassword = rs.getString("hashed_password");
        if (!administratorService.authorization(administrator)) {
            throw new IsNotAllowedToModifyCatalogueException("This user is not allowed to modify the catalogue");
        }
        return true;


    }
}

