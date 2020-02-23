package com.qfedu.estore.entity;

public class User {
	private Integer id;
	private String nickname;
	private String telephone;
	private String password;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String toString() {
		return "User [id=" + id + ", nickname=" + nickname + ", telephone=" + telephone + ", password=" + password
				+ "]";
	}
}
