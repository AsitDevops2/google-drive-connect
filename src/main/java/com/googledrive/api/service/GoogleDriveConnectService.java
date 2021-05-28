package com.googledrive.api.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googledrive.api.config.GoogleApiClientConfig;
import com.googledrive.api.util.SourceCodeDownloadUtil;

/**
 * Service class for all api's
 *
 */
@Service
public class GoogleDriveConnectService {
	@Autowired
	GoogleApiClientConfig config;

	Logger logger = LoggerFactory.getLogger(GoogleDriveConnectService.class);

	@Autowired
	SourceCodeDownloadUtil util;

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
	 * Method to download source code
	 * 
	 * @param response
	 * 
	 */
	public void downloadSource() {
		util.downloadSourceCode();
	}

}
