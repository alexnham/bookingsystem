package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainController extends Utility implements Initializable {

	@FXML
	private TextField appID;
	@FXML
	private TextField newName;
	@FXML
	private TextField serviceName;
	@FXML
	private ComboBox<String> serviceDuration;
	@FXML
	private Text success;
	@FXML
	private DatePicker selectDate;
	@FXML
	private Text date;

	public void home(ActionEvent e) {
		super.swap(e, "HomeScreen.fxml");
	}

	public void employees(ActionEvent e) throws SQLException {
		super.swap(e, "Employees.fxml");
	}

	public void booking(ActionEvent e) {
		super.swap(e, "Booking.fxml");

	}

	public void appointment(ActionEvent e) {
		super.swap(e, "Appointments.fxml");
	}

	public void newEmp(ActionEvent e) throws SQLException, FileNotFoundException {
		if (newName.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please Fill Name");
			alert.show();
		} else {
			if (super.newEmp(e, newName.getText())) {
				success.setStyle("-fx-fill: #2ec684");
				success.setText("Employee Added Sucessfully");
			} else {
				success.setStyle("-fx-fill: #FF5733");
				success.setText("Employee Already Exists");
			}
		}
	}

	public void delEmp(ActionEvent e) throws SQLException, FileNotFoundException {
		if (newName.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please Fill Name");
			alert.show();
		} else {
			for (int i = 0; i < super.getEmployees().size(); i++) {
				if (super.getEmployees().get(i).getEmpName().equals(newName.getText())) {
					super.delEmp(e, super.getEmployees().get(i));
					success.setStyle("-fx-fill: #2ec684");
					success.setText("Employee Deleted Successfully");
				} else {
					success.setStyle("-fx-fill: #FF5733");
					success.setText("Employee Not Found");

				}
			}

		}
	}

	public void delService(ActionEvent e) throws SQLException, FileNotFoundException {
		if (newName.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please Fill Name");
			alert.show();
		} else {
			if (super.delService(e, newName.getText())) {
				success.setText("Service Deleted Successfully");
			} else {
				success.setText("");
			}
		}
	}

	public void addService(ActionEvent e) throws FileNotFoundException, SQLException {
		if (serviceName.getText().isEmpty() || serviceDuration.getValue() == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please Fill Both Name and Duration");
			alert.show();
		} else {
			super.newService(e, serviceName.getText(), serviceDuration.getValue());
			success.setText("Service Added Successfully");
		}
	}

	public void cancelApp(ActionEvent e) {
		try {
			if (super.cancelApp(Integer.valueOf(appID.getText()))) {
				success.setStyle("-fx-fill: #2ec684");
				success.setText("Appointment Cancelled Successfully");
			} else {
				success.setStyle("-fx-fill: #FF5733");
				success.setText("Appointment Not Found");
			}
		} catch (NumberFormatException | FileNotFoundException | SQLException e1) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please Enter A Valid ID Number");
			alert.show();
		}

	}

	public void changeDate(ActionEvent e) {
		LocalDate selectedDate = selectDate.getValue();
		String formatDate = selectedDate.format(DateTimeFormatter.ofPattern("MMMM dd"));
		date.setText(formatDate);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		if (serviceDuration != null) {
			serviceDuration.getItems().add("00:15:00");
			serviceDuration.getItems().add("00:30:00");
			serviceDuration.getItems().add("00:45:00");
			serviceDuration.getItems().add("01:00:00");

		}
	}

}
