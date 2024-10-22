package org.doannhom7.shippermanagement.Controllers.Shipper;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import org.doannhom7.shippermanagement.Models.Model;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ShipperOrderNoteController implements Initializable {
    public Label order_id;
    public Button confirm_btn;
    public Button close_btn;
    public Label number_of_deliveries;
    public Label date_delivery_expect_lbl;
    public Label status_lbl;
    public RadioButton not_delivery;
    public RadioButton delivered;
    public ResultSet deliveryNote;

    public ResultSet order;
    public ShipperOrderNoteController(ResultSet deliveryNote, ResultSet order) {
        this.deliveryNote = deliveryNote;
        this.order = order;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
        confirm_btn.setOnAction(actionEvent -> {
            int orderId = Integer.parseInt(order_id.getText());
            String deliveryDate = LocalDate.now().toString();
            if(not_delivery.isSelected()) {
                String notDelivery = not_delivery.textProperty().get();
                int num = Integer.parseInt(number_of_deliveries.getText());
                num++;
                if(num == 3) {
                    Model.getInstance().getDatabaseDriver().updateOrderNote(num, notDelivery, "", orderId);
                    number_of_deliveries.setText(String.valueOf(num));
                    confirm_btn.setDisable(true);
                    delivered.setDisable(true);
                    not_delivery.setDisable(true);
                }
                Model.getInstance().getDatabaseDriver().updateOrderNote(num, notDelivery, "", orderId);
                number_of_deliveries.setText(String.valueOf(num));
                confirm_btn.setDisable(true);
                delivered.setDisable(true);
                not_delivery.setDisable(true);
            }
            if(delivered.isSelected()) {
                int num = Integer.parseInt(number_of_deliveries.getText());
                if(num == 0) {
                    num++;
                }
                Model.getInstance().getDatabaseDriver().updateOrderNote(num, "Delivered, wait Admin confirm!", deliveryDate, orderId);
                number_of_deliveries.setText(String.valueOf(num));
                status_lbl.setText("Delivered, wait Admin confirm! " +'\n'+ LocalDate.now());
                status_lbl.setStyle("-fx-background-color: #5BBD2BFF; -fx-text-fill: white; -fx-background-radius: 10;");
            }
            confirm_btn.setDisable(true);
            delivered.setDisable(true);
            not_delivery.setDisable(true);
        });
        close_btn.setOnAction(actionEvent -> {
            Stage stage = (Stage) order_id.getScene().getWindow();
            stage.close();
        });
    }
    private void init() {
        try{
            if(deliveryNote.next()) {
                int orderId = deliveryNote.getInt("order_id");
                int numOfDeli = deliveryNote.getInt("delivery_number");
                String status = deliveryNote.getString("delivery_status");
                String delivery_date = deliveryNote.getString("delivery_date");
                order_id.setText(String.valueOf(orderId));
                number_of_deliveries.setText(String.valueOf(numOfDeli));
                if(order.next()) {
                    String delivery_date_expect = order.getString("delivery_date_expect");
                    date_delivery_expect_lbl.setText(delivery_date_expect);
                }
                if(status.equals("Delivered")) {
                    status_lbl.setText("Delivered on "+ delivery_date);
                    status_lbl.setStyle("-fx-background-color: #5BBD2BFF; -fx-text-fill: white; -fx-background-radius: 10;");
                    delivered.setDisable(true);
                    not_delivery.setDisable(true);
                    confirm_btn.setDisable(true);
                }else if(status.equals("Not Delivery") && numOfDeli < 3){
                    status_lbl.setText("Not Delivery");
                    status_lbl.setStyle("-fx-background-color: #E33539FF; -fx-text-fill: white; -fx-background-radius: 10;");
                }else if(numOfDeli == 3){
                    status_lbl.setStyle("-fx-background-color: #E33539FF; -fx-text-fill: white; -fx-background-radius: 10; -fx-wrap-text: true; -fx-pref-height: 70;");
                    status_lbl.layoutYProperty().set(130);
                    status_lbl.setText("""
                            Not Delivered
                            You have failed to deliver the order
                            more than 3 times""");
                    delivered.setDisable(true);
                    not_delivery.setDisable(true);
                    confirm_btn.setDisable(true);
                }else if(status.equals("Delivered, wait Admin confirm!")) {
                    status_lbl.setText("Delivered, wait Admin confirm!");
                    status_lbl.setStyle("-fx-background-color: #F7AD1DFF; -fx-text-fill: white; -fx-background-radius: 10;");
                    delivered.setDisable(true);
                    not_delivery.setDisable(true);
                    confirm_btn.setVisible(false);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}