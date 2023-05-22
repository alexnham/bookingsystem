package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Utility {

	private Parent root;
	private Stage stage;

	public ObservableList<Employee> loadEmployees() throws SQLException, FileNotFoundException {

		ObservableList<Employee> list = FXCollections.observableArrayList();
		Connection connection = null;
		PreparedStatement psReturnEmp = null;
		ResultSet resultSet = null;
		File f = new File("src/sqlLogin.txt");
		Scanner s = new Scanner(f);
		String[] connectInfo = { s.next(), s.next(), s.next() };

		try {
			connection = DriverManager.getConnection(connectInfo[0], connectInfo[1], connectInfo[2]);
			psReturnEmp = connection.prepareStatement("SELECT * FROM employees");
			resultSet = psReturnEmp.executeQuery();

			while (resultSet.next())
				list.add(new Employee(resultSet.getString("name"), resultSet.getInt("ID")));
		} catch (Exception e) {

		} finally {
			s.close();
			if (resultSet != null) {
				resultSet.close();
			}
			if (psReturnEmp != null) {
				psReturnEmp.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
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

	public boolean newService(ActionEvent e, String name, String time) throws FileNotFoundException, SQLException {
		Connection connection = null;
		PreparedStatement psInsert = null;
		File file = new File("src/sqlLogin.txt");
		Scanner scan = new Scanner(file);
		String[] connectInfo = { scan.next(), scan.next(), scan.next() };

		try {
			//// connection
			connection = DriverManager.getConnection(connectInfo[0], connectInfo[1], connectInfo[2]);
			//// creating the new service
			psInsert = connection.prepareStatement("INSERT INTO services (name, duration) VALUES (?,?)");
			psInsert.setString(1, name);
			if (time.equals("00:15:00")) {
				psInsert.setInt(2, 1);

			} else if (time.equals("00:30:00")) {
				psInsert.setInt(2, 2);

			} else if (time.equals("00:45:00")) {
				psInsert.setInt(2, 3);

			} else {
				psInsert.setInt(2, 4);
			}
			psInsert.executeUpdate();

		} catch (SQLException f) {
			f.printStackTrace();
		} finally {
			scan.close();
			if (psInsert != null) {
				psInsert.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		return true;
	}

	public boolean delService(ActionEvent e, String name) throws SQLException, FileNotFoundException {
		Connection connection = null;
		PreparedStatement psDel = null;
		PreparedStatement psExists = null;
		ResultSet resultSet = null;
		File file = new File("src/sqlLogin.txt");
		Scanner scan = new Scanner(file);
		String[] connectInfo = { scan.next(), scan.next(), scan.next() };

		try {
			connection = DriverManager.getConnection(connectInfo[0], connectInfo[1], connectInfo[2]);
			psExists = connection.prepareStatement("SELECT * FROM services WHERE name = ?");
			psExists.setString(1, name);
			resultSet = psExists.executeQuery();

			if (!resultSet.isBeforeFirst()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Service Does Not Exist");
				alert.show();
				return false;
			} else {
				psDel = connection.prepareStatement("DELETE FROM services WHERE name = ?");
				psDel.setString(1, name);
				psDel.executeUpdate();
			}
		} catch (SQLException f) {
			f.printStackTrace();
		} finally {
			scan.close();
			if (psDel != null) {
				psDel.close();
			}
			if (psExists != null) {
				psExists.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		return true;
	}

	public boolean newEmp(ActionEvent e, String name) throws SQLException, FileNotFoundException {
		Connection connection = null;
		PreparedStatement psInsert = null;
		PreparedStatement psCheck = null;
		PreparedStatement topKey = null;
		ResultSet resultSet = null;
		ResultSet getTopKey = null;
		int key;
		File file = new File("src/sqlLogin.txt");
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(file);
		String[] connectInfo = { scan.next(), scan.next(), scan.next() };

		try {
			//// connection
			connection = DriverManager.getConnection(connectInfo[0], connectInfo[1], connectInfo[2]);
			/// checking if name is taken
			psCheck = connection.prepareStatement("SELECT * FROM employees WHERE name = ?");
			psCheck.setString(1, name);
			resultSet = psCheck.executeQuery();
			if (resultSet.isBeforeFirst()) {
				return false;
			} else {
				//// grabbing the highest value key to create new primary key
				topKey = connection.prepareStatement("SELECT MAX(ID) FROM employees");
				getTopKey = topKey.executeQuery();
				getTopKey.next();
				key = getTopKey.getInt("MAX(ID)") + 1;
				//// creating the new employee
				psInsert = connection.prepareStatement("INSERT INTO employees (ID, name) VALUES (?,?)");
				psInsert.setInt(1, key);
				psInsert.setString(2, name);
				psInsert.executeUpdate();
			}
		} catch (SQLException f) {
			f.printStackTrace();
		} finally {
			if (psInsert != null) {
				psInsert.close();
			}
			if (psCheck != null) {
				psCheck = null;
			}
			if (topKey != null) {
				topKey.close();
			}
			if (getTopKey != null) {
				getTopKey.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		return true;
	}

	public boolean delEmp(ActionEvent e, Employee employee) throws SQLException, FileNotFoundException {
		Connection connection = null;
		PreparedStatement psDel = null;
		PreparedStatement psExists = null;
		PreparedStatement psApp = null;
		ResultSet resultSet = null;
		ResultSet removeApp = null;
		File file = new File("src/sqlLogin.txt");
		Scanner scan = new Scanner(file);
		String[] connectInfo = { scan.next(), scan.next(), scan.next() };

		try {
			connection = DriverManager.getConnection(connectInfo[0], connectInfo[1], connectInfo[2]);
			psExists = connection.prepareStatement("SELECT * FROM employees WHERE name = ?");
			psExists.setString(1, employee.getEmpName());
			resultSet = psExists.executeQuery();
			if (!resultSet.isBeforeFirst()) {
				return false;
			} else {
				psDel = connection.prepareStatement("DELETE FROM employees WHERE name = ?");
				psDel.setString(1, employee.getEmpName());
				psDel.executeUpdate();
				psApp = connection.prepareStatement("DELETE FROM appointments WHERE name = ?");
				psExists.setInt(1, employee.getEmpID());
				removeApp = psExists.executeQuery();
			}

		} catch (Exception f) {
			f.printStackTrace();
		} finally {
			scan.close();
			if (psDel != null) {
				psDel.close();
			}
			if (psExists != null) {
				psExists.close();
			}
			if (psApp != null) {
				psApp.close();
			}
			if (removeApp != null) {
				removeApp.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		return true;
	}

	public void selectDate(ActionEvent e) {

	}

	public void fillServices(ComboBox<Service> services) throws SQLException, FileNotFoundException {
		ObservableList<Service> list = FXCollections.observableArrayList();
		Connection connection = null;
		PreparedStatement psReturnEmp = null;
		ResultSet resultSet = null;
		File f = new File("src/sqlLogin.txt");
		Scanner s = new Scanner(f);
		String[] connectInfo = { s.next(), s.next(), s.next() };
		try {
			connection = DriverManager.getConnection(connectInfo[0], connectInfo[1], connectInfo[2]);
			psReturnEmp = connection.prepareStatement("SELECT * FROM services");
			resultSet = psReturnEmp.executeQuery();

			while (resultSet.next())
				list.add(new Service(resultSet.getString("name"), resultSet.getInt("duration")));
			System.out.println(list.get(0));
			services.getItems().addAll(list);
//			
		} catch (Exception e) {
		} finally {
			s.close();
			if (resultSet != null) {
				resultSet.close();
			}
			if (psReturnEmp != null) {
				psReturnEmp.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

	public void fillEmp(ComboBox<Employee> emp) throws FileNotFoundException, SQLException {
		emp.getItems().clear();
		for (int i = 0; i < getEmployees().size(); i++) {
			emp.getItems().add(getEmployees().get(i));
		}
	}

	public void fillStartTime(ComboBox<String> startTime, Service service, int id, LocalDate date)
			throws SQLException, FileNotFoundException {
		startTime.getItems().clear();
		HashMap<Integer, String> timeList = getHash();
		for (int i = 0; i < timeList.size(); i++) {
			startTime.getItems().add(timeList.get(i));
		}
		startTime.getItems().remove(43);
		startTime.getItems().remove(42);
		startTime.getItems().remove(41);
		Connection connection = null;
		PreparedStatement psReturnTime = null;
		ResultSet resultSet = null;
		File f = new File("src/sqlLogin.txt");
		Scanner s = new Scanner(f);
		String[] connectInfo = { s.next(), s.next(), s.next() };
		try {
			connection = DriverManager.getConnection(connectInfo[0], connectInfo[1], connectInfo[2]);
			psReturnTime = connection
					.prepareStatement("SELECT startTime, endTime from appointments WHERE employee = ? AND appDay = ?");
			psReturnTime.setString(1, String.valueOf(id));
			psReturnTime.setDate(2, java.sql.Date.valueOf(date));
			resultSet = psReturnTime.executeQuery();

			int prevStartTime = 0;
			while (resultSet.next()) {
				System.out.println(
						"Start: " + resultSet.getInt("startTime") + "\n" + "End: " + resultSet.getInt("endTime"));
				for (int i = resultSet.getInt("startTime"); i < resultSet.getInt("endTime"); i++) {
					System.out.println(getHash().get(i) + "-");
					startTime.getItems().remove(getHash().get(i));
				}
				for (int i = resultSet.getInt("endTime") - 1; i >= resultSet.getInt("endTime")
						- service.getTimeKey(); i--) {
					System.out.println(getHash().get(i) + "--");
					startTime.getItems().remove(getHash().get(i));

				}
				if (resultSet.getInt("startTime") - prevStartTime < service.getTimeKey()) {
					for (int i = prevStartTime; i < resultSet.getInt("startTime"); i++) {
						System.out.println(getHash().get(i) + "---");
						startTime.getItems().remove(getHash().get(i));
					}

				}
				prevStartTime = resultSet.getInt("startTime");
			}
		} catch (Exception e) {
		} finally {
			s.close();
			if (resultSet != null) {
				resultSet.close();
			}
			if (psReturnTime != null) {
				psReturnTime.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

	public HashMap<Integer, String> getHash() {
		int time = 9;
		HashMap<Integer, String> timeList = new HashMap<Integer, String>();
		int count = 0;
		while (time != 8) {
			if (time < 10) {
				timeList.put(count, "0" + time + ":00:00");
				count++;
				timeList.put(count, "0" + time + ":15:00");
				count++;
				timeList.put(count, "0" + time + ":30:00");
				count++;
				timeList.put(count, "0" + time + ":45:00");
				count++;
			} else {
				timeList.put(count, time + ":00:00");
				count++;
				timeList.put(count, time + ":15:00");
				count++;
				timeList.put(count, time + ":30:00");
				count++;
				timeList.put(count, time + ":45:00");
				count++;
			}
			time = time + 1;
			if (time > 12) {
				time = 1;
			}
		}
		return timeList;
	}

	public void setEnd(Text endTime, String start, Service service) {

		int endValue = returnKey(start) + service.getTimeKey();
		endTime.setText(getHash().get(endValue));
	}

	public int returnKey(String selectedValue) {
		HashMap<Integer, String> a = getHash();
		int endValue = 0;
		for (Entry<Integer, String> entry : a.entrySet()) {
			if (entry.getValue().equals(selectedValue)) {
				endValue = entry.getKey();
			}
		}
		return endValue;
	}

	public ArrayList<Employee> getEmployees() throws SQLException, FileNotFoundException {
		ArrayList<Employee> employees = new ArrayList<Employee>();
		Connection connection = null;
		PreparedStatement psReturnEmp = null;
		ResultSet resultSet = null;
		File f = new File("src/sqlLogin.txt");
		@SuppressWarnings("resource")
		Scanner s = new Scanner(f);
		String[] connectInfo = { s.next(), s.next(), s.next() };
		try {
			connection = DriverManager.getConnection(connectInfo[0], connectInfo[1], connectInfo[2]);
			psReturnEmp = connection.prepareStatement("SELECT * FROM employees");
			resultSet = psReturnEmp.executeQuery();

			while (resultSet.next()) {
				employees.add(new Employee(resultSet.getString("name"), resultSet.getInt("ID")));
			}
		} catch (Exception g) {

		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
			if (psReturnEmp != null) {
				psReturnEmp.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		return employees;

	}

	public boolean bookAppointment(String customerName, Service service, Employee employee, int startKey, int endKey,
			String phoneNum, LocalDate date) throws SQLException, FileNotFoundException {
		Connection connection = null;
		PreparedStatement psInsert = null;
		PreparedStatement topKey = null;
		ResultSet getTopKey = null;
		int key;
		File file = new File("src/sqlLogin.txt");
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(file);
		String[] connectInfo = { scan.next(), scan.next(), scan.next() };

		try {
			//// connection
			connection = DriverManager.getConnection(connectInfo[0], connectInfo[1], connectInfo[2]);
			//// grabbing the highest value key to create new primary key
			topKey = connection.prepareStatement("SELECT MAX(appID) FROM appointments");
			getTopKey = topKey.executeQuery();
			getTopKey.next();
			key = getTopKey.getInt("MAX(appID)") + 1;
			//// creating the new employee
			psInsert = connection.prepareStatement(
					"INSERT INTO appointments (appID, employee, customer, appDay, startTime, endTime, phoneNum) VALUES (?,?,?,?,?,?,?)");
			psInsert.setInt(1, key);
			psInsert.setInt(2, employee.getEmpID());
			psInsert.setString(3, customerName);
			psInsert.setDate(4, java.sql.Date.valueOf(date));
			psInsert.setInt(5, startKey);
			psInsert.setInt(6, endKey);
			psInsert.setString(7, phoneNum);
			psInsert.executeUpdate();

		} catch (Exception f) {
			return false;
		} finally {
			if (psInsert != null) {
				psInsert.close();
			}
			if (topKey != null) {
				topKey.close();
			}
			if (getTopKey != null) {
				getTopKey.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		return true;

	}

	@SuppressWarnings("unlikely-arg-type")
	public void assignTable(GridPane timeTable, Employee employee, LocalDate date)
			throws FileNotFoundException, SQLException {
		Pane paneCheck = new Pane();
		ArrayList<Pane> remove = new ArrayList<Pane>();
		ObservableList<Node> a = timeTable.getChildren();
		for (int i = 0; i < a.size(); i++) {
			System.out.println(a.size());
			System.out.println(a.get(i) + "--");
			if (a.get(i).getClass() == paneCheck.getClass()) {
				System.out.println(a.get(i));
				paneCheck = (Pane) a.get(i);
				remove.add(paneCheck);
			}
		}
		for (int i = 0; i < remove.size(); i++) {
			timeTable.getChildren().remove(remove.get(i));

		}

		Connection connection = null;
		PreparedStatement psReturnTime = null;
		ResultSet resultSet = null;
		File f = new File("src/sqlLogin.txt");
		Scanner s = new Scanner(f);
		String[] connectInfo = { s.next(), s.next(), s.next() };
		String[] colorCodes = { "#ACDDDE", "#CAF1DE", "#FFE7C7", "#ff726f" };
		int colorCodeValue = 0;
		int row = 1;
		int column = 1;
		int startTime;
		int endTime;
		int appID;
		String customer;

		try {
			connection = DriverManager.getConnection(connectInfo[0], connectInfo[1], connectInfo[2]);
			psReturnTime = connection.prepareStatement("SELECT * from appointments WHERE employee = ? AND appDay = ?");
			psReturnTime.setString(1, String.valueOf(employee.getEmpID()));
			psReturnTime.setDate(2, java.sql.Date.valueOf(date));
			resultSet = psReturnTime.executeQuery();

			while (resultSet.next()) {
				appID = resultSet.getInt("appID");
				startTime = resultSet.getInt("startTime");
				endTime = resultSet.getInt("endTime");
				customer = resultSet.getString("customer");
				for (int i = startTime; i < endTime; i++) {
					row += Math.floorDiv(i, 4);
					column += i % 4;

					Pane p = new Pane();
					p.setStyle("-fx-background-color: " + colorCodes[colorCodeValue]);
					if (i == startTime) {
						Text info = new Text("ID: " + String.valueOf(appID) + " Name: `" + customer);
						info.relocate(0, 0);
						info.setStyle("-fx-font: 8 arial;");
						Text time = new Text(1, 1, getHash().get(startTime) + "-" + getHash().get(endTime));
						time.relocate(1, 10);
						time.setStyle("-fx-font: 10 arial;");

						p.getChildren().addAll(info, time);
					}
					timeTable.add(p, column, row);
					System.out.println("this prints");

					row = 1;
					column = 1;
				}
				colorCodeValue++;
				if (colorCodeValue == 4) {
					colorCodeValue = 0;
				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			s.close();
			if (resultSet != null) {
				resultSet.close();
			}
			if (psReturnTime != null) {
				psReturnTime.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

	public boolean cancelApp(int id) throws SQLException, FileNotFoundException {
		Connection connection = null;
		PreparedStatement psDel = null;
		PreparedStatement psExists = null;
		ResultSet resultSet = null;
		File file = new File("src/sqlLogin.txt");
		Scanner scan = new Scanner(file);
		String[] connectInfo = { scan.next(), scan.next(), scan.next() };

		try {
			connection = DriverManager.getConnection(connectInfo[0], connectInfo[1], connectInfo[2]);
			psExists = connection.prepareStatement("SELECT * FROM appointments WHERE appID = ?");
			psExists.setInt(1, id);
			resultSet = psExists.executeQuery();

			if (!resultSet.isBeforeFirst()) {
				return false;
			} else {
				psDel = connection.prepareStatement("DELETE FROM appointments WHERE appID = ?");
				psDel.setInt(1, id);
				psDel.executeUpdate();
			}
		} catch (SQLException f) {
			f.printStackTrace();
		} finally {
			scan.close();
			if (psDel != null) {
				psDel.close();
			}
			if (psExists != null) {
				psExists.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		return true;
	}
}
