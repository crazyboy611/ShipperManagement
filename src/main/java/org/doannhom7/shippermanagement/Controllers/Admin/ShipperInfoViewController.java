package org.doannhom7.shippermanagement.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ShipperInfoViewController implements Initializable {
    private final ResultSet resultSet;
    public Label id_lbl;
    public Label fName_lbl;
    public Label lName_lbl;
    public Label phone_lbl;
    public Label address_lbl;
    public Label email_lbl;
    public Label birth_lbl;
    public ImageView image_view;

    public ShipperInfoViewController(ResultSet resultSet) {
        this.resultSet = resultSet;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            if(resultSet.next()) {
                int id = resultSet.getInt("shipper_id");
                String fName = resultSet.getString("firstname");
                String lName = resultSet.getString("lastname");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                String birth = resultSet.getString("birthDay");
                String email = resultSet.getString("email");
                id_lbl.setText(String.valueOf(id));
                fName_lbl.setText(fName);
                lName_lbl.setText(lName);
                phone_lbl.setText(phone);
                address_lbl.setText(address);
                email_lbl.setText(email);
                birth_lbl.setText(birth);
                InputStream inputStream = resultSet.getBinaryStream("personal_image");
                Image image = new Image(inputStream);
                image_view.setImage(image);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
