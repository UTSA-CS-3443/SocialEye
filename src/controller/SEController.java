package controller;

import java.io.IOException;

import org.json.JSONException;

import SMauthorization.GetAccessToken;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import reddit.RedditGet;
import reddit.RedditOAuth;
import reddit.WebViewBrowser;
import reddit.WebViewBrowser.Browser;
import twitter.*;
import twitter4j.TwitterException;

public class SEController 
{
	@FXML
	TabPane SETabPane;
	@FXML
	Tab homeTab, twitterTab, facebookTab, redditTab;
	@FXML
	Button btnRedditLogin, btnTwitterLogin, btnPostTweet;
	@FXML
	Text loginid;
	Stage stage;
	@FXML
	TextFlow localTrendsBox, globalTrendsBox, twitterFeed, redditFeed, facebookFeed;
	@FXML
	TextArea postTweet;

	/**
	 * On click on a login button, a new browser will open up to prompt
	 * the user to login.
	 * For Twitter, an external browser will be opened and the user
	 * will be prompted to authorize the application, then given
	 * a unique access token
	 * 
	 * @author Devin Nguyen
	 */
	
	public void onLoginClick() {
		btnRedditLogin.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent event) {
	    				WebViewBrowser webViewBrowser = new WebViewBrowser();
	    				Stage browserStage = new Stage();
	    				webViewBrowser.start(browserStage);
	    				browserStage.setOnHiding(new EventHandler<WindowEvent>() {
	    				    @Override
	    				    public void handle(WindowEvent event) {
		    					try {
									loginid.setText("Successfully logged in as: " + RedditGet.username(RedditGet.access_token));
								} catch (IOException | JSONException e) {
									e.printStackTrace();
								}
	    				    }
	    				});
		    }
		});
		btnTwitterLogin.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				GetAccessToken.login();
			}
		});
	}
	
	/**
	 * On click, takes the text from the postTweet TextArea and Tweets it
	 *
	 * @author Alex Shi
	 */
	
	/*
	public void onClick() {
		btnPostTweet.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				twitter twitter = new twitter();
				String tweet;
				tweet = postTweet.getText();
				System.out.println(tweet);
				twitter.PostTweet(tweet);
			}
		});
	} /*
	
	/**
	 * onTabSelect will perform certain actions
	 * based on what the user clicks on.
	 * 
	 * 
	 * @author Brian Le
	 */
	public void onTabSelect() 
	{
		
		// Tab is set to home by default
		homeTab.setOnSelectionChanged(new EventHandler<Event>() 
		{
            @Override
            public void handle(Event t) 
            {
                if (homeTab.isSelected()) 
                {
                	// Attempt to change the view back to the default view
        			try
        			{
        				// change back to the home view
        				Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        				Scene scene = new Scene(root,743,727);
        				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        				Main.stage.setScene(scene);
        				Main.stage.show();														  
        			}
        			// We were unable to set the view back to the default view
        			catch(Exception e) {
        				e.printStackTrace(); 
        			}
                }
                
                // If the user selects the twitter tab
                twitterTab.setOnSelectionChanged(new EventHandler<Event>() 
                {
                    @Override
                    public void handle(Event t)
                    {
                    	// The following will be implemented later
                        if (twitterTab.isSelected()) 
                        {
                            System.out.println("twitter");
                            
                            //create a new twitter object
                            twitter twitter = new twitter();
                            try
							{
                            		// Gets global and local trends
                            		// 1 is global, 2487796 is hard coded for San Antonio 8)
								twitter.Top10Trends(globalTrendsBox, 1);
								twitter.Top10Trends(localTrendsBox, 2487796);
								// Gets the user's twitter feed
								twitter.TwitterTL(twitterFeed);
							} catch (TwitterException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                        }
                    }
                });
                
                // If the user selects facebook
                facebookTab.setOnSelectionChanged(new EventHandler<Event>() 
                {
                    @Override
                    public void handle(Event t)
                    {
                    	// The following will be implemented later
                        if (facebookTab.isSelected()) 
                        {
                            System.out.println("facebook");
                        }
                    }
                });
                
                // If the user selects reddit
                redditTab.setOnSelectionChanged(new EventHandler<Event>() 
                {
                    @Override
                    public void handle(Event t)
                    {
                    	// The following will be implemented later
                        if (redditTab.isSelected()) 
                        {
                            System.out.println("reddit");
                        }
                    }
                });
                
            }
        });
	}
	

	
}
