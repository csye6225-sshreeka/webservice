package com.springboot.springbootapp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Time;
import java.sql.Timestamp;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "users")
public class User {
	@Id
	@GeneratedValue
	private int id;
	private String fname;
	private String lname;
	private String emailId;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	private Timestamp account_created;
	private Timestamp account_updated;
	public void setAccTimestamp(Timestamp date) {
		this.account_created = date;
	}
	public void setAccUpdateTimestamp(Timestamp date) {
		this.account_updated = date;
	}
	public User() {}
	public User(int id, String fname, String lname , String emailId, String password) {
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.emailId = emailId;
		this.password = password;
}}
