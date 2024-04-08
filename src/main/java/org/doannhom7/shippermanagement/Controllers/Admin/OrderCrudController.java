package org.doannhom7.shippermanagement.Controllers.Admin;

import animatefx.animation.Shake;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.doannhom7.shippermanagement.Models.Model;
import org.doannhom7.shippermanagement.Models.Order;
import org.doannhom7.shippermanagement.Models.Shipper;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class OrderCrudController implements Initializable {
    public TableView<Order> order_table_view;
    public TableColumn<Order, Integer> order_id_col;
    public TableColumn<Shipper, Integer> shipper_id_col;
    public TableColumn<Order, String> pickup_location_col;
    public TableColumn<Order, String> delivery_location_col;
    public TableColumn<Order, String> deli_date_expect_col;
    public TableColumn<Order, String> value_col;
    public TableColumn<Order, String> other_details_col;
    public TableColumn<Order, String> view_shipper_btn_col;
    public TextField pickup_location_fd;
    public TextField deli_location_fd;
    public DatePicker date_picker;
    public TextField value_fd;
    public TextField other_details_fd;
    public Button save_btn;
    public Button create_btn;
    public Button edit_btn;
    public Button delete_btn;
    public Button clear_btn;
    private final ObservableList<Order> orders = FXCollections.observableArrayList();
    public ComboBox<Integer> shipper_id_combobox;
    public Label first_name_lbl;
    public Label last_name_lbl;
    public Label pNumber_lbl;
    public TextField search_fd;
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
            if(getEditFlag() & !order_table_view.getSelectionModel().isEmpty()) {
                saveShipper();
            }
        });
        delete_btn.setOnAction(actionEvent -> {
            if(!order_table_view.getSelectionModel().isEmpty() & getDeleteFlag()) {
                deleteOrder();
            }
        });
        create_btn.setOnAction(actionEvent -> {
            if(getCreateFlag()) {
                if(shipper_id_combobox.getSelectionModel().getSelectedItem() == null) {
                    shipper_id_combobox.setStyle("-fx-border-color: red; -fx-border-width: 2");
                    new Shake(shipper_id_combobox).play();
                }else if(pickup_location_fd.getText().isEmpty()) {
                    pickup_location_fd.setStyle("-fx-border-color: red; -fx-border-width: 2");
                    new Shake(pickup_location_fd).play();
                }else if(deli_location_fd.getText().isEmpty()) {
                    deli_location_fd.setStyle("-fx-border-color: red; -fx-border-width: 2");
                    new Shake(deli_location_fd).play();
                }else if(date_picker.getValue() == null) {
                    date_picker.setStyle("-fx-border-color: red; -fx-border-width: 2");
                    new Shake(date_picker).play();
                }else if(value_fd.getText().isEmpty()) {
                    value_fd.setStyle("-fx-border-color: red; -fx-border-width: 2");
                    new Shake(value_fd).play();
                }else{
                    createOrder();
                }
            }
        });
    }
    private void initTable() {
        this.orders.clear();
        setCreateFlag(true);
        setEditFlag(false);
        setDeleteFlag(true);
        shipperIdComboBox();
        ResultSet resultSet = Model.getInstance().getDatabaseDriver().getOrdersData();
        try {
            while(resultSet.next()) {
                int order_id = resultSet.getInt("order_id");
                int shipper_id = resultSet.getInt("shipper_id");
                String pickup_location = resultSet.getString("pickup_location");
                String delivery_location = resultSet.getString("delivery_location");
                Double value = resultSet.getDouble("value");
                String other_details = resultSet.getString("other_details");
                String[] date = resultSet.getString("delivery_date_expect").split("-");
                LocalDate localDate = LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
                this.orders.add(new Order(order_id, shipper_id, pickup_location, delivery_location, value, other_details, localDate));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        order_id_col.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        shipper_id_col.setCellValueFactory(new PropertyValueFactory<>("shipperId"));
        pickup_location_col.setCellValueFactory(new PropertyValueFactory<>("pickupLocation"));
        delivery_location_col.setCellValueFactory(new PropertyValueFactory<>("deliveryLocation"));
        value_col.setCellValueFactory(new PropertyValueFactory<>("value"));
        deli_date_expect_col.setCellValueFactory(new PropertyValueFactory<>("deliveryDateExpect"));
        other_details_col.setCellValueFactory(new PropertyValueFactory<>("otherDetails"));
        Callback<TableColumn<Order, String>, TableCell<Order, String>> cellFactory = (TableColumn<Order, String> param) -> new TableCell<>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.USER);
                    icon.setFill(Paint.valueOf("#FFFFFF"));
                    final Button viewShipper = new Button();
                    viewShipper.setGraphic(icon);
                    viewShipper.setStyle("-fx-background-color:#0099CCFF; -fx-text-fill: white; -fx-cursor: hand;");
                    if (getTableRow().getItem().shipperIdProperty().get() == 0) {
                        viewShipper.setDisable(true);
                    } else {
                        viewShipper.setDisable(false);
                        viewShipper.setOnAction(actionEvent -> {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/Admin/ShipperInfoView.fxml"));
                            ResultSet resultSet1 = Model.getInstance().getDatabaseDriver().findShipperById(getTableRow().getItem().shipperIdProperty().get());
                            ShipperInfoViewController shipperOrderTableViewController = new ShipperInfoViewController(resultSet1);
                            fxmlLoader.setController(shipperOrderTableViewController);
                            Stage stage = new Stage();
                            Scene scene;
                            try {
                                scene = new Scene(fxmlLoader.load());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            stage.setScene(scene);
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.setResizable(false);
                            stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/icons8-shipper-64.png"))));
                            stage.show();
                        });
                    }

                    setGraphic(viewShipper);
                    setText(null);
                }
            }

            ;
        };
        view_shipper_btn_col.setCellFactory(cellFactory);
        order_table_view.setItems(orders);

        FilteredList<Order> filteredData = new FilteredList<>(orders, b -> true);

        search_fd.textProperty().addListener((observableValue, oldVal, newVal) -> {
            filteredData.setPredicate(predicateOrder -> {
                if(newVal.isEmpty() || newVal.isBlank()) {
                    return true;
                }
                String searchKeyword = newVal.toLowerCase();
                if(predicateOrder.orderIdProperty().getValue().toString().toLowerCase().contains(searchKeyword)) {
                    return true;
                }else if(predicateOrder.pickupLocationProperty().get().toLowerCase().contains(searchKeyword)) {
                    return true;
                }else if(predicateOrder.deliveryLocationProperty().get().toLowerCase().contains(searchKeyword)) {
                    return true;
                }else if(predicateOrder.valueProperty().getValue().toString().toLowerCase().contains(searchKeyword)) {
                    return true;
                }else return predicateOrder.deliveryDateExpectProperty().get().toString().contains(searchKeyword);
            });
        });

        SortedList<Order> sortedList = new SortedList<>(filteredData);

        sortedList.comparatorProperty().bind(order_table_view.comparatorProperty());

        order_table_view.setItems(sortedList);
    }
    private void onEdit() {
        if(!order_table_view.getSelectionModel().isEmpty()){
            Order selectionModel = order_table_view.getSelectionModel().getSelectedItem();
            pickup_location_fd.setStyle(null);
            deli_location_fd.setStyle(null);
            date_picker.setStyle(null);
            value_fd.setStyle(null);
            other_details_fd.setStyle(null);
            pickup_location_fd.setText(selectionModel.pickupLocationProperty().get());
            deli_location_fd.setText(selectionModel.deliveryLocationProperty().get());
            date_picker.setValue(selectionModel.deliveryDateExpectProperty().get());
            value_fd.setText(Double.toString(selectionModel.valueProperty().get()));
            other_details_fd.setText(selectionModel.otherDetailsProperty().get());
            setDeleteFlag(false);
            setCreateFlag(false);
            setEditFlag(true);
        }
    }
    private void saveShipper() {
        int id = order_table_view.getSelectionModel().getSelectedItem().orderIdProperty().get();
        String pickup_location = pickup_location_fd.getText();
        String delivery_location = deli_location_fd.getText();
        Double value = Double.parseDouble(value_fd.getText());
        String deli_date_expect = date_picker.getValue().toString();
        String other_details = other_details_fd.getText();
        Model.getInstance().getDatabaseDriver().updateOrder(id, shipper_id_combobox.getValue(), pickup_location, delivery_location, value, other_details, deli_date_expect);
        setEmpty();
        initTable();
    }
    private void shipperIdComboBox() {
        ResultSet resultSet = Model.getInstance().getDatabaseDriver().getAllShipper();
        ObservableList<Integer> shipperId = FXCollections.observableArrayList();
        shipper_id_combobox.getSelectionModel().selectedItemProperty().addListener(observable -> shipperInfo());
        try{
            while (resultSet.next()) {
                int id = resultSet.getInt("shipper_id");
                shipperId.add(id);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        shipper_id_combobox.setItems(shipperId);
    }
    private void shipperInfo() {
        if(getCreateFlag() | getEditFlag()) {
            if(shipper_id_combobox.getSelectionModel().isEmpty()){

            }else{
                int id = shipper_id_combobox.getSelectionModel().getSelectedItem();
                ResultSet resultSet = Model.getInstance().getDatabaseDriver().findShipperById(id);
                try {
                    if(resultSet.next()){
                        String firstName = resultSet.getString("firstname");
                        String lastName  = resultSet.getString("lastname");
                        String phone = resultSet.getString("phone");
                        first_name_lbl.setText(firstName);
                        last_name_lbl.setText(lastName);
                        pNumber_lbl.setText(phone);
                    }

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void createOrder() {
        if(getEditFlag()){
            return;
        }else{
            int shipper_id = shipper_id_combobox.getValue();
            String pickup_location = pickup_location_fd.getText();
            String delivery_location = deli_location_fd.getText();
            String date = date_picker.getValue().toString();
            String other_details = other_details_fd.getText();
            Double value = Double.parseDouble(value_fd.getText());
            int order_id = Model.getInstance().getDatabaseDriver().createOrder(shipper_id, pickup_location, delivery_location, value, other_details, date);
            Model.getInstance().getDatabaseDriver().createDeliveryNote(order_id,0, "Not Delivery", "");
            setEmpty();
            initTable();
        }
    }
    private void deleteOrder() {
        int id = order_table_view.getSelectionModel().getSelectedItem().orderIdProperty().get();
        Model.getInstance().getDatabaseDriver().deleteOrder(id);
        initTable();
    }
    private void setEmpty() {
        pickup_location_fd.setText("");
        pickup_location_fd.setStyle("");
        deli_location_fd.setText("");
        deli_location_fd.setStyle("");
        value_fd.setText("");
        value_fd.setStyle("");
        date_picker.setValue(null);
        date_picker.setStyle(null);
        other_details_fd.setText("");
        other_details_fd.setStyle("");
        shipper_id_combobox.getItems().clear();
        first_name_lbl.setText("");
        last_name_lbl.setText("");
        pNumber_lbl.setText("");
        initTable();
    }
}
