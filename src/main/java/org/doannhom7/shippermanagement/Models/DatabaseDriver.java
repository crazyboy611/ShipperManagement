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
    public ResultSet getAllShipper() {
        Statement statement;
        ResultSet resultSet = null;
        try{
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM shippers ;");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
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
    public ResultSet getShipperData(String phone, String password) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM shippers WHERE phone = '"+phone+"' AND password='"+password+"';");
        }catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return resultSet;
    }
    public ResultSet getAdminData(String username, String password) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Admins WHERE username = '"+username+"' AND password='"+password+"';");
        }catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return resultSet;
    }
}
