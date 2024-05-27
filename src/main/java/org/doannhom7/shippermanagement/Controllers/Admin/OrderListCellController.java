package org.doannhom7.shippermanagement.Controllers.Admin;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.doannhom7.shippermanagement.Controllers.Shipper.ShipperOrderNoteController;
import org.doannhom7.shippermanagement.Models.DeliveryNote;
import org.doannhom7.shippermanagement.Models.Model;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class OrderListCellController implements Initializable {
    public Label note_id_lbl;
    public Label order_id_lbl;
    public Label number_lbl;
    public Label delivery_status_lbl;
    public Label delivery_date;
    public Button check_btn;
    private final DeliveryNote deliveryNote;

    public OrderListCellController(DeliveryNote deliveryNote) {
        this.deliveryNote = deliveryNote;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
     note_id_lbl.textProperty().bind(deliveryNote.noteIdProperty().asString());
     order_id_lbl.textProperty().bind(deliveryNote.orderIdProperty().asString());
     number_lbl.textProperty().bind(deliveryNote.numberProperty().asString());
     delivery_status_lbl.textProperty().bind(deliveryNote.statusProperty());
     delivery_status_lbl.setWrapText(true);
     delivery_status_lbl.setTextAlignment(TextAlignment.JUSTIFY);
     delivery_status_lbl.setMaxWidth(150);
     if(deliveryNote.deliveryDateProperty().get() == null){
         delivery_date.setText("                   ");
     }else{
         delivery_date.textProperty().bind(deliveryNote.deliveryDateProperty().asString());
     }
     check_btn.setOnAction(actionEvent -> {
         int id = Integer.parseInt(order_id_lbl.getText());
         ResultSet deliveryNote  = Model.getInstance().getDatabaseDriver().getOrderNoteById(id);
         ResultSet order = Model.getInstance().getDatabaseDriver().findOrderById(id);
         ResultSet shipper = Model.getInstance().getDatabaseDriver().findShipperByOrderId(id);
         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/Admin/AdminOrderConfirmView.fxml"));
         AdminOrderConfirmController adminOrderConfirmController = new AdminOrderConfirmController(deliveryNote, order, shipper);
         fxmlLoader.setController(adminOrderConfirmController);
         Stage stage = new Stage();
         Scene scene;
         try {
             scene = new Scene(fxmlLoader.load());
         }catch (IOException e) {
             throw new RuntimeException(e);
         }
         stage.setScene(scene);
         stage.initModality(Modality.APPLICATION_MODAL);
         stage.initStyle(StageStyle.UNDECORATED);
         stage.setResizable(false);
         stage.setTitle("Delivery Note");
         stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/icons8-shipper-64.png"))));
         stage.show();
     });
    }
}
