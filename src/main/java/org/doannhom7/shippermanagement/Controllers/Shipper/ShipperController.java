package org.doannhom7.shippermanagement.Controllers.Shipper;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import org.doannhom7.shippermanagement.Models.Model;
import java.net.URL;
import java.util.ResourceBundle;

public class ShipperController implements Initializable {
    public BorderPane shipper_parent;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getShipperSelectedMenu().addListener((observableValue, oldVal, newVal) -> {
            switch (newVal) {
                case MY_ORDERS -> shipper_parent.setCenter(Model.getInstance().getViewFactory().getShipperOrdersView());
                case STATISTICS -> shipper_parent.setCenter(Model.getInstance().getViewFactory().getShipperStatisticView());
                default -> shipper_parent.setCenter(Model.getInstance().getViewFactory().getShipperProfileView());
            }
        });
    }
}
