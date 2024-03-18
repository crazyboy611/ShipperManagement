package org.doannhom7.shippermanagement.Controllers.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.doannhom7.shippermanagement.Models.Model;
import org.doannhom7.shippermanagement.Models.Shipper;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ShipperCRUDController implements Initializable {
    public TableView<Shipper> shipper_table_view;
    public TableColumn<Shipper, String> shipper_id_col;
    public TableColumn<Shipper, String> first_name_col;
    public TableColumn<Shipper, String> last_name_col;
    public TableColumn<Shipper, String> birth_col;
    public TableColumn<Shipper, String> phone_col;
    public TableColumn<Shipper, String> email_col;
    public TableColumn<Shipper, String> address_col;
    public TextField first_name_fd;
    public TextField last_name_fd;
    public DatePicker date_picker;
    public TextField phone_fd;
    public TextField email_fd;
    public TextField address_fd;
    public Button create_btn;
    public Button update_btn;
    public Button delete_btn;
    public Button refresh_btn;

    private final ObservableList<Shipper> shippers = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
    }
    private void initTable() {
            ResultSet resultSet = Model.getInstance().getDatabaseDriver().getAllShipper();
            try{
                while (resultSet.next()) {
                    Integer id = resultSet.getInt("shipper_id");
                    String firstName = resultSet.getString("firstname");
                    String lastName = resultSet.getString("lastname");
                    String phone = resultSet.getString("phone");
                    String[] birth = resultSet.getString("birthDay").split("-");
                    LocalDate date = LocalDate.of(Integer.parseInt(birth[0]), Integer.parseInt(birth[1]), Integer.parseInt(birth[2]));
                    String email = resultSet.getString("email");
                    String address = resultSet.getString("address");
                    this.shippers.add(new Shipper(id,firstName, lastName, date, phone, email, address));
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            shipper_id_col.setCellValueFactory(new PropertyValueFactory<>("shipper_id"));
            first_name_col.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            last_name_col.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            birth_col.setCellValueFactory(new PropertyValueFactory<>("birth"));
            phone_col.setCellValueFactory(new PropertyValueFactory<>("phone"));
            email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
            address_col.setCellValueFactory(new PropertyValueFactory<>("address"));
            shipper_table_view.setItems(shippers);
    }

}
