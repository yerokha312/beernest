package com.neobis.yerokha;


import java.sql.Connection;
import java.sql.SQLException;

public class JDBCExecutor {
    public static void main(String[] args) {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager(
                "localhost", "beernestdb", "postgres", "password");

        try {
            Connection connection = dcm.getConnection();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to establish connection");
        }
    }
}
