package com.example.markethibernate.gui.controller;

import com.example.markethibernate.bll.dtos.CountPerDate;
import com.example.markethibernate.bll.dtos.DeviceBorrowingStatByName;
import com.example.markethibernate.bll.dtos.DeviceBorrowingStatByTime;
import com.example.markethibernate.bll.services.StatisticsService;
import com.example.markethibernate.utils.AppUtil;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import org.apache.poi.ss.formula.functions.T;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class StatisticController {
    @FXML
    private StackedBarChart<String, Number> chart;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;
    @FXML
    private HBox header;

    private DatePicker datePicker;

    private TextField textField;


    public void initStatCheckInByTime(String department, String major) {
        int month = datePicker.getValue().getMonth().getValue();
        int year = datePicker.getValue().getYear();

        chart.getData().clear();

        x.setLabel("Ngày vào");
        y.setLabel("Số lượt vào");

        List<CountPerDate> data = null;
        if (department == null  && major == null) {
            chart.setTitle("Số lượt vào phòng thiết bị trong tháng " + month + "/" + year);
            data = StatisticsService.getInstance().countPersonCheckInMonth(month, year);
        } else if (department != null) {
            chart.setTitle("Số lượt vào phòng thiết bị trong tháng " + month + "/" + year + " của khoa " + department);
            data = StatisticsService.getInstance().countPersonCheckInMonthByDepartment(month, year, department);
        } else {
            chart.setTitle("Số lượt vào phòng thiết bị trong tháng " + month + "/" + year + " của ngành " + major);
            data = StatisticsService.getInstance().countPersonCheckInMonthByMajor(month, year, major);
        }
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        data.forEach(item -> {
            series1.getData().add(new XYChart.Data<>(AppUtil.dateToStringShort(item.getDateTime()), item.getCount()));
        });
        chart.getData().add(series1);
        chart.setAnimated(false);
    }

    public void initStatDeviceByTime() {
        int month = datePicker.getValue().getMonth().getValue();
        int year = datePicker.getValue().getYear();

        chart.getData().clear();

        x.setLabel("Tên thiết bị");
        y.setLabel("Số lượt mượn");

        List<DeviceBorrowingStatByTime> data1 = StatisticsService.getInstance().countDevicesBorrowedInMonth(month, year);
        List<DeviceBorrowingStatByTime> data2 = StatisticsService.getInstance().countDevicesBorrowedInMonthNotReturn(month, year);
        chart.setTitle("Số lượt mượn thiết bị trong tháng " + month + "/" + year);
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series1.setName("Thiết bị đã được mượn");
        series2.setName("Thiết bị đang được mượn");
        data1.forEach(item -> {
            series1.getData().add(new XYChart.Data<>(item.getDeviceId() + "-" + item.getDeviceName(), item.getTimesBorrowed()));
        });
        data2.forEach(item -> {
            series2.getData().add(new XYChart.Data<>(item.getDeviceId() + "-" + item.getDeviceName(), item.getTimesBorrowed()));
        });
        chart.getData().addAll(series1, series2);
        chart.setCategoryGap(50);
        chart.setAnimated(false);


    }

    public void initStatDeviceById() {
        int month = datePicker.getValue().getMonth().getValue();
        int year = datePicker.getValue().getYear();

        chart.getData().clear();

        x.setLabel("Ngày mượn");
        y.setLabel("Số lượt mượn");

        List<DeviceBorrowingStatByName> data = StatisticsService.getInstance().countDevicesBorrowedInMonthById(month, year, textField.getText());
        if(!data.isEmpty()) {
            chart.setTitle("Số lượt mượn thiết bị " + data.get(0).getDeviceName() + " trong tháng " + month + "/" + year);
            XYChart.Series<String, Number> series1 = new XYChart.Series<>();
            data.forEach(item -> series1.getData().add(
                    new XYChart.Data<>(
                            AppUtil.dateToStringShort(item.getDate()), item.getTimesBorrowed())
                    )
            );
            chart.getData().add(series1);
            chart.setAnimated(false);
        } else {
            if(textField.getText().isEmpty()) {
                chart.setTitle("");
            } else {
                chart.setTitle("Không có dữ liệu được ghi nhận vào tháng " + month + "/" + year);
            }
        }
    }

    public void initCombobox() {
        getDatePicker();
        datePicker.setOnAction(ev -> {
            if (datePicker.getValue() != null) {
                if (textField != null) {
                    initStatDeviceById();
                } else {
                    initStatDeviceByTime();
                }
            }
        });
        header.getChildren().add(datePicker);
    }

    public void initTextField() {
        textField = new TextField();
        textField.setPromptText("Nhập mã thiết bị...");
        header.getChildren().add(textField);
        textField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                initStatDeviceById();
            }
        });
    }


    public void initStatPenalize() {
        int month = datePicker.getValue().getMonth().getValue();
        int year = datePicker.getValue().getYear();

        if (header.getChildren().size() > 1) {
            header.getChildren().remove(1);
        }
        Label label = new Label("Tổng số tiền phạt: " + AppUtil.moneyToString(StatisticsService.getInstance().totalPenalize(year, month)));
        label.setStyle("-fx-font-size: 14");
        header.getChildren().add(label);

        chart.getData().clear();

        x.setLabel("Ngày phạt");
        y.setLabel("Số lượng phiếu");

        List<CountPerDate> data1 = StatisticsService.getInstance().countPenalizeInMonthPresent(month, year);
        List<CountPerDate> data2 = StatisticsService.getInstance().countPenalizeInMonthNotPresent(month, year);
        chart.setTitle("Số lượng phiếu phạt trong tháng " + month + "/" + year);
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series1.setName("Đã xử lý");
        series2.setName("Chưa được xử lý");
        data1.forEach(item -> {
            series1.getData().add(new XYChart.Data<>(AppUtil.dateToStringShort(item.getDateTime()), item.getCount()));
        });
        data2.forEach(item -> {
            series2.getData().add(new XYChart.Data<>(AppUtil.dateToStringShort(item.getDateTime()), item.getCount()));
        });
        chart.getData().addAll(series1, series2);
        chart.setAnimated(false);
    }

    public void setTotalPenalize() {
        getDatePicker();
        datePicker.setOnAction(ev -> {
            if (datePicker.getValue() != null) {
                initStatPenalize();
            }
        });
        header.getChildren().add(datePicker);
    }

    public void getDatePicker() {
        datePicker = new DatePicker(YearMonth.now().atDay(1));
        datePicker.setConverter(new StringConverter<LocalDate>() {
            private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/yyyy");
            @Override
            public String toString(LocalDate localDate) {
                if (localDate != null) {
                    return dateFormatter.format(localDate);
                }
                return "";
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return YearMonth.parse(string, dateFormatter).atDay(1);
                }
                return null;
            }
        });
    }

    public void initDepartmentAndMajorField() {
        Button department = new Button("Lọc theo khoa");
        Button major = new Button("Lọc theo ngành");
        department.setOnMouseClicked(event -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Nhập khoa");
            dialog.setHeaderText("Nhập khoa muốn lọc");
            dialog.setContentText("Tên khoa:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(s -> initStatCheckInByTime(s, null));
        });
        major.setOnMouseClicked(event -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Nhập ngành");
            dialog.setHeaderText("Nhập ngành muốn lọc:");
            dialog.setContentText("Tên ngành:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(s -> initStatCheckInByTime(null, s));
        });
        header.getChildren().addAll(department, major);


    }
}
