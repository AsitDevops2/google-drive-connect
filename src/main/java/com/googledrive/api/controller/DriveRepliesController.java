/**
 * 
 */
package com.googledrive.api.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.drive.model.Reply;
import com.googledrive.api.service.DriveRepliesService;

@RestController
public class DriveRepliesController {
	
	@Autowired
	DriveRepliesService service;

	
	@GetMapping(value = { "/listreplies/{fileId}/{commentId}" })
	public ResponseEntity<List<Reply>> listComments(@PathVariable String fileId,@PathVariable String commentId)
			throws IOException, GeneralSecurityException {
		return ResponseEntity.ok(service.replies(fileId,commentId));

	}

	@GetMapping(value = { "/comments/{fileId}/{commentId}" })
	public ResponseEntity<Reply> commentById(@PathVariable String fileId, @PathVariable String commentId,@PathVariable String replyId)
			throws IOException, GeneralSecurityException {
		return ResponseEntity.ok(service.reply(fileId, commentId,replyId));

	}

	@PostMapping(value = { "/createComment/{fileId}/{commentId}" })
	public ResponseEntity<Reply> createComment(@PathVariable String fileId, @PathVariable String commentId)
			throws IOException, GeneralSecurityException {
		return ResponseEntity.ok(service.createReply(fileId, commentId));

	}
	
	@DeleteMapping(value = { "/delete/{fileId}/{commentId}" })
	public ResponseEntity<String> createComment(@PathVariable String fileId, @PathVariable String commentId,@PathVariable String replyId)
			throws IOException, GeneralSecurityException {
		
		service.deleteReply(fileId, commentId,replyId);
		return ResponseEntity.ok("Reply deleted successfully");
	}

}
