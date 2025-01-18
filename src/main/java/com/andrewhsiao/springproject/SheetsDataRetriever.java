package com.andrewhsiao.springproject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

/*
 * Workflow for OAuth2.0
 * 1. Authorization server grants clientID and clientSecret for the application
 * 2. Application uses clientID and clientSecret to request access to user (google sheets account) data
 * 3. Request is granted leads to obtaining an authorization code
 * 4. Auth code can be used to obtain access token, which can be used to obtain user data
 */
public class SheetsDataRetriever {
    private static final List<String> SCOPES =
      Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    private final String spreadsheetId = "17EP71_7E8mdwR7TNgjB1J4CgMlVM0bt6KgxNtVzdg6U";
    Credential accessToken;

    Sheets service;

    public SheetsDataRetriever() throws IOException, GeneralSecurityException {
        accessToken = getCredentials(HTTP_TRANSPORT);
        service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, accessToken)
        .setApplicationName(APPLICATION_NAME)
        .build();
    }

    public List<List<Object>> retrieveData() throws IOException {
        if (accessToken.getExpiresInSeconds() <= 60) {
            accessToken.refreshToken();
        }
        int numOfDataRetrieved = new ExpenseDatabaseService().getNumExpenses();
        int nextRow = 2 + numOfDataRetrieved;
        String range = String.format("Form Responses 1!B%s:F", nextRow);
        ValueRange response = service.spreadsheets().values()
            .get(spreadsheetId, range)
            .execute();
        List<List<Object>> values = response.getValues();
        numOfDataRetrieved += values.size();
        return values;
    }

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secret and client id from credentials.json
        InputStream in = SheetsDataRetriever.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Use the client id and secret to make a request to the user to grant permissions
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
            .setAccessType("offline")
            .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();

        // Once permissions are granted and auth code is obtains, a Credential object is obtained containing the access token to access google sheets data
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
}


