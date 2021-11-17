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
 * Created by Brian Hill on 10/25/21
 */
@Entity
@Table(name = "address")
@Cacheable
public class Address extends PanacheEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contact_id")
	@JsonBackReference
	private Contact contact;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "zipcode")
	@JsonBackReference
	private ZipCode zipcode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id")
	@JsonBackReference
	private Country country;

	public String address_1;
	public String address_2;
	public String city;
	public String state;

	public Address() {
	}

	public Address(String address1, String address2, String city, String state) {
		this.address_1 = address1;
		this.address_2 = address2;
		this.city = city;
		this.state = state;
	}

	@ManyToOne
	@JoinColumn(name="contact_id", referencedColumnName = "id")
	@JsonBackReference
	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	@ManyToOne
	@JoinColumn(name="country_id", referencedColumnName = "id")
	@JsonBackReference
	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@ManyToOne
	@JoinColumn(name="zipcode", referencedColumnName = "zip")
	@JsonBackReference
	public ZipCode getZipCode() {
		return zipcode;
	}

	public void setZipCode(ZipCode zipcode) {
		this.zipcode = zipcode;
	}
}
