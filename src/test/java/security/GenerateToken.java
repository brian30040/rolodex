package com.qackathon.security;

/**
 * Created by Brian Hill on 11/17/21
 */

import java.util.Arrays;
import java.util.HashSet;

import org.eclipse.microprofile.jwt.Claims;

import io.smallrye.jwt.build.Jwt;

/**
 * A simple utility class to generate and print a JWT token string to stdout.
 */
public class GenerateToken {
	/**
	 * Generate JWT token
	 */
	public static void main(String[] args) {
		String token = Jwt.issuer("https://example.com/issuer")
			.upn("brian.w.hill@onedatascan.com")
			.groups(new HashSet<>(Arrays.asList("User", "Admin")))
			.claim(Claims.birthdate.name(), "1972-08-19")
			.sign();
		System.out.println(token);
	}
}