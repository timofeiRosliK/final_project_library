package service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DbConfig {
    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/library";
    public static final String USER = "root";
    public static final String PASSWORD = "root";
}
