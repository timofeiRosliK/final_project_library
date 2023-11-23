package service;


import entity.User;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserService {

    public String hashingPassword(User user) {
        return BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
    }

    public boolean authorization(User user) throws SQLException{
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/library";
        final String USER = "root";
        final String PASSWORD = "root";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            Statement statement = connection.createStatement()){
            String sql = "SELECT name, hashed_password from users ";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String userName = rs.getString("name");
                String hashedPassword = rs.getString("hashed_password");
                if (user.getUsername().equals(userName) && comparePasswords(user.getPassword(), hashedPassword)) {
                    return true;
                }
            }
            return false;
        }
    }


    public static boolean comparePasswords(String inputPassword, String hashedPassword) {
        return BCrypt.checkpw(inputPassword, hashedPassword);
    }

    public String getEmail(User user) throws SQLException {
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/library";
        final String USER = "root";
        final String PASSWORD = "root";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement
                ( "SELECT email from users where name = ?")) {
            preparedStatement.setString(1, user.getUsername());
            ResultSet rs = preparedStatement.executeQuery();
            StringBuilder stringBuilder = new StringBuilder();
            while (rs.next()) {
                String email = rs.getString("email");
                stringBuilder.append(email);
            }
            return stringBuilder.toString();
        }
    }
}
