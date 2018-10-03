package com.auth.auth.controller;

import com.auth.auth.model.GoogleUser;
import com.auth.auth.service.SaveUser;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets.Details;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;
import com.google.api.services.plus.PlusScopes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Controller
@RestController
public class GoogleAuthenticationController {

	private static final String APPLICATION_NAME = "Test";
	private static HttpTransport httpTransport;
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	GoogleClientSecrets clientSecrets;
	GoogleAuthorizationCodeFlow flow;
	Credential credential;

	@Value("${gmail.client.clientId}")
	private String clientId;

	@Value("${gmail.client.clientSecret}")
	private String clientSecret;

	@Value("${gmail.client.redirectUri}")
	private String redirectUri;

	@RequestMapping(value = "/login/gmail", method = RequestMethod.GET)
	public RedirectView googleConnectionStatus(HttpServletRequest request) throws Exception {
		return new RedirectView(authorize());
	}

	@Autowired
	SaveUser saveUser;

	@RequestMapping(value = "/login/gmailCallback", method = RequestMethod.GET, params = "code")
	public ResponseEntity<String> oauth2Callback(@RequestParam(value = "code") String code) {

		JSONObject json = new JSONObject();
		JSONArray arr = new JSONArray();
        Userinfoplus userinfo = null;
		try {
			TokenResponse response = flow.newTokenRequest(code).setRedirectUri(redirectUri).execute();
			credential = flow.createAndStoreCredential(response, "userID");

			Oauth2 oauth2 = new Oauth2.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName(
					APPLICATION_NAME).build();

			userinfo = oauth2.userinfo().v2().me().get().execute();
            System.out.println(userinfo.toString());

		} catch (Exception e) {

			System.out.println("exception cached ");
			e.printStackTrace();
		}

		GoogleUser user = new GoogleUser(userinfo.getGivenName(),
				userinfo.getFamilyName(),
				userinfo.getEmail(),
				userinfo.getPicture());
        GoogleUser savedUser = saveUser.save(user);
		if(savedUser!=null){
			if(saveUser.generateRefreshToken(savedUser)!=null){
				return new ResponseEntity<>(saveUser.generateJwtToken(user), HttpStatus.OK);
			}
		}


		return new ResponseEntity<>("try again", HttpStatus.UNAUTHORIZED);
	}

	private String authorize() throws Exception {
		AuthorizationCodeRequestUrl authorizationUrl;
		if (flow == null) {
			Details web = new Details();
            System.out.println(clientId+"***/n"+clientSecret);
			web.setClientId(clientId);
			web.setClientSecret(clientSecret);
			clientSecrets = new GoogleClientSecrets().setWeb(web);
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets,
					Collections.singleton(PlusScopes.USERINFO_EMAIL)).build();
		}
		authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(redirectUri);

		System.out.println("gamil authorizationUrl ->" + authorizationUrl);
		return authorizationUrl.build();
	}


}
