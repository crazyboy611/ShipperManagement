package org.doannhom7.shippermanagement.Controllers.Shipper;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.doannhom7.shippermanagement.Models.Model;
import org.doannhom7.shippermanagement.Models.Order;

import javax.sound.midi.Soundbank;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class OrdersTableViewController implements Initializable {
    public TableView<Order> my_orders_view;
    public TableColumn<Order, Integer> idCol;
    public TableColumn<Order, String> pickup_location_col;
    public TableColumn<Order, String> delivery_location_col;
    public TableColumn<Order, String> deli_date_expect_col;
    public TableColumn<Order, String> value_col;
    public TableColumn<Order, String> other_details_col;
    public Label hello_label;
    public Text date_label;

    private final ObservableList<Order> orders = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        date_label.setText("Today is " + LocalDate.now());
        hello_label.textProperty().bind(Bindings.concat("Hi, ").concat(Model.getInstance().getShipper().lastNameProperty()));
        initTable();
    }
    private void initTable() {
        this.orders.clear();
        ResultSet resultSet = Model.getInstance().getDatabaseDriver().getOrdersByShipperId(Model.getInstance().getShipper().shipperIdProperty().get());
        try{
            while (resultSet.next()) {
                int id = resultSet.getInt("order_id");
                String pickup_location = resultSet.getString("pickup_location");
                String delivery_location = resultSet.getString("delivery_location");
                Double value = resultSet.getDouble("value");
                String[] date = resultSet.getString("delivery_date_expect").split("-");
                LocalDate deliDateExpect = LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
                String other_details = resultSet.getString("other_details");
                this.orders.add(new Order(id, pickup_location, delivery_location, deliDateExpect, value, other_details));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        idCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        pickup_location_col.setCellValueFactory(new PropertyValueFactory<>("pickupLocation"));
        delivery_location_col.setCellValueFactory(new PropertyValueFactory<>("deliveryLocation"));
        value_col.setCellValueFactory(new PropertyValueFactory<>("value"));
        other_details_col.setCellValueFactory(new PropertyValueFactory<>("otherDetails"));
        deli_date_expect_col.setCellValueFactory(new PropertyValueFactory<>("deliveryDateExpect"));
        my_orders_view.setItems(orders);
    }
}
