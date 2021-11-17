package com.qackathon.data.resource;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.qackathon.data.entity.Contact;
import com.qackathon.data.repository.ContactRepository;

/**
 * Created by Brian Hill on 10/25/21
 */
@Path("contacts")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class ContactResource {
	private static final Logger LOGGER = Logger.getLogger(ContactResource.class.getName());

	@Inject
	JsonWebToken jwt;

	@Inject
	@Claim(standard = Claims.birthdate)
	String birthdate;

	@Inject ContactRepository contactRepository;


	@GET
	@RolesAllowed({ "User", "Admin" })
	public List<Contact> getBySearchCriteria(
		@DefaultValue ("") @QueryParam("firstName") String fname,
		@DefaultValue ("") @QueryParam("lastName") String lname,
		@DefaultValue ("") @QueryParam("phone") String phone) {
		if(!fname.isEmpty())
			return contactRepository.findByFirstName(fname);
		else if(!lname.isEmpty())
			return contactRepository.findByLastName(lname);
		else
			return contactRepository.findByPhoneNumber(phone);
	}

	@POST
	@Transactional
	public Response create(Contact contact) {
//		if (contact.getAddresses() != null) {
//			throw new WebApplicationException("Id was invalidly set on request.", 422);
//		}

		contactRepository.persist(contact);
		return Response.ok(contact).status(201).build();
	}

	@PUT
	@Path("{id}")
	@Transactional
	public Contact update(@PathParam Long id, Contact contact) {

		if (contact.firstName == null) {
			throw new WebApplicationException("Contact First Name was not set on request.", 422);
		}

		if (contact.lastName == null) {
			throw new WebApplicationException("Contact Last Name was not set on request.", 422);
		}

		Contact entity = Contact.findById(id);

		if (entity == null) {
			throw new WebApplicationException("Contact with Id of " + id + " does not exist.", 404);
		}

		entity.firstName = contact.firstName;
		entity.middleName = contact.middleName;
		entity.lastName = contact.lastName;
		entity.dob = contact.dob;
		entity.death = contact.death;

		return entity;
	}

	@DELETE
	@Path("{id}")
	@Transactional
	public Response delete(@PathParam Long id) {
		Contact entity = Contact.findById(id);
		if (entity == null) {
			throw new WebApplicationException("Contact with id of " + id + " does not exist.", 404);
		}
		contactRepository.delete(entity);
		return Response.status(204).build();
	}

	@Provider
	public static class ErrorMapper implements ExceptionMapper<Exception> {

		@Inject
		ObjectMapper objectMapper;

		@Override
		public Response toResponse(Exception exception) {
			LOGGER.error("Failed to handle request", exception);

			int code = 500;
			if (exception instanceof WebApplicationException) {
				code = ((WebApplicationException) exception).getResponse().getStatus();
			}

			ObjectNode exceptionJson = objectMapper.createObjectNode();
			exceptionJson.put("exceptionType", exception.getClass().getName());
			exceptionJson.put("code", code);

			if (exception.getMessage() != null) {
				exceptionJson.put("error", exception.getMessage());
			}

			return Response.status(code)
				.entity(exceptionJson)
				.build();
		}

	}
}
