package org.doannhom7.shippermanagement.Controllers.Shipper;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.doannhom7.shippermanagement.Views.AccountType;
import org.doannhom7.shippermanagement.Models.Model;
import org.doannhom7.shippermanagement.Views.ShipperMenuOptions;

import java.net.URL;
import java.util.ResourceBundle;

public class ShipperMenuController implements Initializable {
    public Button my_orders_btn;
    public Button profile_btn;
    public Button logout_btn;
    public Button report_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profile_btn.setOnAction(actionEvent -> onShipperProfile());
        my_orders_btn.setOnAction(actionEvent -> onShipperOrder());
        logout_btn.setOnAction(actionEvent -> onLogout());
    }
    private void onShipperProfile() {
        Model.getInstance().getViewFactory().getShipperSelectedMenu().set(ShipperMenuOptions.MY_PROFILE);
    }
    private void onShipperOrder() {
        Model.getInstance().getViewFactory().getShipperSelectedMenu().set(ShipperMenuOptions.MY_ORDERS);
    }
    private void onLogout() {
        Stage stage = (Stage) report_btn.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().setAdminLoginSuccessFlag(false);
        Model.getInstance().setShipperLoginSuccessFlag(false);
        Model.getInstance().getViewFactory().setAccountType(AccountType.ADMIN);
        Model.getInstance().getViewFactory().showLoginWindow();
    }
}
