package com.googledrive.api.exception;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.googledrive.api.controller.DriveRepliesController;
import com.googledrive.api.controller.GoogleDriveConnectController;

@RestControllerAdvice(assignableTypes = { GoogleDriveConnectController.class, DriveRepliesController.class })
public class GoogleApiClientExceptionHandler {

	/**
	 * Handle errors returned from a HTTP request to a Google API.
	 *
	 * @param e {GoogleJsonResponseException}
	 * @return {GoogleJsonError}
	 */
	@ExceptionHandler(GoogleJsonResponseException.class)
	public ResponseEntity<GoogleJsonError> handleGoogleApiErrors(GoogleJsonResponseException e) {
		GoogleJsonError googleJsonError = e.getDetails();
		HttpStatus httpStatus = HttpStatus.valueOf(googleJsonError.getCode());
		return new ResponseEntity<>(googleJsonError, httpStatus);
	}

	/**
	 * Handle errors when trying to get the raw response.
	 *
	 * @param e {IOException}
	 * @return {Map<String, String>}
	 */
	@ExceptionHandler(IOException.class)
	public ResponseEntity<Map<String, String>> handleGoogleApiErrors(IOException e) {
		Map<String, String> error = new HashMap<>();
		error.put("message", e.getMessage());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Handle errors when trying to get the raw response.
	 *
	 * @param e {GeneralSecurityException}
	 * @return {Map<String, String>}
	 */
	@ExceptionHandler(GeneralSecurityException.class)
	public ResponseEntity<Map<String, String>> handleGoogleApiErrors(GeneralSecurityException e) {
		Map<String, String> error = new HashMap<>();
		error.put("message", e.getMessage());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
