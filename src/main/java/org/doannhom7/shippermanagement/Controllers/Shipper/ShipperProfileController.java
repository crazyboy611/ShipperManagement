package org.doannhom7.shippermanagement.Controllers.Shipper;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.doannhom7.shippermanagement.Models.Model;
import org.doannhom7.shippermanagement.Models.Shipper;
import org.doannhom7.shippermanagement.Views.ShipperMenuOptions;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ShipperProfileController implements Initializable {
    public ImageView shipper_image;
    public Label shipper_id_lbl;
    public Label first_name;
    public Label last_name;
    public Label phone_num;
    public Label birth_lbl;
    public Label email_lbl;
    public Label address_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initData();
        Model.getInstance().getViewFactory().getShipperSelectedMenu().addListener((observable, oldValue, newValue) -> {
            if (newValue == ShipperMenuOptions.MY_PROFILE) {
                initData();
            }
        });
    }
    private void initData() {
        Shipper shipper = Model.getInstance().getShipper();

        shipper_id_lbl.textProperty().bind(Model.getInstance().getShipper().shipperIdProperty().asString());
        first_name.textProperty().bind(Model.getInstance().getShipper().firstNameProperty());
        last_name.textProperty().bind(Model.getInstance().getShipper().lastNameProperty());
        phone_num.textProperty().bind(Model.getInstance().getShipper().phoneProperty());
        birth_lbl.textProperty().bind(Model.getInstance().getShipper().birthProperty().asString());
        email_lbl.textProperty().bind(Model.getInstance().getShipper().emailProperty());
        address_lbl.textProperty().bind(Model.getInstance().getShipper().addressProperty());

        InputStream inputStream = Model.getInstance().getDatabaseDriver().getPersonalImage(shipper.shipperIdProperty().get());

        shipper_image.setImage(new Image(inputStream));
    }
}
