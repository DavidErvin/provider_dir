package us.ervin.providerdir.providerdirectory.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // make this return an HTTP 404
public class ProviderNotFoundException extends RuntimeException {

	public ProviderNotFoundException(String message) {
		super(message);
	}
}
