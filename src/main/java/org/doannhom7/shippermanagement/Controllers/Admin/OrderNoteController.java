package org.doannhom7.shippermanagement.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class OrderNoteController implements Initializable {
    public Label order_id;
    public Label number_of_deliveries;
    private final ResultSet resultSet;
    private final ResultSet resultSet1;
    public Label delivery_date_expect_lbl;
    public Label status_lbl;

    public OrderNoteController(ResultSet resultSet, ResultSet resultSet1) {
        this.resultSet = resultSet;
        this.resultSet1 = resultSet1;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initData();
    }
    private void initData() {
        try{
            if(resultSet.next()) {
                int orderId = resultSet.getInt("order_id");
                String delivery_date = resultSet.getString("delivery_date");
                int number = resultSet.getInt("delivery_number");
                String status = resultSet.getString("delivery_status");
                order_id.setText(String.valueOf(orderId));
                number_of_deliveries.setText(String.valueOf(number));
                if(status.equals("Delivered")) {
                        status_lbl.setText("Delivered on "+ delivery_date);
                        status_lbl.setStyle("-fx-background-color: #5BBD2BFF; -fx-text-fill: white; -fx-background-radius: 10;");
                    }else{
                        status_lbl.setText("Not Delivery");
                        status_lbl.setStyle("-fx-background-color: #E33539FF; -fx-text-fill: white; -fx-background-radius: 10;");
                    }
                if(resultSet1.next()) {
                    String delivery_date_expect = resultSet1.getString("delivery_date_expect");
                    delivery_date_expect_lbl.setText(delivery_date_expect);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
