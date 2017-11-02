package twitter;

import java.io.IOException;
import twitter4j.*;

/**
 * Top10Trends currently gets the top 10 trends worldwide. 
 * This might change soon
 *  
 * @author Brian
 */

public class Top10Trends {

	public static void main(String[] args) throws IOException, TwitterException {
		
    		// Create twitter object
		TwitterFactory tf = new TwitterFactory();
		Twitter twitter = tf.getInstance();
        
        /* 
         * Gets the top 10 trends world wide
         * WOEID stands for Where on Earth ID, the 
         * WOEID 1 means to search worldwide. 
         */
		
		Trends trends = twitter.getPlaceTrends(1);
        
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
}
