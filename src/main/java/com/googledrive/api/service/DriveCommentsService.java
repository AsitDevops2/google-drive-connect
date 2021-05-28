package com.googledrive.api.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.services.drive.model.Comment;
import com.google.api.services.drive.model.CommentList;
import com.googledrive.api.config.GoogleApiClientConfig;
import com.googledrive.api.util.SourceCodeDownloadUtil;

/**
 * Service class for all api's
 *
 */
@Service
public class DriveCommentsService {
	@Autowired
	GoogleApiClientConfig config;

	Logger logger = LoggerFactory.getLogger(DriveCommentsService.class);

	public String doGoogleSignIn() {
		return config.doGoogleSignIn();
	}

	public void saveAuthorizationCode(String code) {
		try {
			config.saveAuthorizationCode(code);
		} catch (IOException ioException) {
			logger.error("Error: ", ioException);

		}
	}

	/**
	 * Method to get comments
	 * 
	 * @param fileid
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public List<Comment> comments(String fileid) throws IOException, GeneralSecurityException {
		CommentList list = config.googleDrive().comments().list(fileid).setFields("*").execute();
		return list.getComments();
	}

	/**
	 * Method to get comments by file id and comment id
	 * 
	 * @param fileid
	 * @param commentId
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public Comment commentById(String fileid, String commentId) throws IOException, GeneralSecurityException {
		return config.googleDrive().comments().get(fileid, commentId).setFields("*").execute();
	}

	public Comment createComment(String fileid, String content) throws IOException, GeneralSecurityException {
		Comment comment = new Comment();
		comment.setContent(content);
		return config.googleDrive().comments().create(fileid, comment).setFields("*").execute();
	}

}
