package org.doannhom7.shippermanagement.Models;

import javax.xml.transform.Result;
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
    public ResultSet findShipperByOrderId(int order_id) {
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        String query = "SELECT s.shipper_id, s.firstname, s.lastname, s.phone "
                + "FROM shippermanagement.shippers s "
                + "join shippermanagement.orders o on s.shipper_id = o.shipper_id "
                + "WHERE order_id = ?";
        try{
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, order_id);
            resultSet = preparedStatement.executeQuery();
        }catch (Exception e) {
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
    public int createOrder(int shipper_id, String pickup_location, String delivery_location, Double value, String other_details, String delivery_date_expect) {
        PreparedStatement preparedStatement;
        String query = "INSERT INTO shippermanagement.orders(shipper_id, pickup_location, delivery_location, value, other_details, delivery_date_expect) VALUES (?, ?, ?, ?, ?, ?)";
        int order_id = -1;
        try {
            preparedStatement = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,shipper_id);
            preparedStatement.setString(2, pickup_location);
            preparedStatement.setString(3,delivery_location);
            preparedStatement.setDouble(4, value);
            preparedStatement.setString(5, other_details);
            preparedStatement.setString(6, delivery_date_expect);
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()) {
                order_id = resultSet.getInt(1);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return order_id;

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
            deleteDeliveryNote(id);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ResultSet getOrderNoteById(int id) {
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        String query = "SELECT * FROM shippermanagement.delivery_note WHERE order_id=?";
        try{
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    public void updateOrderNote(int delivery_number, String status, String delivery_date, int id) {
        PreparedStatement preparedStatement;
        String query = "UPDATE shippermanagement.delivery_note SET delivery_number=?, delivery_status=?, delivery_date=? where order_id=?";
        try {
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, delivery_number);
            preparedStatement.setString(2, status);
            preparedStatement.setString(3, delivery_date);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createDeliveryNote(int order_id, int delivery_number, String delivery_status, String delivery_date) {
        PreparedStatement preparedStatement;
        String query = "INSERT INTO shippermanagement.delivery_note(order_id, delivery_number, delivery_status, delivery_date) VALUES ( ?,?,?,? )";
        try {
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, order_id);
            preparedStatement.setInt(2, delivery_number);
            preparedStatement.setString(3, delivery_status);
            preparedStatement.setString(4, delivery_date);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ResultSet getDeliveryNote() {
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        String query = "SELECT * FROM shippermanagement.delivery_note";
        try {
            preparedStatement = this.connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    public ResultSet findOrderById(int order_id) {
        PreparedStatement preparedStatement;
        String query = "SELECT * FROM shippermanagement.orders WHERE order_id = ?";
        ResultSet resultSet = null;
        try{
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, order_id);
            resultSet = preparedStatement.executeQuery();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    public void deleteDeliveryNote(int order_id) {
        PreparedStatement deleteStatement;
        PreparedStatement removeOrderId;
        String query1 = "DELETE FROM shippermanagement.delivery_note WHERE order_id=?";
        String query2 = "DELETE FROM shippermanagement.orders WHERE order_id = ?";
        try{
            deleteStatement = this.connection.prepareStatement(query1);
            removeOrderId = this.connection.prepareStatement(query2);
            deleteStatement.setInt(1, order_id);
            deleteStatement.executeUpdate();
            removeOrderId.setInt(1,order_id);
            removeOrderId.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ResultSet getShipperOrderStatistic(int shipper_id) {
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        String query = "SELECT LEFT(delivery_date, 7) AS YearMonth, sum(value), COUNT(o.order_id) " +
                "FROM shippermanagement.delivery_note " +
                "RIGHT JOIN shippermanagement.orders o on delivery_note.order_id = o.order_id " +
                "RIGHT JOIN shippermanagement.shippers s on s.shipper_id = o.shipper_id " +
                "WHERE o.shipper_id = ? and shippermanagement.delivery_note.delivery_status ='Delivered' " +
                "GROUP BY YearMonth " +
                "ORDER BY YearMonth " +
                "LIMIT 8";
        try{
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, shipper_id);
            resultSet = preparedStatement.executeQuery();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    public ResultSet getAdminOrderStatistic() {
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        String query = "SELECT LEFT(delivery_date, 7) AS YearMonth, sum(value), COUNT(o.order_id) " +
                "FROM shippermanagement.delivery_note " +
                "RIGHT JOIN shippermanagement.orders o on delivery_note.order_id = o.order_id " +
                "WHERE delivery_status = 'Delivered' " +
                "GROUP BY YearMonth " +
                "ORDER BY YearMonth " +
                "LIMIT 8";
        try {
            preparedStatement = this.connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
        }catch (SQLException e ) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
