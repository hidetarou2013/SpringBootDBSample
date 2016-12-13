package com.example.springboot;

import java.util.Date;

import javax.persistence.Id;

public class MemoBox {

	@Id
	private String id;

	private String name;

	private String memo;

	private Date date;

	public MemoBox(String name, String memo) {
		this.name = name;
		this.memo = memo;
		this.date = new Date();
	}

//	public MyDataMongo(String id,String name, String memo) {
//		this.id = id;
//		this.name = name;
//		this.memo = memo;
//		this.date = new Date();
//	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getMemo() {
		return memo;
	}

	public Date getDate() {
		return date;
	}

}
