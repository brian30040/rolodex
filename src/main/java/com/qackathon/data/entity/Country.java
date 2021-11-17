package com.qackathon.data.entity;

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
@Table(name = "countries")
@Cacheable
public class Country extends PanacheEntity {

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "country", orphanRemoval = true)
	@JsonManagedReference
	private Set<Address> addresses;

	@Column(name = "phone_code")
	public String phoneCode;

	@Column(name = "country_code")
	public String countryCode;

	@Column(name = "country_name")
	public String countryName;

	public Country(String phoneCode, String countryCode, String countryName) {
		this.phoneCode = phoneCode;
		this.countryCode = countryCode;
		this.countryName = countryName;
	}

	public Country() {
	}

	@OneToMany
	@JoinColumn(name="id", referencedColumnName = "country_id")
	@JsonManagedReference
	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addressSet) {
		this.addresses = addressSet;
	}
}
