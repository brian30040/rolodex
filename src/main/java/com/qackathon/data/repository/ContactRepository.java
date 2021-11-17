package com.qackathon.data.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.qackathon.data.entity.Contact;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

/**
 * Created by Brian Hill on 10/31/21
 */
@ApplicationScoped
public class ContactRepository implements PanacheRepository<Contact> {
	private static final String FIRST_NAME = "firstName";
	private static final String LAST_NAME = "lastName";
	private static final String PHONE = "phone";
	private static final String CONTACT_REF = "c";
	private static final String PHONE_REF = "p";
	private static final String BASE_QUERY =
		"from Contact c "+
			"inner join fetch c.addresses a "+
			"inner join fetch c.phones p "+
			"inner join fetch a.country "+
			"inner join fetch a.zipcode "+
			"where %s.%s like '%s%%' ";

	public List<Contact> findByLastName(String name) {
		return find(String.format(BASE_QUERY, CONTACT_REF, LAST_NAME, name)).list();
	}

	public List<Contact> findByFirstName(String name) {
		return find(String.format(BASE_QUERY, CONTACT_REF, FIRST_NAME, name)).list();
	}

	public List<Contact> findByPhoneNumber(String phone) {
		return find(String.format(BASE_QUERY, PHONE_REF, PHONE, phone)).list();
	}
}
