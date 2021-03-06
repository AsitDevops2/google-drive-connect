package com.googledrive.api.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive.Files.Export;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.googledrive.api.config.GoogleApiClientConfig;

@Service
public class DriveFilesService {

	@Autowired
	GoogleApiClientConfig config;

	Logger logger = LoggerFactory.getLogger(DriveFilesService.class);

	

	/**
	 * Method to get list of files from google drive
	 * 
	 * @return list of files
	 * 
	 * @throws IOException
	 * 
	 * @throws GeneralSecurityException
	 */
	public List<File> files() throws IOException, GeneralSecurityException {
		// Print the names and IDs for up to 10 files.
		FileList result = config.googleDrive().files().list().setPageSize(10)
				.setFields("nextPageToken, files(id, name)").execute();
		return result.getFiles();
	}

	/**
	 * Method to download file from google drive
	 * 
	 * @param id
	 * 
	 * @param outputStream
	 * 
	 * @throws IOException
	 */
	public void downloadFile(@Nonnull String id, OutputStream outputStream)
			throws IOException, GeneralSecurityException {
		config.googleDrive().files().get(id).executeMediaAndDownloadTo(outputStream);
	}

	/**
	 * Method to download file from google drive
	 * 
	 * @param id
	 * 
	 * @param outputStream
	 * 
	 * @throws IOException
	 */
	public Export downloadFile(@Nonnull String id, String mimeTYpe) throws IOException, GeneralSecurityException {
		return config.googleDrive().files().export(id, mimeTYpe);
	}

	/**
	 * Method to get file meta data for a given file id
	 * 
	 * @param id
	 * 
	 * @return File
	 * 
	 * @throws IOException
	 */
	public File getFile(@Nonnull String id) throws IOException, GeneralSecurityException {
		return config.googleDrive().files().get(id).execute();
	}


	/**
	 * Method to upload file to google drive
	 * 
	 * @return
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public void upload(MultipartFile file) {
		try {
			String folderId = "1SDo8Tl_Wc0dw11XpUxfHnA9FrGWPd-mC";
			if (null != file) {
				File fileMetadata = new File();
				fileMetadata.setParents(Collections.singletonList(folderId));
				fileMetadata.setName(file.getOriginalFilename());
				 config.googleDrive().files().create(fileMetadata,
						new InputStreamContent(file.getContentType(), new ByteArrayInputStream(file.getBytes())))
						.setFields("id").execute();
			}
		} catch (Exception e) {
			logger.error("Exception while uploading: ", e);
		}

	}
	
	public void delete(@Nonnull String fileId) throws IOException, GeneralSecurityException
	{
		 config.googleDrive().files().delete(fileId).execute();
	}
}
