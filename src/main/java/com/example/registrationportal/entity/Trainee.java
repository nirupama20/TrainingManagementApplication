package com.example.registrationportal.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Trainee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int tid;
	private String tname;
	private String temail;
	private String tphoneno;
	private String tpassword;
	private String tconformpassword;
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "trainee")
	private List<Task> tasks;
	public Trainee(int tid, String tname, String temail,String tphoneno, String tpassword, String tconformpassword, List<Task> tasks) {
		super();
		this.tid = tid;
		this.tname = tname;
		this.temail = temail;
		this.tpassword = tpassword;
		this.tconformpassword=tconformpassword;
		this.tasks = tasks;
		this.tphoneno=tphoneno;
	}
	public String getTconformpassword() {
		return tconformpassword;
	}
	public void setTconformpassword(String tconformpassword) {
		this.tconformpassword = tconformpassword;
	}
	public String getTphoneno() {
		return tphoneno;
	}
	public void setTphoneno(String tphoneno) {
		this.tphoneno = tphoneno;
	}
	public Trainee() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public String getTemail() {
		return temail;
	}
	public void setTemail(String temail) {
		this.temail = temail;
	}
	public String getTpassword() {
		return tpassword;
	}
	public void setTpassword(String tpassword) {
		this.tpassword = tpassword;
	}
	public List<Task> getTasks() {
		return tasks;
	}
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
	
	
}
