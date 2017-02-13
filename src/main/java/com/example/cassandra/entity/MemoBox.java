package com.example.cassandra.entity;

import java.util.Date;
import java.util.UUID;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import com.datastax.driver.core.utils.UUIDs;

@Table(value = "memobox")
public class MemoBox {

	@PrimaryKeyColumn(name = "id",ordinal = 1,type = PrimaryKeyType.PARTITIONED)
	private UUID id = UUIDs.timeBased();

	@Column(value = "name")
	private String name;

	@Column(value = "memo")
	private String memo;

	@Column(value = "date")
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
