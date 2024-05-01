package org.doannhom7.shippermanagement.Controllers;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.doannhom7.shippermanagement.Views.AccountType;
import org.doannhom7.shippermanagement.Models.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public ChoiceBox<AccountType> acc_selector;

    public Label shipper_phone;
    public TextField shipper_phone_field;
    public PasswordField password_field;
    public Button login_btn;
    public Label error_label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        acc_selector.setItems(FXCollections.observableArrayList(AccountType.ADMIN, AccountType.SHIPPER));
        acc_selector.setValue(Model.getInstance().getViewFactory().getAccountType());
        acc_selector.valueProperty().addListener(observable -> {
            setAcc_selector();
        });
        login_btn.setOnAction(actionEvent -> onLogin());
    }

    private void setAcc_selector() {
        Model.getInstance().getViewFactory().setAccountType(acc_selector.getValue());
        if(acc_selector.getValue() == AccountType.SHIPPER) {
            shipper_phone.setText("Phone: ");
        }else{
            shipper_phone.setText("Username: ");
        }
    }
    private void onLogin() {
        Stage stage = (Stage) error_label.getScene().getWindow();
        if (Model.getInstance().getViewFactory().getAccountType() == AccountType.SHIPPER) {
            Model.getInstance().evaluateShipperCred(shipper_phone_field.getText(), password_field.getText());
            if(Model.getInstance().getShipperLoginSuccessFlag()){
                Model.getInstance().getViewFactory().showShipperWindow();
                Model.getInstance().getViewFactory().closeStage(stage);
            }else{
                shipper_phone_field.setText("");
                password_field.setText("");
                error_label.setText("No Such Login Credentials");
            }
        }
        else{
            if(Model.getInstance().getViewFactory().getAccountType() == AccountType.ADMIN) {
                Model.getInstance().evaluateAdminCred(shipper_phone_field.getText(), password_field.getText());
                if(Model.getInstance().getAdminLoginSuccessFlag()) {
                    Model.getInstance().getViewFactory().showAdminWindow();
                    Model.getInstance().getViewFactory().closeStage(stage);
                }else{
                    shipper_phone_field.setText("");
                    password_field.setText("");
                    error_label.setText("No Such Login Credentials");
                }
            }
        }
    }
}
