/**
 * 
 */
package com.googledrive.api.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.drive.model.Reply;
import com.googledrive.api.service.DriveRepliesService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class DriveRepliesController {
	
	@Autowired
	DriveRepliesService service;

	
	@GetMapping(value = { "/listreplies/{fileId}/{commentId}" })
	public ResponseEntity<List<Reply>> replies(@PathVariable String fileId,@PathVariable String commentId)
			throws IOException, GeneralSecurityException {
		return ResponseEntity.ok(service.replies(fileId,commentId));

	}

	@GetMapping(value = { "/replies/{fileId}/{commentId}/{replyId}" })
	public ResponseEntity<Reply> replyById(@PathVariable String fileId, @PathVariable String commentId,@PathVariable String replyId)
			throws IOException, GeneralSecurityException {
		return ResponseEntity.ok(service.reply(fileId, commentId,replyId));

	}
	
	@DeleteMapping(value = { "/delete/{fileId}/{commentId}/{replyId}" })
	public ResponseEntity<String> deleteReply(@PathVariable String fileId, @PathVariable String commentId,@PathVariable String replyId)
			throws IOException, GeneralSecurityException {
		
		service.deleteReply(fileId, commentId,replyId);
		return ResponseEntity.ok("Reply deleted successfully");
	}

}
