package service;

import entity.Administrator;
import entity.User;
import org.mindrot.jbcrypt.BCrypt;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Scanner;

public class AdministratorService {

    public String hashingPassword(Administrator administrator) {
        return BCrypt.hashpw(administrator.getPassword(), BCrypt.gensalt());
    }

    public boolean authorization(Administrator administrator) throws SQLException, NoSuchAlgorithmException {
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/library";
        final String USER = "root";
        final String PASSWORD = "root";
        Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        Statement statement = connection.createStatement();
        String sql = "SELECT name, hashed_password from administrators ";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            String administratorName = rs.getString("name");
            String hashedPassword = rs.getString("hashed_password");
            if (administrator.getUsername().equals(administratorName) && comparePasswords(administrator.getPassword(),
                    hashedPassword)) {
                return true;
            }
        }
        return false;
    }

    public static boolean comparePasswords(String inputPassword, String hashedPassword) {
        return BCrypt.checkpw(inputPassword, hashedPassword);
    }


}
