package org.doannhom7.shippermanagement.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.doannhom7.shippermanagement.Models.Model;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class AdminStatisticController implements Initializable {
    public Label quantity_lbl;
    public Label value_lbl;
    public BarChart<String, Double> statistic_chart;
    private double value;
    private int quantity;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
    }
    private void init() {
        ResultSet resultSet = Model.getInstance().getDatabaseDriver().getAdminOrderStatistic();
        try{
            XYChart.Series<String, Double> chart = new XYChart.Series<>();
            while(resultSet.next()) {
                XYChart.Data<String, Double> data = new XYChart.Data<>(resultSet.getString(1), resultSet.getDouble(2) );
                chart.getData().add(data);
                value = value + resultSet.getDouble(2);
                quantity = quantity + resultSet.getInt(3);
                String text = data.getYValue() +"";
//                displayLabelForData(dataPoint);
                StackPane node = new StackPane();
                Label label = new Label(text);
                label.setStyle("-fx-text-fill: white; -fx-font-family: Arial; -fx-font-size: 1.2em;");
//                label.setRotate(-90);
                Group group = new Group(label);
                StackPane.setAlignment(group, Pos.TOP_CENTER);
                StackPane.setMargin(group, new Insets(0, 0, 5, 0));
                node.getChildren().add(group);
                data.setNode(node);
            }
            value_lbl.setText(value + "VND");
            quantity_lbl.setText(String.valueOf(quantity));
            statistic_chart.getData().add(chart);
            statistic_chart.getXAxis().setAutoRanging(true);
            statistic_chart.getYAxis().setAutoRanging(true);
            statistic_chart.autosize();
            chart.setName("Tong gia tri tat ca cac don hang da giao duoc trong nam");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
