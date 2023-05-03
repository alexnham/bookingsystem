package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Utility {

	private Parent root;
	private Stage stage;
	private Stage TempStage;

	
	
	public ObservableList<Employee> loadEmployees() throws SQLException {
		ObservableList<Employee> list = FXCollections.observableArrayList();
		Connection connection = null; 
		PreparedStatement psReturnEmp = null;
		ResultSet resultSet = null;
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Booking", "root", "12345678");
		psReturnEmp = connection.prepareStatement("SELECT * FROM employees");
		resultSet = psReturnEmp.executeQuery();
		
		while(resultSet.next())
			list.add(new Employee(resultSet.getString("name"), resultSet.getInt("ID")));
		return list;
	}
	
	public void swap(ActionEvent e, String s) {
		try {
			root = FXMLLoader.load(getClass().getResource(s));
			stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();

		} catch (Exception f) {
			f.printStackTrace();
		}
	}

	public boolean newEmp(ActionEvent e, String name) throws SQLException {
		System.out.println("add");
		Connection connection = null;
		PreparedStatement psInsert = null;
		PreparedStatement topKey = null;
		ResultSet getTopKey = null;
		int key;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Booking", "root", "12345678");
			// psCheckEmpExists = connection.prepareStatement("SELECT * FROM employees WHERE
			// name = ?" );
			// psCheckEmpExists.setString(1, name);
			// resultSet = psCheckEmpExists.executeQuery();
			
			topKey = connection.prepareStatement("SELECT MAX(ID) FROM employees");
			getTopKey = topKey.executeQuery();
			getTopKey.next();
			key = getTopKey.getInt("MAX(ID)")+1;
			
			psInsert = connection.prepareStatement("INSERT INTO employees (ID, name) VALUES (?,?)");
			psInsert.setInt(1, key);
			psInsert.setString(2, name);
			psInsert.executeUpdate();

		} catch (SQLException f) {
			f.printStackTrace();
		} finally {
			// if(resultSet != null) {
			// resultSet.close();
			// }
			// if(psCheckEmpExists != null) {
			// psCheckEmpExists.close();
			// }
			if (psInsert != null) {
				psInsert.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		return true;
	}

	public boolean delEmp(ActionEvent e, String name) throws SQLException {
		Connection connection = null;
		PreparedStatement psDel = null;
		PreparedStatement psExists = null;
		ResultSet resultSet = null;
		
		
		
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Booking", "root", "12345678");
			psExists = connection.prepareStatement("SELECT * FROM employees WHERE name = ?");
			psExists.setString(1, name);
			resultSet = psExists.executeQuery();

			if (!resultSet.isBeforeFirst()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Employee Does Not Exist");
				alert.show();
				return false;
			} else {
				psDel = connection.prepareStatement("DELETE FROM employees WHERE name = ?");
				psDel.setString(1, name);
				psDel.executeUpdate();
			}
		} catch (SQLException f) {
			f.printStackTrace();
		} finally {
			if (psDel != null) {
				psDel.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		return true;
	}
	public void selectDate(ActionEvent e) {
		
	}
}
