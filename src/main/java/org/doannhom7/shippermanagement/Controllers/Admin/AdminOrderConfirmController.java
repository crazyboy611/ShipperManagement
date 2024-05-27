package org.doannhom7.shippermanagement.Controllers.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.doannhom7.shippermanagement.Models.Model;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminOrderConfirmController implements Initializable {
    public Label order_id;
    public RadioButton delivered;
    public ToggleGroup status;
    public RadioButton not_delivery;
    public Label number_of_deliveries;
    public Label date_delivery_expect_lbl;
    public Label status_lbl;
    public Label first_name_lbl;
    public Label last_name_lbl;
    public Label phone_lbl;
    public ComboBox<Integer> shipper_combobox;
    public ObservableList<Integer> shipperId;
    public Label error_lbl;
    public Button close_btn;
    public Button confirm_btn;
    private final boolean confirmBtnIsClicked = false;
    public Button update_btn;

    private final ResultSet deliveryNotes;
    private final ResultSet orders;
    private final ResultSet shipper;
    public Label pickup_location_lbl;
    public Label other_details_lbl;
    public Label value_lbl;
    public Label delivery_location_lbl;

    public AdminOrderConfirmController(ResultSet deliveryNotes, ResultSet orders, ResultSet shipper) {
        this.deliveryNotes = deliveryNotes;
        this.orders = orders;
        this.shipper = shipper;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
        confirm_btn.setOnAction(actionEvent -> {
            int numOfDel = Integer.parseInt(number_of_deliveries.getText());
            if (numOfDel < 3) {
                int orderId = Integer.parseInt(order_id.getText());
                String deliveryDate = LocalDate.now().toString();
                if (not_delivery.isSelected()) {
                    String notDelivery = not_delivery.textProperty().get();
                    int num = Integer.parseInt(number_of_deliveries.getText());
                    num--;
                    if(num < 0) {
                        num = 0;
                    }
                    Model.getInstance().getDatabaseDriver().updateOrderNote(num, notDelivery, "", orderId);
                    delivered.setDisable(true);
                    not_delivery.setDisable(true);
                    confirm_btn.setDisable(true);
                    status_lbl.setText("Not Delivery");
                    status_lbl.setStyle("-fx-background-color: #E33539FF; -fx-text-fill: white; -fx-background-radius: 10;");
                }
                if (delivered.isSelected()) {
                    String delivery = delivered.textProperty().get();
                    int num = Integer.parseInt(number_of_deliveries.getText());
                    Model.getInstance().getDatabaseDriver().updateOrderNote(num, delivery, deliveryDate, orderId);
                    status_lbl.setText("Delivered on " + LocalDate.now());
                    status_lbl.setStyle("-fx-background-color: #5BBD2BFF; -fx-text-fill: white; -fx-background-radius: 10;");
                    confirm_btn.setVisible(false);
                    delivered.setDisable(true);
                    not_delivery.setDisable(true);
                }
            }else if(numOfDel == 3 && not_delivery.isSelected()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm");
                alert.setHeaderText("Please give the order to another delivery person");
                Optional<ButtonType> buttonType = alert.showAndWait();
                if(buttonType.get() == ButtonType.OK) {
                    solution();
                    update_btn.setDisable(false);
                }
                updateAction();
            }
        });
        close_btn.setOnAction(actionEvent -> {
            Stage stage = (Stage) order_id.getScene().getWindow();
            stage.close();
        });
    }
    private void init() {
        update_btn.setDisable(true);
        error_lbl.setVisible(false);
        try {
            if(deliveryNotes.next()) {
                int orderId = deliveryNotes.getInt("order_id");
                int numOfDeli = deliveryNotes.getInt("delivery_number");
                String status = deliveryNotes.getString("delivery_status");
                String delivery_date = deliveryNotes.getString("delivery_date");
                order_id.setText(String.valueOf(orderId));
                number_of_deliveries.setText(String.valueOf(numOfDeli));
                if(orders.next()) {
                    String delivery_date_expect = orders.getString("delivery_date_expect");
                    String pickup_location = orders.getString("pickup_location");
                    String delivery_location = orders.getString("delivery_location");
                    double value = orders.getDouble("value");
                    String other_details = orders.getString("other_details");
                    date_delivery_expect_lbl.setText(delivery_date_expect);
                    pickup_location_lbl.setText(pickup_location);
                    delivery_location_lbl.setText(delivery_location);
                    value_lbl.setText(String.valueOf(value));
                    other_details_lbl.setText(other_details);
                }
                if(status.equals("Delivered")) {
                    status_lbl.setText("Delivered on "+ delivery_date);
                    status_lbl.setStyle("-fx-background-color: #5BBD2BFF; -fx-text-fill: white; -fx-background-radius: 10;");
                    delivered.setDisable(true);
                    not_delivery.setDisable(true);
                    confirm_btn.setDisable(true);
                    setShipperInfo();
                    shipper_combobox.setDisable(true);
                }else if(status.equals("Not Delivery") && numOfDeli < 3) {
                    status_lbl.setText("Not Delivery");
                    status_lbl.setStyle("-fx-background-color: #E33539FF; -fx-text-fill: white; -fx-background-radius: 10;");
                    setShipperInfo();
                    shipper_combobox.setDisable(true);
                    confirm_btn.setDisable(true);
                    delivered.setDisable(true);
                    not_delivery.setDisable(true);
                }else if(status.equals("Not Delivery") && numOfDeli == 3) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Alert");
                    alert.setHeaderText("Please give the order to another delivery person");
                    alert.getButtonTypes().setAll(ButtonType.OK);
                    Window window = alert.getDialogPane().getScene().getWindow();
                    Stage stage = (Stage) window;
                    stage.initModality(Modality.APPLICATION_MODAL);
                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if(buttonType.isPresent()) {
                        status_lbl.setText("Not Delivery");
                        status_lbl.setStyle("-fx-background-color: #E33539FF; -fx-text-fill: white; -fx-background-radius: 10;");
                        confirm_btn.setDisable(false);
                        delivered.setDisable(true);
                        confirm_btn.setDisable(true);
                        not_delivery.setDisable(true);
                        close_btn.setDisable(true);
                        update_btn.setDisable(false);
                        confirm_btn.setVisible(false);
                        setShipperInfo();
                        setShipperCombobox();
                        error_lbl.setVisible(true);
                        updateAction();
                    }
                }else if(status.equals("Delivered, wait Admin confirm!") && numOfDeli < 3) {
                    status_lbl.setText("Delivered, wait Admin confirm!");
                    setShipperInfo();
                    setShipperCombobox();
                    status_lbl.setStyle("-fx-background-color: #F7AD1DFF; -fx-text-fill: white; -fx-background-radius: 10;");
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setShipperInfo() {
        try{
            while(shipper.next()) {
                int shipper_id = shipper.getInt(1);
                String firstname = shipper.getString(2);
                String lastName = shipper.getString(3);
                String phone = shipper.getString(4);
                shipper_combobox.setValue(shipper_id);
                first_name_lbl.setText(firstname);
                last_name_lbl.setText(lastName);
                phone_lbl.setText(phone);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setShipperCombobox() {
        shipper_combobox.setDisable(false);
        this.shipperId = FXCollections.observableArrayList();
        ResultSet resultSet = Model.getInstance().getDatabaseDriver().getAllShipper();
        shipper_combobox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldVal, newVal) -> {
            ResultSet shipperInfo = Model.getInstance().getDatabaseDriver().findShipperById(newVal);
            try {
                while (shipperInfo.next()) {
                    int shipper_id = shipperInfo.getInt(1);
                    String firstname = shipperInfo.getString(2);
                    String lastName = shipperInfo.getString(3);
                    String phone = shipperInfo.getString(4);
                    shipper_combobox.setValue(shipper_id);
                    first_name_lbl.setText(firstname);
                    last_name_lbl.setText(lastName);
                    phone_lbl.setText(phone);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        try{
            while(resultSet.next()) {
                int id = resultSet.getInt("shipper_id");
                shipperId.add(id);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        shipper_combobox.setItems(shipperId);
    }
    private void solution() {
        int number = Integer.parseInt(number_of_deliveries.getText());
        if(number >= 3) {
            setShipperCombobox();
        }
    }
    private void updateAction() {
        update_btn.setOnAction(actionEvent1 -> {
            int orderId = Integer.parseInt(order_id.getText());
            ResultSet resultSet = Model.getInstance().getDatabaseDriver().findOrderById(orderId);
            try{
                if(resultSet.next()) {
                    int shipper_id = shipper_combobox.getValue();
                    String pickup_location = resultSet.getString(3);
                    String delivery_location = resultSet.getString(4);
                    double value = resultSet.getDouble(5);
                    String other_details = resultSet.getString(6);
                    String delivery_date_expect = resultSet.getString(7);
                    Model.getInstance().getDatabaseDriver().updateOrder(orderId, shipper_id, pickup_location, delivery_location, value, other_details, delivery_date_expect);
                    Model.getInstance().getDatabaseDriver().updateOrderNote(0, status_lbl.getText(), "",orderId);
                    update_btn.setDisable(true);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            close_btn.setDisable(false);
            confirm_btn.setDisable(false);
            error_lbl.setVisible(false);
        });
    }
}
