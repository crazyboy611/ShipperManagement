package org.doannhom7.shippermanagement.Controllers.Shipper;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.doannhom7.shippermanagement.Models.Model;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ShipperStatistic implements Initializable {
    public BarChart<String, Number> shipper_order_statistic;
    public Label quantity_lbl;
    public Label value_lbl;
    public Button reset_btn;
    private int id;
    XYChart.Series<String, Number> valueOfChart;
    ObservableList<XYChart.Data<String, Number>> data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
        reset_btn.setOnAction(actionEvent -> {
            refreshChart();
        });
    }
    private void refreshChart() {
        this.id = Model.getInstance().getShipper().shipperIdProperty().get();
        valueOfChart.getChart().getData().clear();
        shipper_order_statistic.getData().clear();
        init();
    }
    private void init() {
        this.valueOfChart = new XYChart.Series<>();
        this.id = Model.getInstance().getShipper().shipperIdProperty().get();
        ResultSet resultSet = null;
        XYChart.Data<String, Number> dataPoint;
        try {
            this.shipper_order_statistic.getData().clear();
            this.data = FXCollections.observableArrayList();
            resultSet =  Model.getInstance().getDatabaseDriver().getShipperOrderStatistic(id);
            double sumPrice = 0.00;
            int quantity = 0;
            while(resultSet.next()) {
                dataPoint = new XYChart.Data<>(resultSet.getString(1), resultSet.getDouble(2));
                data.add(dataPoint);
                sumPrice = sumPrice + resultSet.getDouble(2);
                quantity = quantity + resultSet.getInt(3);
                String text = dataPoint.getYValue() + "";
//                displayLabelForData(dataPoint);
                StackPane node = new StackPane();
                Label label = new Label(text);
                label.setStyle("-fx-text-fill: white; -fx-font-family: Arial; -fx-font-size: 1.2em;");
//                label.setRotate(-90);
                Group group = new Group(label);
                StackPane.setAlignment(group, Pos.TOP_CENTER);
                StackPane.setMargin(group, new Insets(0, 0, 5, 0));
                node.getChildren().add(group);
                dataPoint.setNode(node);
            }
            valueOfChart.setData(data);
            shipper_order_statistic.getData().add(valueOfChart);
            value_lbl.setText(sumPrice + " VND");
            quantity_lbl.setText(String.valueOf(quantity));
            valueOfChart.setName("Tong gia tri don hang giao duoc trong thang");
            shipper_order_statistic.setBarGap(5);
            shipper_order_statistic.setCategoryGap(20);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void displayLabelForData(XYChart.Data<String, Number> data) {
        Node node = data.getNode();
        Text dataText = new Text(data.getYValue() + "");
        node.parentProperty().addListener(new ChangeListener<Parent>() {
            @Override
            public void changed(ObservableValue<? extends Parent> observableValue, Parent parent, Parent t1) {
                Group parentGroup = (Group) parent;
                parentGroup.getChildren().add(dataText);
            }
        });
        node.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> ov, Bounds oldBounds, Bounds bounds) {
                dataText.setLayoutX(
                        Math.round(
                                bounds.getMinX() + bounds.getWidth() / 2 - dataText.prefWidth(-1) / 2
                        )
                );
                dataText.setLayoutY(
                        Math.round(
                                bounds.getMinY() - dataText.prefHeight(-1) * 0.5
                        )
                );
            }
        });
    }
}
