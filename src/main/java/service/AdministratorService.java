package service;

import entity.Administrator;
import entity.User;
import org.mindrot.jbcrypt.BCrypt;

import java.security.NoSuchAlgorithmException;
import java.sql.*;

import static service.DbConfig.*;


public class AdministratorService {
    private static final String sql = "SELECT * from administrators where name = ?";
    public String hashingPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


    public boolean authorization(Administrator administrator) throws SQLException{
        Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,administrator.getUsername());
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            String administratorName = rs.getString("name");
            String hashedPassword = rs.getString("hashed_password");
            if (administrator.getUsername().equals(administratorName) && comparePasswords((administrator.getPassword()),
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
