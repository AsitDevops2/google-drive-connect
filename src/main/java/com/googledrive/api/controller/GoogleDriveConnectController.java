package com.googledrive.api.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.googledrive.api.service.GoogleDriveConnectService;

/**
 * Google driver controller class
 *
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class GoogleDriveConnectController {

	@Autowired
	GoogleDriveConnectService service;

	@GetMapping(value = { "/googlesignin" })
	public void doGoogleSignIn(HttpServletResponse response) throws IOException {

		String signinURL = service.doGoogleSignIn();
		response.sendRedirect(signinURL);

	}

	@GetMapping(value = { "/oauth" })
	public ResponseEntity<String> saveAuthorizationCode(HttpServletRequest request) {
		String code = request.getParameter("code");
		service.saveAuthorizationCode(code);
		return ResponseEntity.ok("Authenticated Successfully.Please close the window and proceed with source code download.");

	}

	@GetMapping("/downloadSource")
	public ResponseEntity<String> downloadSource() {
		service.downloadSource();
		return ResponseEntity.ok("Source Code Downloaded Successfully....Please check your drive");
	}

}
