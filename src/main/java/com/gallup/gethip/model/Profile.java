package com.gallup.gethip.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@XmlRootElement
@DatabaseTable(tableName = "profiles")
public class Profile {

	@DatabaseField(id = true, columnName = "id")
	private long id;
	@DatabaseField(unique = true, columnName = "profile_name")
	private String profileName;
	@DatabaseField(columnName = "name")
	private String name;
	@DatabaseField(columnName = "phone_number")
	private String phoneNumber;
	@DatabaseField(columnName = "created")
	private Date created;

	public Profile() {

	}

	public Profile(long id, String profileName, String name, String phoneNumber) {
		this.id = id;
		this.profileName = profileName;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.created = new Date();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

}
