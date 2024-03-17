package org.doannhom7.shippermanagement.Models;

import java.sql.*;

public class DatabaseDriver {
    private Connection connection;
    public DatabaseDriver() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:2004/ShipperManagement", "DOAN", "DOAN");
        }catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("success");
    }

    public ResultSet getOrdersData() {
        ResultSet resultSet = null;
        Statement statement;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Orders ;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
