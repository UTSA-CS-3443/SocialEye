package twitter;

import java.util.List;
import twitter4j.*;

/**
 * As of right now, TwitterTL gets the user's home timeline.
 * This will be changed later.
 * 
 * @author Brian
 *
 */
public class TwitterTL
{
	public static void main(String[] args) 
	{
		
		// gets Twitter instance with default credentials
		Twitter twitter = new TwitterFactory().getInstance();
		// Used to get the name of the user and the list of tweets on the timeline
		String user;
		List<Status> statuses;		
		// Attempt to get the timeline
		try 
		{
			// Verify the user and get their twitter handle
			user = twitter.verifyCredentials().getScreenName();
			
			/**
			 *  The follow status code causes our code to be usable for
			 *  either getting the user or home timeline. All you have to do
			 *  is change twitter.getHomeTimeline() to twitter.getUserTimeline()
			 *  to get the user's timeline and vice versa. We will need to implement 
			 *  an if statement later to handle that.
			 * 
			 */
			
			// Attempt to get all the tweets from the user's home timeline
			statuses = twitter.getHomeTimeline();
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
