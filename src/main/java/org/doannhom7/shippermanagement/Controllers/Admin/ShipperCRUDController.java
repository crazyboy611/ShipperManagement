package org.doannhom7.shippermanagement.Controllers.Admin;

import animatefx.animation.Shake;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.textfield.TextFields;
import org.doannhom7.shippermanagement.Models.Model;
import org.doannhom7.shippermanagement.Models.Shipper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

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
    public Label error_lbl;
    public TextField search_field;
    public Label shipper_id;
    public Label phone_exist_error_lbl;

    private boolean editFlag;
    private boolean createFlag;
    private boolean deleteFlag;
    int index = -1;

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
        getShipperSelected();
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
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm");
                alert.setHeaderText("Are you sure you want to delete this Shipper?");
                Optional<ButtonType> buttonType = alert.showAndWait();
                if(buttonType.get() == ButtonType.OK) {
                    deleteShipper();
                }
            }
        });
        upload_image_btn.setOnAction(actionEvent -> {
            uploadPersonalImage();
        });
        create_btn.setOnAction(actionEvent -> {
            if(getCreateFlag()) {
                if(first_name_fd.getText().isEmpty()) {
                    first_name_fd.setStyle("-fx-border-color: red; -fx-border-width: 2");
                    new Shake(first_name_fd).play();
                }else if(last_name_fd.getText().isEmpty()) {
                    last_name_fd.setStyle("-fx-border-color: red; -fx-border-width: 2");
                    new Shake(last_name_fd).play();
                }else if(date_picker.getValue() == null) {
                    date_picker.setStyle("-fx-border-color: red; -fx-border-width: 2");
                    new Shake(date_picker).play();
                }else if(phone_fd.getText().isEmpty()) {
                    phone_fd.setStyle("-fx-border-color: red; -fx-border-width: 2");
                    new Shake(phone_fd).play();
                }else if(email_fd.getText().isEmpty()) {
                    email_fd.setStyle("-fx-border-color: red; -fx-border-width: 2");
                    new Shake(email_fd).play();
                }else if(address_fd.getText().isEmpty()) {
                    address_fd.setStyle("-fx-border-color: red; -fx-border-width: 2");
                    new Shake(address_fd).play();
                }else if(password_fd.getText().isEmpty()) {
                    password_fd.setStyle("-fx-border-color: red; -fx-border-width: 2");
                    new Shake(password_fd).play();
                }else if(personal_image_view.getImage() == null) {
                    error_lbl.setText("Please Load Personal Image!");
                    error_lbl.setStyle("-fx-text-fill: red;");
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
        shipper_id_col.setCellValueFactory(new PropertyValueFactory<>("shipperId"));
        first_name_col.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        last_name_col.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        birth_col.setCellValueFactory(new PropertyValueFactory<>("birth"));
        phone_col.setCellValueFactory(new PropertyValueFactory<>("phone"));
        email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
        address_col.setCellValueFactory(new PropertyValueFactory<>("address"));
        password_col.setCellValueFactory(new PropertyValueFactory<>("password"));
        Callback<TableColumn<Shipper, String>, TableCell<Shipper, String>> cellFactory = (TableColumn<Shipper, String> param) -> new TableCell<>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);

                } else {
                    final Button viewOrders = new Button("View Orders");
                    viewOrders.setStyle("-fx-background-color:#0066CCFF; -fx-text-fill: white; -fx-cursor: hand;");
                    viewOrders.setOnAction(actionEvent -> {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/Admin/ShipperOrderView.fxml"));
                        ResultSet resultSet1 = Model.getInstance().getDatabaseDriver().getOrdersByShipperId(getTableRow().getItem().shipperIdProperty().get());
                        String shipper_name = getTableRow().getItem().lastNameProperty().get();
                        ShipperOrderTableViewController shipperOrderTableViewController = new ShipperOrderTableViewController(resultSet1, shipper_name);
                        fxmlLoader.setController(shipperOrderTableViewController);
                        Stage stage = new Stage();
                        Scene scene;
                        try {
                            scene = new Scene(fxmlLoader.load());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        stage.setScene(scene);
                        stage.setTitle("Shipper Information");
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setResizable(false);
                        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/icons8-shipper-64.png"))));
                        stage.show();
                    });
                    setGraphic(viewOrders);
                    setText(null);
                }
            }

            ;
        };
        personal_image_view.setFitWidth(250);
        personal_image_view.setFitHeight(300);
        btn_col.setCellFactory(cellFactory);
        shipper_table_view.setItems(shippers);

        FilteredList<Shipper> filteredData = new FilteredList<>(shippers, b -> true);

         search_field.textProperty().addListener((observableValue, oldVal, newVal) -> {
             filteredData.setPredicate(shipper -> {
                 if(newVal.isEmpty() || newVal.isBlank()) {
                     return true;
                 }
                 String searchKeyword = newVal.toLowerCase();
                 return shipper.firstNameProperty().toString().toLowerCase().contains(searchKeyword) ||
                         shipper.lastNameProperty().get().toLowerCase().contains(searchKeyword) ||
                         Integer.valueOf(shipper.shipperIdProperty().get()).toString().toLowerCase().contains(searchKeyword) ||
                         shipper.phoneProperty().get().toLowerCase().contains(searchKeyword) ||
                         shipper.addressProperty().get().toLowerCase().contains(searchKeyword);
             });
         });

        SortedList<Shipper> sortedList = new SortedList<>(filteredData);

        sortedList.comparatorProperty().bind(shipper_table_view.comparatorProperty());

        shipper_table_view.setItems(sortedList);

    }
    private void getShipperSelected() {
        shipper_table_view.setOnMouseClicked(event -> {
            index = shipper_table_view.getSelectionModel().getSelectedIndex();
            if(index <= -1) {
                return;
            }
            shipper_id.setText(String.valueOf(shipper_id_col.getCellData(index)));
            shipper_id.setVisible(false);
            first_name_fd.setText(first_name_col.getCellData(index));
            last_name_fd.setText(last_name_col.getCellData(index));
            phone_fd.setText(phone_col.getCellData(index));
            address_fd.setText(address_col.getCellData(index));
            email_fd.setText(email_col.getCellData(index));
            String[] date = String.valueOf(birth_col.getCellData(index)).split("-");
            LocalDate localDate = LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
            date_picker.setValue(localDate);
            password_fd.setText(password_col.getCellData(index));
            showPersonalImage(shipper_table_view.getSelectionModel().getSelectedItem().shipperIdProperty().get());
        });
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
            int id = shipper_table_view.getSelectionModel().getSelectedItem().shipperIdProperty().get();
            String fName = first_name_fd.getText();
            String lName = last_name_fd.getText();
            String phone = phone_fd.getText();
            String password = password_fd.getText();
            String birth = date_picker.getValue().toString();
            String email = email_fd.getText();
            String address = address_fd.getText();
            InputStream inputStream = imageToInputStream(personal_image_view.getImage());
//            boolean exist = false;
//            for(Shipper shipper : shippers) {
//                if(phone.equals(shipper.phoneProperty().get())) {
//                    phone_exist_error_lbl.setText("This Phone has already exist!");
//                    phone_exist_error_lbl.setStyle("-fx-text-fill: red;");
//                    exist = true;
//                }
//            }
//        boolean finalExist = exist;
//        phone_fd.textProperty().addListener((observableValue, oldVal, newVal) -> {
//                    if(newVal.equals(oldVal)) {
                        Model.getInstance().getDatabaseDriver().updateShipperData(id, fName, lName,birth, phone, email, address, password, inputStream);
                        setEmpty();
                        initTable();
//                    }
//                    if(!finalExist) {
//                        Model.getInstance().getDatabaseDriver().updateShipperData(id, fName, lName,birth, phone, email, address, password, inputStream);
//                        setEmpty();
//                        initTable();
//                    }
//            });
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
            InputStream image = imageToInputStream(personal_image_view.getImage());
            boolean exist = false;
            for(Shipper shipper : shippers) {
                if(phone.equals(shipper.phoneProperty().get())) {
                    phone_exist_error_lbl.setText("This Phone has already exist!");
                    phone_exist_error_lbl.setStyle("-fx-text-fill: red;");
                    exist = true;
                }
            }
            if(!exist) {
                Model.getInstance().getDatabaseDriver().createNewShipper(fName, lName, birth, phone, email, address, password, image);
                setEmpty();
                initTable();
            }

        }
    }
    private void deleteShipper() {
        int id = Integer.parseInt(shipper_id.getText());
        Model.getInstance().getDatabaseDriver().deleteShipper(id);
        personal_image_view.setImage(null);
        setEmpty();
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
        personal_image_view.setImage(null);
        phone_exist_error_lbl.setText("");
        initTable();
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
    private void showPersonalImage(int id) {
        InputStream inputStream = null;
        try{
            inputStream = Model.getInstance().getDatabaseDriver().getPersonalImage(id);
            if(inputStream != null){
                Image image = new Image(inputStream, personal_image_view.getFitWidth(), personal_image_view.getFitHeight(), true, true);
                personal_image_view.setImage(image);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static InputStream imageToInputStream(Image image) {
        WritableImage writableImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());
        PixelReader pixelReader = image.getPixelReader();
        BufferedImage bufferedImage = new BufferedImage((int) image.getWidth(), (int) image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int argb = pixelReader.getArgb(x, y);
                bufferedImage.setRGB(x, y, argb);
            }
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
