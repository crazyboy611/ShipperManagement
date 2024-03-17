package org.doannhom7.shippermanagement.Controllers;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.doannhom7.shippermanagement.Models.AccountType;
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
    }

    public ChoiceBox<AccountType> getAcc_selector() {
        return acc_selector;
    }
    private void setAcc_selector() {
        Model.getInstance().getViewFactory().setAccountType(acc_selector.getValue());
        if(acc_selector.getValue() == AccountType.ADMIN) {
            shipper_phone.setText("Username: ");
        }else{
            shipper_phone.setText("Phone: ");
        }
    }
}
