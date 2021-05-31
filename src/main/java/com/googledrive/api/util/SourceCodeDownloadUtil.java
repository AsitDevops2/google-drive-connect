package com.googledrive.api.util;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Class for downloading source code util
 *
 */
@Component
public class SourceCodeDownloadUtil {
	Logger logger = LoggerFactory.getLogger(SourceCodeDownloadUtil.class);
	private String dirName = "D:\\";

	/**
	 * Method to download source code from git hub
	 */
	public void downloadSourceCode() {
		String fileName = dirName + "/googleDrive.zip";
		String fileUrl = "https://github.com/AsitDevops2/google-drive-connect/archive/main.zip";
		URL fileUrlObj = null;
		try {
			fileUrlObj = new URL(fileUrl);
		} catch (MalformedURLException exception) {
			logger.error("Exception while downloading source code: ", exception);
		}
		if (null != fileUrlObj) {
			try (BufferedInputStream inStream = new BufferedInputStream(fileUrlObj.openStream())) {
				fileOutputStream(fileName, inStream);

			} catch (IOException exception) {
				logger.error("Exception while downloading: ", exception);
			}

		}
	}

	/**
	 * @param fileName
	 * @param inStream
	 */
	private void fileOutputStream(String fileName, BufferedInputStream inStream) {
		try (FileOutputStream outStream = new FileOutputStream(fileName)) {

			byte[] data = new byte[1024];
			int count;
			while ((count = inStream.read(data, 0, 1024)) != -1) {
				outStream.write(data, 0, count);
			}
		} catch (IOException exception) {
			logger.error("Exception while writing to output stream: ", exception);
		}
	}
}
