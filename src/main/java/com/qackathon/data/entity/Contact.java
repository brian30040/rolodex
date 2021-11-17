package com.qackathon.data.entity;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

/**
 * Created by Brian Hill on 10/25/21
 */
@Entity
@Table(name = "contact")
@Cacheable
public class Contact extends PanacheEntity {

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contact", orphanRemoval = true)
	@JsonManagedReference
	private Set<Address> addresses;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contact", orphanRemoval = true)
	@JsonManagedReference
	private Set<Phone> phones;

	@Column(name = "first_name")
	public String firstName;

	@Column(name = "middle_name")
	public String middleName;

	@Column(name = "last_name")
	public String lastName;

	public Date dob;
	public Date death;

	public Contact() {
	}

	public Contact(String firstName, String middleName, String lastName, Date dob, Date death) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.dob = dob;
		this.death = death;
	}

	@OneToMany
	@JoinColumn(name="id", referencedColumnName = "contact_id")
	@JsonManagedReference
	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	@OneToMany
	@JoinColumn(name="id", referencedColumnName = "contact_id")
	@JsonManagedReference
	public Set<Phone> getPhones() {
		return phones;
	}

	public void setPhones(Set<Phone> phones) {
		this.phones = phones;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Date getDeath() {
		return death;
	}

	public void setDeath(Date death) {
		this.death = death;
	}
}
