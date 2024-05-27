package org.doannhom7.shippermanagement.Controllers.Shipper;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.doannhom7.shippermanagement.Models.Model;
import org.doannhom7.shippermanagement.Models.Order;
import org.doannhom7.shippermanagement.Views.ShipperMenuOptions;

import java.io.IOException;
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
    public TableColumn<Order, String> note_col;
    public Label hello_label;
    public Text date_label;
    ObservableList<Order> orders = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initData();
        Model.getInstance().getViewFactory().getShipperSelectedMenu().addListener((observable, oldValue, newValue) -> {
            if (newValue == ShipperMenuOptions.MY_ORDERS) {
               initTable();
            }
        });
    }
    private void initData() {
        initTable();
        date_label.setText("Today is " + LocalDate.now());
        hello_label.textProperty().bind(Bindings.concat("Hi, ").concat(Model.getInstance().getShipper().lastNameProperty()));
    }
    public void initTable() {
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
        Callback<TableColumn<Order, String>, TableCell<Order, String>> cellFactory = (TableColumn<Order, String> param) -> new TableCell<>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    final Button viewNote = new Button("Order Note");
                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.STICKY_NOTE_ALT);
                    icon.setFill(Paint.valueOf("#FFFFFF"));
                    viewNote.setGraphic(icon);
                    viewNote.setStyle("-fx-background-color:#0099CCFF; -fx-text-fill: white; -fx-cursor: hand;");
                    viewNote.setOnAction(actionEvent -> {
                        int id = getTableRow().getItem().orderIdProperty().get();
                        ResultSet resultSet = Model.getInstance().getDatabaseDriver().getOrderNoteById(id);
                        ResultSet resultSet1 = Model.getInstance().getDatabaseDriver().findOrderById(id);
                        ShipperOrderNoteController shipperOrderNoteController = new ShipperOrderNoteController(resultSet, resultSet1);
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/Shipper/ShipperOrderNote.fxml"));
                        fxmlLoader.setController(shipperOrderNoteController);
                        Stage stage = new Stage();
                        Scene scene;
                        try {
                            scene = new Scene(fxmlLoader.load());
                        }catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        stage.setScene(scene);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setResizable(false);
                        stage.setTitle("Delivery Note");
                        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/icons8-shipper-64.png"))));
                        stage.show();
                    });
                    setGraphic(viewNote);
                    setText(null);
                }
            }
        };
        note_col.setCellFactory(cellFactory);
        my_orders_view.setItems(orders);
    }
}
