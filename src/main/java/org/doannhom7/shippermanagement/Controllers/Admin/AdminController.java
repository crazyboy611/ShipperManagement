package org.doannhom7.shippermanagement.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import org.doannhom7.shippermanagement.Models.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public BorderPane admin_parent;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getAdminSelectedMenu().addListener((observableValue, oldVal, newVal) -> {
            switch (newVal) {
                case ALL_ORDERS -> admin_parent.setCenter(Model.getInstance().getViewFactory().getAllOrdersCrudView());
                default -> admin_parent.setCenter(Model.getInstance().getViewFactory().getAllShipperView());
            }
        } );
    }
}
