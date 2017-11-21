package reddit;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Various GET calls for the reddit api after obtaining an access token.
 * 
 * @author Devin Nguyen
 *
 */

public class RedditGet {
	public static String access_token;
	
	
	/**
	 * 
	 * @param access_token
	 * @return <username> ( <link_karma> : <commment_karma> ) format
	 * @throws IOException
	 * @throws JSONException
	 */
	public static String username(String access_token) throws IOException, JSONException {
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		HttpGet httpget = new HttpGet("https://oauth.reddit.com/api/v1/me");
        httpget.addHeader("User-Agent", "windows:SocialEye:v0.1.1 (by /u/diagonr)");
		httpget.setHeader("Authorization", "bearer " + access_token);
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		String json = IOUtils.toString(entity.getContent(), "UTF-8");
		JSONObject jsonObject = new JSONObject(json);
		System.out.println(json);
		System.out.println("Username: " + jsonObject.getString("name"));
		httpclient.close();
		return jsonObject.getString("name")
				+ " (" + jsonObject.getInt("link_karma") 
				+ " : " + jsonObject.getInt("comment_karma") + ")";
	}
	
	public static String frontpage(String access_token) throws UnsupportedOperationException, IOException, JSONException {
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		HttpGet httpget = new HttpGet("https://oauth.reddit.com/");
        httpget.addHeader("User-Agent", "windows:SocialEye:v0.1.1 (by /u/diagonr)");
		httpget.setHeader("Authorization", "bearer " + access_token);
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		String json = IOUtils.toString(entity.getContent(), "UTF-8");
		JSONObject jsonObject = new JSONObject(json);
		System.out.println(json);
		httpclient.close();
		JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("children");
		System.out.println(jsonArray.get(0));
		for(int i = 0; i < 25; i++) {
			JSONObject current = jsonArray.getJSONObject(i);
			System.out.println(current.getJSONObject("data").getString("title"));
		}
		return "";
	}
	
}
