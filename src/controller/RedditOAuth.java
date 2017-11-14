package controller;

import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.util.List;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.awt.Desktop;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;


public class RedditOAuth {
	
	//Send user to auth website; will need to authorize
	public static final String OAUTH_API_DOMAIN = "https://oauth.reddit.com";
	//Reddit sends user to redirect	URI
	//private static final String REDIRECT_URI = OAUTH_APP_DOMAIN + "/auth";
	private static final String REDIRECT_URI = "http://127.0.0.1:8888/auth";

	public static final String OAUTH_AUTH_URL = "https://ssl.reddit.com/api/v1/authorize";
    // https://ssl.reddit.com/api/v1/authorize?client_id=CLIENT_ID&response_type=TYPE&state=RANDOM_STRING&redirect_uri=URI&duration=DURATION&scope=SCOPE_STRING
	public static final String OAUTH_APP_DOMAIN = "https://localhost.com";

	//Get token with POST data
	public static final String OAUTH_TOKEN_URL = "https://ssl.reddit.com/api/v1/access_token";
	//TODO replace ???? fields and all following with defined parameters
	//obtained from the reddit api. 
	public static final String MY_APP_ID = "O1gCpk-lZvKqPQ";
   // public static final String MY_APP_SECRET = "??????????";
	public static final String SCOPE_ID = "identity";
	public static final String ACCESS_TOKEN_NAME = "access_token";
	public static final String REFRESH_TOKEN_NAME = "refresh_token";
	public static final String SCOPES = "identity,edit,history,mysubreddits,read,report,save,submit,subscribe,vote";
	
	public static final boolean PERMANENT_ACCESS = false;
	
	HttpClient httpclient = HttpClients.createDefault();
	HttpPost httppost = new HttpPost(OAUTH_TOKEN_URL);
	
	//Request parameters
	List<NameValuePair> params = new ArrayList<NameValuePair>(2);
	public static String getUserAuthUrl(String state) {
		String duration = PERMANENT_ACCESS ? "permanent" : "temporary";
		String url = OAUTH_AUTH_URL + "?client_id=" + MY_APP_ID + "&response_type=code&state=" + state
                + "&redirect_uri=" + REDIRECT_URI + "&duration=" + duration + "&scope=" + SCOPES; 
        // scopes: modposts, identity, edit, flair, history, modconfig, modflair, modlog, modposts, modwiki,
        // mysubreddits, privatemessages, read, report, save, submit, subscribe, vote, wikiedit, wikiread, etc.
        return url;
	}
	
	public static JSONObject getToken(String code) throws IOException {
		System.out.println("getToken for code= " + code);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		JSONObject jsonObject = null;
		HttpEntity entity = null;
	    try {
	        URL url = new URL(OAUTH_TOKEN_URL);
	        String nullFragment = null;
	        URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), nullFragment);
	        System.out.println("URI " + uri.toString() + " is OK");
			List<NameValuePair> params = new ArrayList <NameValuePair>();
			params.add(new BasicNameValuePair("grant_type", "authorization_code"));
			params.add(new BasicNameValuePair("code", code));
			params.add(new BasicNameValuePair("redirect_uri", REDIRECT_URI));
			HttpPost httpPost = new HttpPost(uri);
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			
			response = httpclient.execute(httpPost);
			entity = response.getEntity();
			
			if(entity.isStreaming()) {
				String json = entity.toString();
				//String json = response.getStatusLine().toString();
				JSONTokener tokener = new JSONTokener (json);
				jsonObject = new JSONObject(tokener);
			}
			//TODO do something with entity
			
			EntityUtils.consume(entity);
			
	      } catch (MalformedURLException e) {
	        System.out.println("URL " + OAUTH_TOKEN_URL + " is a malformed URL");
	      } catch (URISyntaxException e) {
	        System.out.println("URI " + OAUTH_TOKEN_URL + " is a malformed URL");
	      } catch (JSONException e){
	    	System.out.println("Request failed with: " + response.getStatusLine());
	      } finally {
	    	  response.close();
	      }
	    return jsonObject;
	}

	
	//params.add(new BasicNameValuePair("grant_type", "authorization_code"));
	//TODO modify POST data to retrieve access token
	//grant_type=authorization_code&code=CODE&redirect_uri=URI
	
	//Look on https://github.com/reddit/reddit/wiki/OAuth2 for info
}
