package controller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import reddit.RedditGet;
import reddit.RedditOAuth;
import reddit.RedditThread;
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
	Button btnRedditLogin, btnTwitterLogin, btnPostTweet, authButton;
	@FXML
	VBox redditVBox;
	@FXML
	Text loginid;
	@FXML
	ScrollPane redditScroll;
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
		WebViewBrowser webViewBrowser = new WebViewBrowser();
		Stage browserStage = new Stage();
		webViewBrowser.start(browserStage);
		browserStage.setOnHiding(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				try {
					loginid.setText("Logged in as: " + RedditGet.username(RedditGet.access_token));
					ArrayList<RedditThread> frontpage = RedditGet.frontpage(RedditGet.access_token);
					for(RedditThread current: frontpage) {

						Pane thread = new Pane();
						thread.setPrefWidth(715);
						thread.setPrefHeight(50);
						thread.setStyle("-fx-border-color: black");

						Text domain = new Text();
						domain.setFont(Font.font(10));
						domain.setFill(Color.LIGHTGRAY);
						domain.setText(current.getDomain());


						Hyperlink titlelink = new Hyperlink(current.getTitle());
						titlelink.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent e) {
								try {
									Desktop.getDesktop().browse(current.getUrl().toURI());
								} catch (IOException | URISyntaxException e1) {
									e1.printStackTrace();
								}
							}
						});

						TextFlow title = new TextFlow();
						title.getChildren().add(titlelink);
						title.getChildren().add(domain);
						title.setLayoutX(50);


						Text author = new Text();
						author.setFont(Font.font(12));
						author.setFill(Color.WHITE);
						author.setText("Submitted by " + current.getAuthor());

						Hyperlink commentLink = new Hyperlink(current.getNum_comments() + " comments");
						commentLink.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent e) {
								try {
									Desktop.getDesktop().browse(current.getPermalinkURL().toURI());
								} catch (IOException | URISyntaxException e2) {
									e2.printStackTrace();
								}
							}
						});

						TextFlow comment = new TextFlow();
						comment.getChildren().add(author);
						comment.getChildren().add(commentLink);
						comment.setLayoutY(20);
						comment.setLayoutX(50);

						Text score = new Text();
						score.setFont(Font.font(14));
						score.setFill(Color.WHITE);
						score.setText(Integer.toString(current.getScore()));
						score.setY(25);
						score.setX(5);

						thread.getChildren().add(score);
						thread.getChildren().add(title);
						thread.getChildren().add(comment);

						redditVBox.getChildren().add(thread);	
					}
				} catch (IOException | JSONException e) {
					e.printStackTrace();
				}
			}});

	}
	
	public void twitterLogin()
	{
		GetAccessToken.login();
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
               /* facebookTab.setOnSelectionChanged(new EventHandler<Event>() 
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
                */
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
