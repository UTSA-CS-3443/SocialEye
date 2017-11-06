package twitter;

import java.util.List;
import java.util.Scanner;

import twitter4j.Status;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * twitter does all things
 * twitter related. No further
 * explanation needed.
 * 
 * @author Brian Le
 *
 */
public class twitter 
{

	// Used to create twitter object
	private TwitterFactory tf;
	private Twitter twitter;
	
	/**
	 * Twitter constructor
	 * 
	 * This creates a twitter object
	 * for use.
	 * 
	 * @author Brian Le
	 */
	public twitter() 
	{
		// Create twitter object
		this.tf = new TwitterFactory();
		this.twitter = tf.getInstance();		
	}
	/**
	 * PostTweet does what it says.
	 * Posts a tweet to the user's twitter. 
	 * 
	 * @author Brian
	 */	
	public void PostTweet()
	{
		// Used to get input from the user
		Scanner scan = new Scanner(System.in);
		
		// The factory instance is re-useable and thread safe.
		this.twitter = TwitterFactory.getSingleton();
	
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
	
	/**
	 * Top10Trends currently gets the top 10 trends worldwide. 
	 * This might change soon
	 *  
	 * @author Brian
	 */	
	public void Top10Trends() throws TwitterException 
	{
	    
	    /* 
	     * Gets the top 10 trends world wide
	     * WOEID stands for Where on Earth ID, the 
	     * WOEID 1 means to search worldwide. 
	     */
		
		Trends trends = this.twitter.getPlaceTrends(1);
	    
		// Used to stop our trends loop once the counter reaches 10.
		int count = 0;
	    
		// Iterate through the list of trends
	    
		for (Trend trend : trends.getTrends()) {
			// Keep getting trends until we reach 10
			if (count < 10) {
				// Get the name of the current trend and iterate to the next trend.
	        		System.out.println(trend.getName());
	        		count+=1;
	        	}
		}		
	}
	/**
	 * As of right now, TwitterTL gets the user's home timeline.
	 * This will be changed later.
	 * 
	 * @author Brian
	 *
	 */	
	public void TwitterTL() {
		// Used to get the name of the user and the list of tweets on the timeline
		String user;
		List<Status> statuses;		
		// Attempt to get the timeline
		try 
		{
			// Verify the user and get their twitter handle
			user = this.twitter.verifyCredentials().getScreenName();
			
			/**
			 *  The follow status code causes our code to be usable for
			 *  either getting the user or home timeline. All you have to do
			 *  is change twitter.getHomeTimeline() to twitter.getUserTimeline()
			 *  to get the user's timeline and vice versa. We will need to implement 
			 *  an if statement later to handle that.
			 * 
			 */
			
			// Attempt to get all the tweets from the user's home timeline
			statuses = this.twitter.getHomeTimeline();
			 // Iterate through each tweet and display them
			 for (Status status : statuses) 
			 {
				 System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());		
			 }
		} 
		// If we were unable to get the timeline
		catch (TwitterException te) 
		{
			// Display an error message and exit the program
			System.out.println("Error attempting to get the timeline.");
			System.exit(-1);
		}		
	}
}
