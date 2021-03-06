package com.googledrive.api.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.services.drive.model.File;
import com.googledrive.api.service.DriveFilesService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class DriveFilesController {

	@Autowired
	DriveFilesService service;

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

	@PostMapping(value = "/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public void uploadSingleFile(@RequestBody MultipartFile file) {
		service.upload(file);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteFile(@PathVariable String id) throws IOException, GeneralSecurityException {
		service.delete(id);
		return ResponseEntity.ok("File deleted successfully");

	}
}
