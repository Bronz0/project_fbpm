package com.sma.entitee;

public class User {
	
	private int id;
	private String username;
	private String password;
	private String communaute;
	
	public User() {}
	
	public User(String username, String password, String communaute) {
		this.username = username;
		this.password = password;
		this.communaute = communaute;
	}
	
	public User(int id,String username, String password, String communaute) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.communaute = communaute;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCommunaute() {
		return communaute;
	}

	public void setCommunaute(String communaute) {
		this.communaute = communaute;
	}

	@Override
	public String toString() {
		return "User [id=" + id + " username=" + username + ", password=" + password + ", communaute=" + communaute
				+ "]";
	}
	
	

}
