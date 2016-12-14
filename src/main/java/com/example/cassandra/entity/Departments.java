package com.example.cassandra.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;


@Table(value="departments")
public class Departments {

	@PrimaryKeyColumn(name = "department_id",ordinal = 1,type = PrimaryKeyType.PARTITIONED)
	private int department_id;

	@Column(value = "department_name")
	private String department_name;

	public Departments(int department_id,String department_name){
		this.department_id = department_id;
		this.department_name = department_name;
	}

	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="department_id",insertable=false,updatable=false)
	private List<Employees> emp = new ArrayList<Employees>();

	public List<Employees> getEmp() {
		return emp;
	}

	public void setEmp(List<Employees> emp) {
		this.emp = emp;
	}

	/**
	 * department_idを取得します。
	 * @return department_id
	 */
	public int getDepartment_id() {
		return department_id;
	}

	/**
	 * department_idを設定します。
	 * @param department_id department_id
	 */
	public void setDepartment_id(int department_id) {
		this.department_id = department_id;
	}

	/**
	 * department_nameを取得します。
	 * @return department_name
	 */
	public String getDepartment_name() {
		return department_name;
	}

	/**
	 * department_nameを設定します。
	 * @param department_name department_name
	 */
	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}


}
