package SMauthorization;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;


public class GetAccessToken {

	// This is the consumer key for our twitter app Social Eye
    private static final String TWITTER_CONSUMER_KEY = "IhlUpkWbpS2DwPRUo1hlHlxVV";
    // This is the consumer secret for our twitter app Social Eye
	private static final String TWITTER_CONSUMER_SECRET = "HtTWJJUEjVvakcvEptxUokncNycBa8RDpz26J8VMhVKuZsvcJY";

	/*
	 * Get authorization for the user
	 * User must have a twitter account in order to access our app
	 * 
	 */
	public static void login() {
    	
    	// This will reset the accessToken and accessTokenSecret every time for each user
    	ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(TWITTER_CONSUMER_KEY)
                .setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET)
                .setOAuthAccessToken(null)
                .setOAuthAccessTokenSecret(null);
        
        File file = new File("twitter4j.properties");
        Properties prop = new Properties();
        InputStream is = null;
        OutputStream os = null;
        // Does the properties file exist?
        try {
            if (file.exists()) {
                is = new FileInputStream(file);
                prop.load(is);
            }
            //
            prop.setProperty("oauth.consumerKey", TWITTER_CONSUMER_KEY);
            prop.setProperty("oauth.consumerSecret", TWITTER_CONSUMER_SECRET);
            os = new FileOutputStream("twitter4j.properties");
            prop.store(os, "twitter4j.properties");
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(-1);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignore) {
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException ignore) {
                }
            }
        }
        
        try {
        	// Create a twitter factory object that stores the authorization keys from the configuration build
            TwitterFactory TwitterFactory = new TwitterFactory(cb.build());
            // Create a new instance of twitter that gets the authorization keys from twitter factory
            Twitter twitter = TwitterFactory.getInstance();
            // Get the request tokens using the consumer authorization keys
            RequestToken requestToken = twitter.getOAuthRequestToken();
            System.out.println("Got request token.");
            // These requestTokens are required to prompt the user to login to their account in order to use our app
            System.out.println("Request token: " + requestToken.getToken());
            System.out.println("Request token secret: " + requestToken.getTokenSecret());
            AccessToken accessToken = null;

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (null == accessToken) {
                System.out.println("Open the following URL and grant access to your account:");
                System.out.println(requestToken.getAuthorizationURL());
                try {
                    Desktop.getDesktop().browse(new URI(requestToken.getAuthorizationURL()));
                } catch (UnsupportedOperationException ignore) {
                } catch (IOException ignore) {
                } catch (URISyntaxException e) {
                    throw new AssertionError(e);
                }
                System.out.print("Enter the PIN(if available) and hit enter after you granted access.[PIN]:");
                String pin = br.readLine();
                try {
                    if (pin.length() > 0) {
                        accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                    } else {
                        accessToken = twitter.getOAuthAccessToken(requestToken);
                    }
                } catch (TwitterException te) {
                    if (401 == te.getStatusCode()) {
                        System.out.println("Unable to get the access token.");
                    } else {
                        te.printStackTrace();
                    }
                }
            }
            System.out.println("Got access token.");
            System.out.println("Access token: " + accessToken.getToken());
            System.out.println("Access token secret: " + accessToken.getTokenSecret());

            try {
                prop.setProperty("oauth.accessToken", accessToken.getToken());
                prop.setProperty("oauth.accessTokenSecret", accessToken.getTokenSecret());
                os = new FileOutputStream(file);
                prop.store(os, "twitter4j.properties");
                os.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
                System.exit(-1);
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException ignore) {
                    }
                }
            }
            System.out.println("Successfully stored access token to " + file.getAbsolutePath() + "."); 
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get accessToken: " + te.getMessage());
            System.exit(-1);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("Failed to read the system input.");
            System.exit(-1);
        }
    }
}
