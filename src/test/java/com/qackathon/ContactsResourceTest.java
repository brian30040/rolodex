package com.qackathon;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

import org.eclipse.microprofile.jwt.Claims;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.jwt.build.Jwt;

@QuarkusTest
public class ContactsResourceTest {

	@Test
	public void testSearchByLastName() {
		given()
			.auth()
			.oauth2(generateValidUserToken())
			.queryParam("lastName","Doe")
			.when()
			.get("/contacts")
			.then()
			.statusCode(200)
			.body(containsString("John"));
	}

	@Test
	public void testSearchByFirstName() {
		given()
			.auth()
			.oauth2(generateValidUserToken())
			.queryParam("firstName","Fred")
			.when()
			.get("/contacts")
			.then()
			.statusCode(200)
			.body(containsString("Krueger"));
	}

	@Test
	public void testSearchByPhone() {
		given()
			.auth()
			.oauth2(generateValidUserToken())
			.queryParam("phone","165198")
			.when()
			.get("/contacts")
			.then()
			.statusCode(200)
			.body(containsString("Bastard"));
	}

	static String generateValidUserToken() {
		return Jwt.upn("brian.w.hill@onedatascan.com")
			.issuer("https://example.com/issuer")
			.groups("User")
			.claim(Claims.birthdate.name(), "1972-08-19")
			.sign();
	}
}