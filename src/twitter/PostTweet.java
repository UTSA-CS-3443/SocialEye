package twitter;

import java.io.IOException;
import java.util.Scanner;

import twitter4j.*;

/**
 * PostTweet does what it says.
 * Posts a tweet to the user's twitter. 
 * 
 * @author Brian
 *
 */
public class PostTweet 
{
    public static void main(String[] args) throws IOException, TwitterException 
    {
    	// Used to get input from the user
        Scanner scan = new Scanner(System.in);
       // The factory instance is re-useable and thread safe.
	    Twitter twitter = TwitterFactory.getSingleton();
	    // Gets input from the user of what they want to tweet
	    String tweet = scan.nextLine(); 
	    // Attempt to post a tweet to twitter
	    try {
	    	// Post a tweet to your timeline
	    	Status status = twitter.updateStatus(tweet);
	    // If we were unable to post a tweet
	    } catch (TwitterException e) {
	        System.err.println("Error occurred while trying to post tweet.");
	    }
    }
}
