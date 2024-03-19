package org.doannhom7.shippermanagement.Controllers.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import org.doannhom7.shippermanagement.Models.Model;
import org.doannhom7.shippermanagement.Models.Shipper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
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
    public TableColumn<Shipper, String> btn_col;
    public TableColumn<Shipper, String> password_col;
    public TextField first_name_fd;
    public TextField last_name_fd;
    public DatePicker date_picker;
    public TextField phone_fd;
    public TextField email_fd;
    public TextField address_fd;
    public TextField password_fd;
    public ImageView personal_image_view;
    public Button delete_btn;
    public Button save_btn;
    public Button create_btn;
    public Button edit_btn;
    public Button clear_btn;
    public Button upload_image_btn;
    private final ObservableList<Shipper> shippers = FXCollections.observableArrayList();

    private boolean editFlag;
    private boolean createFlag;
    private boolean deleteFlag;

    public boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public boolean getCreateFlag() {
        return createFlag;
    }

    public void setCreateFlag(boolean createFlag) {
        this.createFlag = createFlag;
    }
    public boolean getEditFlag() {
        return editFlag;
    }
    public void setEditFlag(boolean editFlag) {
        this.editFlag = editFlag;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
        setCreateFlag(true);
        setEditFlag(false);
        setDeleteFlag(true);
        clear_btn.setOnAction(actionEvent -> {
            setEmpty();
        });
        clear_btn.setTooltip(new Tooltip("Clear TextField!"));
        edit_btn.setOnAction(actionEvent -> {
            onEdit();
        });
        save_btn.setOnAction(actionEvent -> {
            if(getEditFlag() & !shipper_table_view.getSelectionModel().isEmpty()) {
                saveShipper();
            }
        });
        delete_btn.setOnAction(actionEvent -> {
            if(!shipper_table_view.getSelectionModel().isEmpty() & getDeleteFlag()) {
                deleteShipper();
            }
        });
        upload_image_btn.setOnAction(actionEvent -> {
            uploadPersonalImage();
        });
        create_btn.setOnAction(actionEvent -> {
            if(getCreateFlag()) {
                if(first_name_fd.getText().isEmpty()) {
                    first_name_fd.setStyle("-fx-border-color: red; -fx-border-width: 2");
                    new animatefx.animation.Shake(first_name_fd).play();
                }else if(last_name_fd.getText().isEmpty()) {
                    last_name_fd.setStyle("-fx-border-color: red; -fx-border-width: 2");
                    new animatefx.animation.Shake(last_name_fd).play();
                }else if(date_picker.getValue() == null) {
                    date_picker.setStyle("-fx-border-color: red; -fx-border-width: 2");
                    new animatefx.animation.Shake(date_picker).play();
                }else if(phone_fd.getText().isEmpty()) {
                    phone_fd.setStyle("-fx-border-color: red; -fx-border-width: 2");
                    new animatefx.animation.Shake(phone_fd).play();
                }else if(email_fd.getText().isEmpty()) {
                    email_fd.setStyle("-fx-border-color: red; -fx-border-width: 2");
                    new animatefx.animation.Shake(email_fd).play();
                }else if(address_fd.getText().isEmpty()) {
                    address_fd.setStyle("-fx-border-color: red; -fx-border-width: 2");
                    new animatefx.animation.Shake(address_fd).play();
                }else if(password_fd.getText().isEmpty()) {
                    password_fd.setStyle("-fx-border-color: red; -fx-border-width: 2");
                    new animatefx.animation.Shake(password_fd).play();
                }else{
                    createShipper();
                }
            }
        });
    }
    private void initTable() {
        this.shippers.clear();
        setCreateFlag(true);
        setDeleteFlag(true);
        setEditFlag(false);
        ResultSet resultSet = Model.getInstance().getDatabaseDriver().getAllShipper();
        try {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("shipper_id");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String phone = resultSet.getString("phone");
                String[] birth = resultSet.getString("birthDay").split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(birth[0]), Integer.parseInt(birth[1]), Integer.parseInt(birth[2]));
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                String password = resultSet.getString("password");
                this.shippers.add(new Shipper(id, firstName, lastName, date, phone, email, address, password));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        shipper_id_col.setCellValueFactory(new PropertyValueFactory<>("shipper_id"));
        first_name_col.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        last_name_col.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        birth_col.setCellValueFactory(new PropertyValueFactory<>("birth"));
        phone_col.setCellValueFactory(new PropertyValueFactory<>("phone"));
        email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
        address_col.setCellValueFactory(new PropertyValueFactory<>("address"));
        password_col.setCellValueFactory(new PropertyValueFactory<>("password"));
        Callback<TableColumn<Shipper, String>, TableCell<Shipper, String>> cellFactory = (TableColumn<Shipper, String> param) -> {
            return new TableCell<Shipper, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        final Button viewOrders = new Button("View Orders");
                        viewOrders.setStyle("-fx-background-color:#0066CCFF; -fx-text-fill: white;");
                        setGraphic(viewOrders);
                        setText(null);
                    }
                };
            };
        };
        personal_image_view.setFitWidth(250);
        personal_image_view.setFitHeight(300);
        btn_col.setCellFactory(cellFactory);
        shipper_table_view.setItems(shippers);
    }
    private void onEdit() {
        if(!shipper_table_view.getSelectionModel().isEmpty()){
            Shipper selectionModel = shipper_table_view.getSelectionModel().getSelectedItem();
            first_name_fd.setStyle(null);
            last_name_fd.setStyle(null);
            phone_fd.setStyle(null);
            date_picker.setStyle(null);
            address_fd.setStyle(null);
            email_fd.setStyle(null);
            password_fd.setStyle(null);
            first_name_fd.setText(selectionModel.firstNameProperty().get());
            last_name_fd.setText(selectionModel.lastNameProperty().get());
            date_picker.setValue(selectionModel.birthProperty().get());
            phone_fd.setText(selectionModel.phoneProperty().get());
            email_fd.setText(selectionModel.emailProperty().get());
            address_fd.setText(selectionModel.addressProperty().get());
            password_fd.setText(selectionModel.passwordProperty().get());
            setDeleteFlag(false);
            setCreateFlag(false);
            setEditFlag(true);
        }


    }
    private void saveShipper() {
            int id = shipper_table_view.getSelectionModel().getSelectedItem().shipper_idProperty().get();
            String fName = first_name_fd.getText();
            String lName = last_name_fd.getText();
            String phone = phone_fd.getText();
            String password = password_fd.getText();
            String birth = date_picker.getValue().toString();
            String email = email_fd.getText();
            String address = address_fd.getText();
            Model.getInstance().getDatabaseDriver().updateShipperData(id, fName, lName,birth, phone, email, address, password);
            setEmpty();
            initTable();
    }
    private void createShipper() {
        if(getEditFlag()){
            return;
        }else{
            String fName = first_name_fd.getText();
            String lName = last_name_fd.getText();
            String phone = phone_fd.getText();
            String password = password_fd.getText();
            String birth = date_picker.getValue().toString();
            String email = email_fd.getText();
            String address = address_fd.getText();
            Model.getInstance().getDatabaseDriver().createNewShipper(fName, lName,birth, phone, email, address, password);
            setEmpty();
            initTable();
        }
    }
    private void deleteShipper() {
        int id = shipper_table_view.getSelectionModel().getSelectedItem().shipper_idProperty().get();
        Model.getInstance().getDatabaseDriver().deleteShipper(id);
        initTable();
    }
    private void setEmpty() {
        first_name_fd.setText("");
        first_name_fd.setStyle("");
        last_name_fd.setText("");
        last_name_fd.setStyle("");
        phone_fd.setText("");
        phone_fd.setStyle("");
        password_fd.setText("");
        password_fd.setStyle("");
        date_picker.setValue(null);
        date_picker.setStyle(null);
        email_fd.setText("");
        email_fd.setStyle("");
        address_fd.setText("");
        address_fd.setStyle("");
    }
    private void uploadPersonalImage() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(upload_image_btn.getScene().getWindow());
        if (file != null) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                Image image = new Image(fileInputStream, personal_image_view.getFitWidth(), personal_image_view.getFitHeight(), true, true);
                personal_image_view.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
