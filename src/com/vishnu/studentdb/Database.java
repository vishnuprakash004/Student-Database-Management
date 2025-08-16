package com.vishnu.studentdb;

import java.sql.*;

public class Database {
    private static final String url  = "jdbc:mysql://localhost:3306/student_db?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String user = "root";           
    private static final String pass = "password";  

    public static Connection get() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }
}
