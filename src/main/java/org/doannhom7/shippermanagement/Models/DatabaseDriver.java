package org.doannhom7.shippermanagement.Models;

import java.io.InputStream;
import java.sql.*;

public class DatabaseDriver {
    private Connection connection;
    public DatabaseDriver() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:2004/ShipperManagement", "DOAN", "DOAN");
        }catch (SQLException e) {
            e.printStackTrace();
        }
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
    public InputStream getPersonalImage(int shipper_id) {
        PreparedStatement preparedStatement;
        String query = "SELECT shippermanagement.shippers.personal_image FROM shippermanagement.shippers WHERE shippermanagement.shippers.shipper_id = ?";
        InputStream inputStream = null;
        try{
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, shipper_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                inputStream = resultSet.getBinaryStream("personal_image");
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return inputStream;
    }
    public ResultSet getOrdersData() {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement;
        String query = "SELECT * FROM shippermanagement.orders";
        try {
            preparedStatement = this.connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
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
    public void updateShipperData(int id, String firstName, String lastName, String birth, String phone, String email, String address, String password, InputStream inputStream) {
        try {
            String query = "UPDATE shippermanagement.shippers SET firstname=?, lastname=?, birthDay=?, phone=?, email=?, address=?, password=?, personal_image =? WHERE shipper_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, birth);
            statement.setString(4, phone);
            statement.setString(5, email);
            statement.setString(6, address);
            statement.setString(7, password);
            statement.setBinaryStream(8, inputStream);
            statement.setInt(9, id);
            statement.executeUpdate();

        }catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    public void createNewShipper(String firstName, String lastName, String birth, String phone, String email, String address, String password, InputStream inputStream) {
        PreparedStatement preparedStatement;
        String query = "INSERT INTO shippermanagement.shippers(firstname, lastname, phone, birthDay, email, address, password, personal_image) VALUES (?,?,?,?,?,?,?,?)";
        try {
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1,firstName);
            preparedStatement.setString(2,lastName);
            preparedStatement.setString(3,phone);
            preparedStatement.setString(4,birth);
            preparedStatement.setString(5,email);
            preparedStatement.setString(6,address);
            preparedStatement.setString(7,password);
            preparedStatement.setBinaryStream(8, inputStream);
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
    public void updateOrder(int id, int shipper_id, String pickup_location, String delivery_location, Double value, String other_details, String delivery_date_expect) {
        PreparedStatement preparedStatement;
        String query = "UPDATE shippermanagement.orders SET shipper_id=?, pickup_location=?, delivery_location=?, value=?, other_details=?, delivery_date_expect=? WHERE order_id=?";
        try{
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, shipper_id);
            preparedStatement.setString(2, pickup_location);
            preparedStatement.setString(3,delivery_location);
            preparedStatement.setDouble(4, value);
            preparedStatement.setString(5, other_details);
            preparedStatement.setString(6, delivery_date_expect);
            preparedStatement.setInt(7, id);
            preparedStatement.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void createOrder(int shipper_id, String pickup_location, String delivery_location, Double value, String other_details, String delivery_date_expect) {
        PreparedStatement preparedStatement;
        String query = "INSERT INTO shippermanagement.orders(shipper_id, pickup_location, delivery_location, value, other_details, delivery_date_expect) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1,shipper_id);
            preparedStatement.setString(2, pickup_location);
            preparedStatement.setString(3,delivery_location);
            preparedStatement.setDouble(4, value);
            preparedStatement.setString(5, other_details);
            preparedStatement.setString(6, delivery_date_expect);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ResultSet findShipperById(int id) {
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        String query = "SELECT * FROM shippermanagement.shippers WHERE shipper_id=?";
        try{
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    public void deleteOrder(int id) {
        PreparedStatement preparedStatement;
        String query = "DELETE FROM shippermanagement.orders WHERE order_id=?";
        try{
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
