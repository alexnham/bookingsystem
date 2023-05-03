package application;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Arc;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainController extends Utility {

	private Stage stage;
	private Scene scene;
	private Parent root;
	@FXML
	private TextField newName;
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

	public void newEmp(ActionEvent e) throws SQLException {
		if (newName.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please Fill Name");
			alert.show();
		} else {
			if (super.newEmp(e, newName.getText())) {
				success.setText("Employee Added Sucessfully");
			} else {
				success.setText("");
			}
		}
	}

	public void delEmp(ActionEvent e) throws SQLException {
		if (newName.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please Fill Name");
			alert.show();
		} else {
			if (super.delEmp(e, newName.getText())) {
				success.setText("Employee Deleted Successfully");
			} else {
				success.setText("");
			}
		}
	}

	public void changeDate(ActionEvent e) {
		LocalDate selectedDate = selectDate.getValue();
		String formatDate = selectedDate.format(DateTimeFormatter.ofPattern("MMMM dd"));
		date.setText(formatDate);
	}

}
