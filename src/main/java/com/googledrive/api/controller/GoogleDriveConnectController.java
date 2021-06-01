package com.googledrive.api.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.googledrive.api.service.GoogleDriveConnectService;

/**
 * Google driver controller class
 *
 */

@Controller
public class GoogleDriveConnectController {

	@Autowired
	GoogleDriveConnectService service;

	@GetMapping(value = { "/googlesignin" })
	public void doGoogleSignIn(HttpServletResponse response) throws IOException {

		String signinURL = service.doGoogleSignIn();
		response.sendRedirect(signinURL);

	}

	@GetMapping(value = { "/oauth" })
	public String saveAuthorizationCode(HttpServletRequest request) {
		String code = request.getParameter("code");
		service.saveAuthorizationCode(code);
		return "Authenticate.html";

	}

}
