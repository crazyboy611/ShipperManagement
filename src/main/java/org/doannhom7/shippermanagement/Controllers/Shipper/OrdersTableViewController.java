package org.doannhom7.shippermanagement.Controllers.Shipper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.doannhom7.shippermanagement.Models.Order;

import java.net.URL;
import java.util.ResourceBundle;

public class OrdersTableViewController implements Initializable {
    public TableView<Order> my_orders_view;
    public TableColumn<Order, String> idCol;
    public TableColumn<Order, String> pickup_location_col;
    public TableColumn<Order, String> delivery_location_col;
    public TableColumn<Order, String> value_col;
    public TableColumn<Order, String> other_details_col;
    public Label hello_label;
    public Text date_label;

    ObservableList<Order> orders = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
