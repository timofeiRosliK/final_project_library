package service;

import entity.Author;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AuthorService {
    public Author getAuthor(int id) throws SQLException {
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/library";
        final String USER = "root";
        final String PASSWORD = "root";

        Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        String sql = "SELECT * from authors WHERE author_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,id);
        ResultSet resultSet = statement.executeQuery();
        Author author = new Author();
        while (resultSet.next()){
            id = resultSet.getInt("author_id");
            String name = resultSet.getString("name");
            String surname = resultSet.getString("surname");
            author.setId(id);
            author.setName(name);
            author.setSurname(surname);
        }
        return author;
    }

}
