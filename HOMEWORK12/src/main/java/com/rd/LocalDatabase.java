package com.rd;

import java.sql.*;

public class LocalDatabase {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/personnel_db";
    private static final String USER = "root";
    private static final String PASS = "root";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            // Create table
            String createTableSQL = "CREATE TABLE IF NOT EXISTS person (id INT PRIMARY KEY, name VARCHAR(50), age INT)";
            try (PreparedStatement pstmt = conn.prepareStatement(createTableSQL)) {
                pstmt.execute();
            }

            // Insert data
            String insertSQL = "INSERT INTO person (id, name, age) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                pstmt.setInt(1, 1);
                pstmt.setString(2, "Hacer bay");
                pstmt.setInt(3, 30);
                try {
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("Error inserting data: " + e.getMessage());
                }
            }

            // Select data
            String selectSQL = "SELECT * FROM person";
            try (PreparedStatement pstmt = conn.prepareStatement(selectSQL);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Name: " + rs.getString("name"));
                    System.out.println("Age: " + rs.getInt("age"));
                }
            }

            // Delete data
            String deleteSQL = "DELETE FROM person WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
                pstmt.setInt(1, 1);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
