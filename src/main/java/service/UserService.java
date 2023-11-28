package service;


import entity.User;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import static service.DbConfig.DATABASE_URL;
import static service.DbConfig.PASSWORD;
import static service.DbConfig.USER;

public class UserService {
    private static final String sql = "SELECT * from users where name = ?";

    public String hashingPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean authorization(User user) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, user.getUsername());
            ResultSet rs = preparedStatement.executeQuery();
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


    private static boolean comparePasswords(String inputPassword, String hashedPassword) {
        return BCrypt.checkpw(inputPassword, hashedPassword);
    }

    public String getEmailByUsername(String name) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement
                ( "SELECT email from users where name = ?")) {
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return rs.getString("email");
        }
    }
}
