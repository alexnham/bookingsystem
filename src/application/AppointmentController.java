package application;

import java.io.FileNotFoundException;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class AppointmentController extends MainController implements Initializable {

	@FXML
	private TextField newName;
	@FXML
	private DatePicker selectDate;
	@FXML
	private Text date;
	@FXML
	private ComboBox<Employee> selectEmp;
	@FXML
	private GridPane timeTable;

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

	public void changeDate(ActionEvent e) {
		LocalDate selectedDate = selectDate.getValue();
		String formatDate = selectedDate.format(DateTimeFormatter.ofPattern("MMMM dd"));
		date.setText(formatDate);
		if (selectEmp.getValue() != null) {
			try {
				assignTable(timeTable, selectEmp.getValue(), selectDate.getValue());
			} catch (FileNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<Employee> employees = null;
		try {
			employees = super.getEmployees();

			LocalDate now = LocalDate.now();
			selectDate.setValue(now);
			if (date != null) {
				for (int i = 0; i < employees.size(); i++) {
					selectEmp.getItems().add(employees.get(i));
				}
				date.setText(now.format(DateTimeFormatter.ofPattern("MMMM dd")));
			}
			selectEmp.setOnAction(event -> {
				try {
					assignTable(timeTable, selectEmp.getValue(), selectDate.getValue());
				} catch (FileNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			});

		} catch (Exception e) {
		}
	}

}
