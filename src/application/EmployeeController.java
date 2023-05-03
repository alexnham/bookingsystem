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
import javafx.scene.control.Label;

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

public class EmployeeController extends MainController implements Initializable {

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
	@FXML
	private TableView<Employee> empList;
	@FXML
	private TableColumn<Employee, Integer> empID;
	@FXML
	private TableColumn<Employee, String> empName;
	ObservableList<Employee> list; 
			//=FXCollections.observableArrayList(

		//	new Employee("alex",1), new Employee("nham",2)

	//);


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

	public void add(ActionEvent e) {
		super.swap(e, "AddEmployee.fxml");
	}

	public void del(ActionEvent e) {
		super.swap(e, "DelEmployee.fxml");
	}

	public void changeDate(ActionEvent e) {
		LocalDate selectedDate = selectDate.getValue();
		String formatDate = selectedDate.format(DateTimeFormatter.ofPattern("MMM dd"));
		date.setText(formatDate);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			list = super.loadEmployees();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		empName.setCellValueFactory(new PropertyValueFactory<Employee, String>("empName"));
		empID.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("empID"));
		empList.setItems(list);
	}

}
