package com.example.cassandra.form;

//@Table(value="employees")
public class EmployeesJOIN {

//	@PrimaryKeyColumn(name = "employee_id",ordinal = 1,type = PrimaryKeyType.PARTITIONED)
	private int employee_id;

//	@Column(value = "employee_name")
	private String employee_name;

//	@Column(value = "employee_phone")
	private String employee_phone;

//	@Column(value = "employee_email")
	private String employee_email;

//	@Column(value = "department_id")
	private int department_id;

//	@Column(value = "department_name")
	private String department_name;

	public EmployeesJOIN(){

	}

	public EmployeesJOIN(int employee_id,String employee_name,String employee_phone,String employee_email,int department_id,String department_name){
		this.employee_id = employee_id;
		this.employee_name = employee_name;
		this.employee_phone = employee_phone;
		this.employee_email = employee_email;
		this.department_id = department_id;
		this.department_name = department_name;
	}

	// add
//	@OneToOne
//	@JoinColumn(name="department_id", insertable=false, updatable=false)
//	private Departments dept;
//
//	public Departments getDept() {
//		return dept;
//	}
//	public void setDept(Departments dept) {
//		this.dept = dept;
//	}
	public int getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}
	public String getEmployee_name() {
		return employee_name;
	}
	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}
	public String getEmployee_phone() {
		return employee_phone;
	}
	public void setEmployee_phone(String employee_phone) {
		this.employee_phone = employee_phone;
	}
	public String getEmployee_email() {
		return employee_email;
	}
	public void setEmployee_email(String employee_email) {
		this.employee_email = employee_email;
	}
	public int getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(int department_id) {
		this.department_id = department_id;
	}

	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}

}
