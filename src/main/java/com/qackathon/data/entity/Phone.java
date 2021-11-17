package com.qackathon.data.entity;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

/**
 * Created by Brian Hill on 11/17/21
 */
@Entity
@Table(name = "phone")
@Cacheable
public class Phone extends PanacheEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contact_id")
	@JsonBackReference
	private Contact contact;

	private String phone;
	private String type;


	@ManyToOne
	@JoinColumn(name="contact_id", referencedColumnName = "id")
	@JsonBackReference
	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
