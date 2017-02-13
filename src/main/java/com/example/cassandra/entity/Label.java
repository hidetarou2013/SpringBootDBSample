package com.example.cassandra.entity;

import java.util.Date;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "MAC000001")
public class Label {

	@PrimaryKeyColumn(name = "kigyou_cd", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
	private String kigyou_cd;

	@PrimaryKeyColumn(name = "label_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
	private int label_id;

	@Column(value = "label_name")
	private String label_name;

	@Column(value = "parent_label_id")
	private int parent_label_id;

	@Column(value = "creat_datetime")
	private Date creat_datetime;

	@Column(value = "creat_user_id")
	private String creat_user_id;

	@Column(value = "update_user_id")
	private String update_user_id;

	@Column(value = "update_datetime")
	private Date update_datetime;

	@Column(value = "ui_assemb_id")
	private String ui_assemb_id;

	@Column(value = "server_assemb_id")
	private String server_assemb_id;

	@Column(value = "delete_id")
	private String delete_id;

	public Label() {

	}

	public Label(String kigyou_cd, int label_id, String label_name, int parent_label_id, Date creat_datetime,
			String creat_user_id, String update_user_id, Date update_datetime, String ui_assemb_id,
			String server_assemb_id, String delete_id) {
		this.kigyou_cd = kigyou_cd;
		this.label_id = label_id;
		this.label_name = label_name;
		this.parent_label_id = parent_label_id;
		this.creat_datetime = creat_datetime;
		this.creat_user_id = creat_user_id;
		this.update_user_id = update_user_id;
		this.update_datetime = update_datetime;
		this.ui_assemb_id = ui_assemb_id;
		this.server_assemb_id = server_assemb_id;
		this.delete_id = delete_id;
	}

	public String getKigyou_cd() {
		return kigyou_cd;
	}

	public void setKigyou_cd(String kigyou_cd) {
		this.kigyou_cd = kigyou_cd;
	}

	public int getLabel_id() {
		return label_id;
	}

	public void setLabel_id(int label_id) {
		this.label_id = label_id;
	}

	public String getLabel_name() {
		return label_name;
	}

	public void setLabel_name(String label_name) {
		this.label_name = label_name;
	}

	public int getParent_label_id() {
		return parent_label_id;
	}

	public void setParent_label_id(int parent_label_id) {
		this.parent_label_id = parent_label_id;
	}

	public Date getCreat_datetime() {
		return creat_datetime;
	}

	public void setCreat_datetime(Date creat_datetime) {
		this.creat_datetime = creat_datetime;
	}

	public String getCreat_user_id() {
		return creat_user_id;
	}

	public void setCreat_user_id(String creat_user_id) {
		this.creat_user_id = creat_user_id;
	}

	public String getUpdate_user_id() {
		return update_user_id;
	}

	public void setUpdate_user_id(String update_user_id) {
		this.update_user_id = update_user_id;
	}

	public Date getUpdate_datetime() {
		return update_datetime;
	}

	public void setUpdate_datetime(Date update_datetime) {
		this.update_datetime = update_datetime;
	}

	public String getUi_assemb_id() {
		return ui_assemb_id;
	}

	public void setUi_assemb_id(String ui_assemb_id) {
		this.ui_assemb_id = ui_assemb_id;
	}

	public String getServer_assemb_id() {
		return server_assemb_id;
	}

	public void setServer_assemb_id(String server_assemb_id) {
		this.server_assemb_id = server_assemb_id;
	}

	public String getDelete_id() {
		return delete_id;
	}

	public void setDelete_id(String delete_id) {
		this.delete_id = delete_id;
	}

	// add
	// @OneToOne
	// @JoinColumn(name="department_id", insertable=false, updatable=false)
	// private Departments dept;
	//
	// public Departments getDept() {
	// return dept;
	// }
	// public void setDept(Departments dept) {
	// this.dept = dept;
	// }

}
