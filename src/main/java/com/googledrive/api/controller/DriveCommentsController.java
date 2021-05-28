package com.googledrive.api.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.drive.model.Comment;
import com.googledrive.api.service.DriveCommentsService;

/**
 * Google driver controller class
 *
 */

@RestController
public class DriveCommentsController {

	@Autowired
	DriveCommentsService service;

	@GetMapping(value = { "/listcomments/{fileId}" })
	public ResponseEntity<List<Comment>> listComments(@PathVariable String fileId)
			throws IOException, GeneralSecurityException {
		return ResponseEntity.ok(service.comments(fileId));

	}

	@GetMapping(value = { "/comments/{fileId}/{commentId}" })
	public ResponseEntity<Comment> commentById(@PathVariable String fileId, @PathVariable String commentId)
			throws IOException, GeneralSecurityException {
		return ResponseEntity.ok(service.commentById(fileId, commentId));

	}

	@PostMapping(value = { "/createComment/{fileId}/{commentId}" })
	public ResponseEntity<Comment> createComment(@PathVariable String fileId, @PathVariable String commentId)
			throws IOException, GeneralSecurityException {
		return ResponseEntity.ok(service.createComment(fileId));

	}

}
