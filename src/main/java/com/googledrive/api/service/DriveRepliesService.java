package com.googledrive.api.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.services.drive.model.Reply;
import com.google.api.services.drive.model.ReplyList;
import com.googledrive.api.config.GoogleApiClientConfig;

@Service
public class DriveRepliesService {

	@Autowired
	GoogleApiClientConfig config;

	Logger logger = LoggerFactory.getLogger(DriveRepliesService.class);

	/**
	 * Method to get list of replies on a file and comment
	 * 
	 * @param fileId
	 * @param commentId
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public List<Reply> replies(String fileId, String commentId) throws IOException, GeneralSecurityException {
		ReplyList list = config.googleDrive().replies().list(fileId, commentId).setFields("*").setIncludeDeleted(true)
				.execute();
		return list.getReplies();
	}

	/**
	 * Method to get reply for a given reply id
	 * 
	 * @param fileId
	 * @param commentId
	 * @param replyId
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public Reply reply(String fileId, String commentId, String replyId) throws IOException, GeneralSecurityException {
		return config.googleDrive().replies().get(fileId, commentId, replyId).setFields("*").setIncludeDeleted(true)
				.execute();
	}

	
	/**
	 * Method to delete reply
	 * 
	 * @param fileId
	 * @param commentId
	 * @param replyId
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public Void deleteReply(String fileId, String commentId,String replyId) throws IOException, GeneralSecurityException {
		
		return config.googleDrive().replies().delete(fileId, commentId, replyId).setFields("*").execute();

	}

}
