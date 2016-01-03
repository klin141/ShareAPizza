package com.gallup.gethip.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@XmlRootElement
@DatabaseTable(tableName = "shareables")
public class Shareable {

	@DatabaseField(id = true, columnName = "id")
	private long id;
	@DatabaseField(columnName = "created_by")
	private String createdBy;
	@DatabaseField(columnName = "shareable")
	private String shareable;
	@DatabaseField(columnName = "from_restaurant")
	private String fromRestaurant;
	@DatabaseField(columnName = "price")
	private double price;
	@DatabaseField(columnName = "created")
	private Date created;

	public Shareable() {

	}

	public Shareable(long id, String createdBy, String shareable, String fromRestaurant, double price) {
		this.id = id;
		this.createdBy = createdBy;
		this.shareable = shareable;
		this.fromRestaurant = fromRestaurant;
		this.price = price;
		this.created = new Date();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getShareable() {
		return shareable;
	}

	public void setShareable(String shareable) {
		this.shareable = shareable;
	}

	public String getFromRestaurant() {
		return fromRestaurant;
	}

	public void setFromRestaurant(String fromRestaurant) {
		this.fromRestaurant = fromRestaurant;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

}
