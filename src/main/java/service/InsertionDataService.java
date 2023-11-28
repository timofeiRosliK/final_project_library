package service;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

import static service.DbConfig.DATABASE_URL;
import static service.DbConfig.PASSWORD;
import static service.DbConfig.USER;

public class InsertionDataService {
    private static final String sql= "INSERT INTO administrators (name, hashed_password, email) VALUES(?,?,?)";
    private static final String sqlUser= "INSERT INTO users(name, hashed_password, email) VALUES(?,?,?)";
    public String hashingPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    public void insertDataAdmin(String name, String password, String email) throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL,USER,PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2,  hashingPassword(password));
        preparedStatement.setString(3, email);
        preparedStatement.execute();
        connection.close();

    }
    public void insertDataUser(String name, String password, String email) throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL,USER,PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(sqlUser);
        preparedStatement.setString(1, "Timofei");
        preparedStatement.setString(2,  hashingPassword(password));
        preparedStatement.setString(3, email);
        preparedStatement.execute();
        connection.close();

    }
}
