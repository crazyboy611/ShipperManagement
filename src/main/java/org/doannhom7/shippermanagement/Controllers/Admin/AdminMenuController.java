package org.doannhom7.shippermanagement.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.doannhom7.shippermanagement.Views.AccountType;
import org.doannhom7.shippermanagement.Views.AdminMenuOptions;
import org.doannhom7.shippermanagement.Models.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    public Button all_shippers_btn;
    public Button all_orders_btn;
    public Button logout_btn;
    public Button find_btn;
    public Button statistic_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        all_shippers_btn.setOnAction(actionEvent -> onAllShipperCrud());
        all_orders_btn.setOnAction(actionEvent -> onAllOrderCrud());
        statistic_btn.setOnAction(actionEvent -> onStatisticView());
        logout_btn.setOnAction(actionEvent -> onLogout());
    }
    private void onAllShipperCrud() {
        Model.getInstance().getViewFactory().getAdminSelectedMenu().set(AdminMenuOptions.ALL_SHIPPERS_CRUD);
    }
    private void onAllOrderCrud() {
        Model.getInstance().getViewFactory().getAdminSelectedMenu().set(AdminMenuOptions.ALL_ORDERS);
    }
    private void onStatisticView() {
        Model.getInstance().getViewFactory().getAdminSelectedMenu().set(AdminMenuOptions.STATISTICS);
    }
    private void onLogout() {
        Stage stage = (Stage) all_orders_btn.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().setAdminLoginSuccessFlag(false);
        Model.getInstance().setShipperLoginSuccessFlag(false);
        Model.getInstance().getViewFactory().showLoginWindow();
    }
}
