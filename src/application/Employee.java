package application;

public class Employee {
	private int empID;
	private String empName;
	public Employee(String name, int id) {
		empID = id;
		empName = name;
	}
	public void setName(String name) {
		empName = name;
	}
	public int getEmpID() {
		return empID;
	}
	public String getEmpName() {
		return empName;
	}
	public void setId(int id) {
		empID = id;
	}
}
