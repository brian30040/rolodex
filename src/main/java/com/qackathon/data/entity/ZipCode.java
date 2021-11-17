package com.qackathon.data.entity;

import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

/**
 * Created by Brian Hill on 10/25/21
 */
@Entity
@Table(name = "zipcodes")
@Cacheable
public class ZipCode extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "zip", updatable = false, nullable = false, unique = true)
	public String zip;

	@OneToMany(mappedBy = "zipcode", orphanRemoval = true)
	@JsonManagedReference
	private Set<Address> addresses;

	public String city;
	public String state;
	public Double latitude;
	public Double longitude;
	public Integer timezone;
	public Integer dst;

	public ZipCode(String zip, String city, String state, Double latitude, Double longitude, Integer timezone, Integer dst) {
		this.zip = zip;
		this.city = city;
		this.state = state;
		this.latitude = latitude;
		this.longitude = longitude;
		this.timezone = timezone;
		this.dst = dst;
	}

	public ZipCode() {
	}

	@OneToMany
	@JoinColumn(name="zip", referencedColumnName = "zipcode")
	@JsonManagedReference
	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addressSet) {
		this.addresses = addressSet;
	}
}
