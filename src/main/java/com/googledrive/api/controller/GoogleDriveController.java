package com.googledrive.api.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.services.drive.model.File;
import com.googledrive.api.service.GoogleDriveService;


/**
 * @author swathi.kompella
 *
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class GoogleDriveController {

	@Autowired
	GoogleDriveService service;

	@GetMapping(value = { "/googlesignin" })
	public void doGoogleSignIn(HttpServletResponse response) throws IOException {

		String signinURL = service.doGoogleSignIn();
		response.sendRedirect(signinURL);

	}

	@GetMapping(value = { "/oauth" })
	public void saveAuthorizationCode(HttpServletRequest request) {
		String code = request.getParameter("code");
		service.saveAuthorizationCode(code);

	}

	@GetMapping("/Files")
	public ResponseEntity<List<File>> files() throws IOException, GeneralSecurityException {
		List<File> files = service.files();
		return ResponseEntity.ok(files);
	}

	@GetMapping("/File/{id}")
	public File retireveFileMetadata(@PathVariable String id) throws IOException, GeneralSecurityException {
		return service.getFile(id);
	}

	@GetMapping("/download/{id}")
	public void download(@PathVariable String id, HttpServletResponse response)
			throws IOException, GeneralSecurityException {
		service.downloadFile(id, response.getOutputStream());
	}
	

	@GetMapping("/downloadSource")
	public ResponseEntity<String> downloadSource() {
		service.downloadSource();
		return ResponseEntity.ok("Source Code Downloaded Successfully....Please check your drive");
	}

	@PostMapping(value = "/upload",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE} )
    public void uploadSingleFile(@RequestBody MultipartFile file) {
        service.upload(file);
  }
}
