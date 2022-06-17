package com.grocerie.include;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DATABASE = "grocerie";

    private static final String USER  = "root";
    private static final String PASSWORD  = "";

    public static java.sql.Connection getConnection() throws ClassNotFoundException, SQLException {
        String databaseDriver = "com.mysql.cj.jdbc.Driver";

        Class.forName(databaseDriver);
        return DriverManager.getConnection( "jdbc:mysql://localhost:3306/" + DATABASE, USER, PASSWORD);
    }
}
