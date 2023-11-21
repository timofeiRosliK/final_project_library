package service;

import entity.Administrator;
import entity.User;
import org.mindrot.jbcrypt.BCrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class UserService {

    public  String hashingPassword(User user) {
        return BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
    }

    public boolean authorization(User user) throws SQLException, NoSuchAlgorithmException {
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/library";
        final String USER = "root";
        final String PASSWORD = "root";
        Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        Statement statement = connection.createStatement();
        String sql = "SELECT name, hashed_password from users ";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            String userName = rs.getString("name");
            String hashedPassword  = rs.getString("hashed_password");
            if(user.getUsername().equals(userName) && comparePasswords(user.getPassword(), hashedPassword)){
                return true;
            }
        }
        return false;
    }
    public static boolean comparePasswords(String inputPassword, String hashedPassword){
        return BCrypt.checkpw(inputPassword, hashedPassword);
    }





}
