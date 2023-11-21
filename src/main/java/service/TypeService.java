//package service;
//
//import entity.Author;
//import entity.BookType;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.sql.*;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//
//public class TypeService {
//    public void getType(int id) throws SQLException {
//        final String DATABASE_URL = "jdbc:mysql://localhost:3306/library";
//        final String USER = "root";
//        final String PASSWORD = "root";
//
//        Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
//        String sql = "SELECT * from type WHERE id = ?";
//        PreparedStatement statement = connection.prepareStatement(sql);
//        statement.setInt(1, id);
//        ResultSet resultSet = statement.executeQuery();
//
//        while (resultSet.next()) {
//            id = resultSet.getInt("id");
//            BookType type = BookType.valueOf(resultSet.getString("type"));
//            System.out.println("id = " + id);
//            System.out.println("type = " + type);
//        }
//
//        resultSet.close();
//        statement.close();
//        connection.close();
//    }
//
//}