package org.doannhom7.shippermanagement.Controllers.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.doannhom7.shippermanagement.Models.Model;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class AdminStatisticController implements Initializable {
    public Label quantity_lbl;
    public Label value_lbl;
    public BarChart<String, Double> statistic_chart;
    public ComboBox<String> year_combobox;
    private double value;
    private int quantity;
    private String year;
    private final XYChart.Series<String, Double> chart = new XYChart.Series<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.year = String.valueOf(LocalDate.now().getYear());
        init();
        byYearStatistic();
        year_combobox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldVal, newVal) -> {
            this.value = 0;
            this.quantity = 0;
            this.chart.getData().clear();
            this.statistic_chart.getData().clear();
            this.year = newVal;
            init();
        });
    }
    private void init() {
        ResultSet resultSet = Model.getInstance().getDatabaseDriver().getAdminOrderStatistic(this.year);
        try{
            while(resultSet.next()) {
                XYChart.Data<String, Double> data = new XYChart.Data<>();
                data.setXValue(resultSet.getString(1));
                data.setYValue(resultSet.getDouble(2));
                chart.getData().add(data);
                this.value = this.value + resultSet.getDouble(2);
                this.quantity = this.quantity + resultSet.getInt(3);
                String text = data.getYValue() +"";
                StackPane node = new StackPane();
                Label label = new Label(text);
                label.setStyle("-fx-text-fill: white; -fx-font-family: Arial; -fx-font-size: 1.2em;");
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
            statistic_chart.getYAxis().setLabel("Value");
            statistic_chart.autosize();
            this.chart.setName("Tong gia tri tat ca cac don hang da giao duoc trong nam");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void byYearStatistic() {
        ObservableList<String> years = FXCollections.observableArrayList();
        int currentYear = LocalDate.now().getYear();
        int oldYear = currentYear - 5;
        for(int i = currentYear; i > oldYear; i--) {
           String year = String.valueOf(i);
           years.add(year);
        }
        year_combobox.setValue(String.valueOf(LocalDate.now().getYear()));
        year_combobox.setItems(years);
    }
}
