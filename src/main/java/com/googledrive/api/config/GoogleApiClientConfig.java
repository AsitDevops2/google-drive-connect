package com.googledrive.api.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

@Configuration
public class GoogleApiClientConfig {

	private static final String USER_IDENTIFIER_KEY = "MY_DUMMY_USER";
	Logger logger = LoggerFactory.getLogger(GoogleApiClientConfig.class);

	@Value("${google.oauth.callback.uri}")
	private String callbackURI;

	@Value("${google.secret.key.path}")
	private String gdSecretKeys;

	@Value("${google.credentials.folder.path}")
	private Resource credentialsFolder;

	/**
	 * Provides a unmodifiable {@link Set<String>} of Google OAuth 2.0 scopes to be
	 * used.
	 *
	 * @return An unmodifiable {@link Set<String>}
	 */
	private Set<String> googleOAuth2Scopes() {
		Set<String> googleOAuth2Scopes = new HashSet<>();
		googleOAuth2Scopes.add(DriveScopes.DRIVE);
		return Collections.unmodifiableSet(googleOAuth2Scopes);
	}

	private GoogleAuthorizationCodeFlow flow;

	@PostConstruct
	public void init() throws FileNotFoundException {
		InputStream in = GoogleApiClientConfig.class.getResourceAsStream(gdSecretKeys);
		if (in == null) {
			throw new FileNotFoundException("Resource not found: " + gdSecretKeys);
		}
		GoogleClientSecrets secrets;
		try {
			secrets = GoogleClientSecrets.load(jacksonFactory(), new InputStreamReader(in));

			flow = new GoogleAuthorizationCodeFlow.Builder(netHttpTransport(), jacksonFactory(), secrets,
					googleOAuth2Scopes()).setDataStoreFactory(new FileDataStoreFactory(credentialsFolder.getFile()))
							.build();
		} catch (IOException | GeneralSecurityException exception) {
			logger.error("Error: ", exception);

		}
	}

	/**
	 * A preconfigured HTTP client for calling out to Google APIs.
	 *
	 * @return {@link NetHttpTransport}
	 */

	public NetHttpTransport netHttpTransport() throws GeneralSecurityException, IOException {
		return GoogleNetHttpTransport.newTrustedTransport();
	}

	public String doGoogleSignIn() {
		GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();
		return url.setRedirectUri(callbackURI).setAccessType("offline").build();
	}

	public void refreshCredentials() throws IOException {

		Credential credential = flow.loadCredential(USER_IDENTIFIER_KEY);
		if (credential != null) {
			credential.refreshToken();

		}

	}

	private void saveToken(String code) throws IOException {
		GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(callbackURI).execute();
		flow.createAndStoreCredential(response, USER_IDENTIFIER_KEY);

	}

	public void saveAuthorizationCode(String code) throws IOException {
		if (code != null) {
			saveToken(code);

		}
	}

	/**
	 * Abstract low-level JSON factory.
	 *
	 * @return {@link JacksonFactory}
	 */

	public JacksonFactory jacksonFactory() {
		return JacksonFactory.getDefaultInstance();
	}

	public Drive googleDrive() throws GeneralSecurityException, IOException {
		Credential cred = flow.loadCredential(USER_IDENTIFIER_KEY);

		return new Drive(netHttpTransport(), jacksonFactory(), cred);
	}
}
