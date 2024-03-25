package org.doannhom7.shippermanagement.Controllers.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.doannhom7.shippermanagement.Models.Order;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ShipperOrderTableViewController implements Initializable {
    public TableView<Order> my_orders_view;
    public TableColumn<Order, Integer> idCol;
    public TableColumn<Order, String> pickup_location_col;
    public TableColumn<Order, String> delivery_location_col;
    public TableColumn<Order, String> value_col;
    public TableColumn<Order, String> other_details_col;
    public TableColumn<Order, LocalDate> delivery_date_expect_col;
    public String shipperLastName;
    public Label shipper_name;
    public ResultSet resultSet;
    private final ObservableList<Order> orders = FXCollections.observableArrayList();
    public ShipperOrderTableViewController(ResultSet resultSet, String shipperLastName) {
        this.resultSet = resultSet;
        this.shipperLastName = shipperLastName;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
        shipper_name.setText("Hi, " + shipperLastName);
    }
    private void initTable() {
        try {
            while (resultSet.next()) {
                int order_id = resultSet.getInt("order_id");
                String pickup = resultSet.getString("pickup_location");
                String delivery = resultSet.getString("delivery_location");
                Double value = resultSet.getDouble("value");
                String others_detail = resultSet.getString("other_details");
                String[] date = resultSet.getString("delivery_date_expect").split("-");
                LocalDate localDate = LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
                this.orders.add(new Order(order_id, pickup, delivery, localDate, value, others_detail));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        idCol.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        pickup_location_col.setCellValueFactory(new PropertyValueFactory<>("pickup_location"));
        delivery_location_col.setCellValueFactory(new PropertyValueFactory<>("delivery_location"));
        value_col.setCellValueFactory(new PropertyValueFactory<>("value"));
        other_details_col.setCellValueFactory(new PropertyValueFactory<>("other_details"));
        delivery_date_expect_col.setCellValueFactory(new PropertyValueFactory<>("deliveryDateExpect"));
        my_orders_view.setItems(orders);
    }
}
