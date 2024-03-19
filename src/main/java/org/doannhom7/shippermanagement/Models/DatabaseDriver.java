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
        PreparedStatement preparedStatement;
        String query = "SELECT * FROM shippermanagement.shippers";
        ResultSet resultSet = null;
        try{
            preparedStatement = this.connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    public ResultSet getPersonalImage(int shipper_id) {
        PreparedStatement preparedStatement;
        String query = "SELECT shippermanagement.shippers.personal_image WHERE shippermanagement.shippers.shipper_id = ?";
        ResultSet resultSet = null;
        try{
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, shipper_id);
            resultSet = preparedStatement.executeQuery();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    public ResultSet getOrdersData() {
        ResultSet resultSet = null;
        Statement statement;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM shippermanagement.orders ;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    public ResultSet getShipperData(String phone, String password) {
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        String query = "SELECT * FROM shippermanagement.shippers WHERE phone = ? AND password= ?";
        try {
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1,phone);
            preparedStatement.setString(2,password);
            resultSet = preparedStatement.executeQuery();
        }catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return resultSet;
    }
    public ResultSet getAdminData(String username, String password) {
        PreparedStatement preparedStatement;
        String query = "SELECT * FROM shippermanagement.admins WHERE username = ? AND password = ?";
        ResultSet resultSet = null;
        try {
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            resultSet = preparedStatement.executeQuery();
        }catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return resultSet;
    }
    public void updateShipperData(int id, String firstName, String lastName, String birth, String phone, String email, String address, String password) {
        try {
            String query = "UPDATE shippermanagement.shippers SET firstname=?, lastname=?, birthDay=?, phone=?, email=?, address=?, password=? WHERE shipper_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, birth);
            statement.setString(4, phone);
            statement.setString(5, email);
            statement.setString(6, address);
            statement.setString(7, password);
            statement.setInt(8, id);
            statement.executeUpdate();

        }catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    public void createNewShipper(String firstName, String lastName, String birth, String phone, String email, String address, String password) {
        PreparedStatement preparedStatement;
        String query = "INSERT INTO shippermanagement.shippers(firstname, lastname, phone, birthDay, email, address, password) VALUES (?,?,?,?,?,?,?)";
        try {
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1,firstName);
            preparedStatement.setString(2,lastName);
            preparedStatement.setString(3,phone);
            preparedStatement.setString(4,birth);
            preparedStatement.setString(5,email);
            preparedStatement.setString(6,address);
            preparedStatement.setString(7,password);
            preparedStatement.executeUpdate();
        }catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    public void deleteShipper(int id) {
        try{
            String query = "DELETE FROM shippermanagement.shippers WHERE shipper_id = ?";
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1, id);

            String updateOrdersQuery = "UPDATE shippermanagement.orders SET shipper_id = NULL WHERE shipper_id = ?";
            PreparedStatement updateOrdersStatement = connection.prepareStatement(updateOrdersQuery);
            updateOrdersStatement.setInt(1, id);
            updateOrdersStatement.executeUpdate();
            statement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ResultSet getOrdersByShipperId(int id) {
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM shippermanagement.orders WHERE shipper_id = ?";
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return resultSet;
    }
}
