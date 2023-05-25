package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class BookingController extends MainController implements Initializable {

	@FXML
	private TextField customerName;
	@FXML
	private TextField phoneNum;
	@FXML
	private Text success;
	@FXML
	private DatePicker selectDate;
	@FXML
	private Text date;
	@FXML
	private ComboBox<Employee> selectEmp;
	@FXML
	private ComboBox<String> startTime;
	@FXML
	private Text endTime;
	@FXML
	private ComboBox<Service> service;

	public void home(ActionEvent e) {
		super.home(e);
	}

	public void employees(ActionEvent e) throws SQLException {
		super.employees(e);
	}

	public void booking(ActionEvent e) {
		super.booking(e);

	}

	public void appointment(ActionEvent e) {
		super.appointment(e);
	}
	public void cancelApp(ActionEvent e) {
		super.swap(e, "DelAppointment.fxml");
	}
	public void delService(ActionEvent e) {
		super.swap(e, "DelService.fxml");
	}

	public void newService(ActionEvent e) {
		super.swap(e, "AddService.fxml");
	}

	public void finishBooking(ActionEvent e) throws SQLException, IOException {
		if (customerName.getText().equals("") || startTime.getSelectionModel().isEmpty()
				|| service.getSelectionModel().isEmpty() || phoneNum.getText().equals("")) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please Fill all Boxes");
			alert.show();
		} else if (phoneNum.getText().length() != 10) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please Enter a real number");
			alert.show();
		} else {

			if (super.bookAppointment(customerName.getText(), service.getValue(), selectEmp.getValue(),
					returnKey(startTime.getValue()), returnKey(endTime.getText()), phoneNum.getText(),
					selectDate.getValue())) {
				success.setStyle("-fx-fill: green;");
				success.setText("Appointment Created");
			} else {
				success.setStyle("-fx-fill: red;");
				success.setText("Appointment Not Created");

			}
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			super.fillServices(service);
		} catch (Exception e) {
		}
		selectDate.setOnAction(event -> {
			startTime.getItems().clear();
			selectEmp.getItems().clear();
			endTime.setText("");
			service.setValue(null);
		});
		service.setOnAction(event -> {
			try {
				super.fillEmp(selectEmp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
			startTime.getItems().clear();
			endTime.setText("");
		});
		// must check if time fits in which employee
		selectEmp.setOnAction(event -> {
			try {
				super.fillStartTime(startTime, service.getValue(), selectEmp.getValue().getEmpID(),
						selectDate.getValue());
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
		});
		startTime.setOnAction(event -> super.setEnd(endTime, startTime.getValue(), service.getValue()));
	}

}
