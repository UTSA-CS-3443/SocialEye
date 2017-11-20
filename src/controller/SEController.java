package controller;

import java.io.IOException;

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
import javafx.scene.image.Image;
import javafx.stage.Stage;
import reddit.WebViewBrowser;
import reddit.WebViewBrowser.Browser;
import twitter.*;

public class SEController 
{
	@FXML
	TabPane SETabPane;
	@FXML
	Tab homeTab, twitterTab, facebookTab, redditTab;
	@FXML
	Button btnRedditLogin;
	Stage stage;
//	Scene scene;
	/**
	 * On click on a login button, a new browser will open up to prompt
	 * the user to login.
	 * 
	 * @author Devin Nguyen
	 */
	public void onLoginClick() {
		btnRedditLogin.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent event) {
		    	WebViewBrowser webViewBrowser = new WebViewBrowser();
		    	webViewBrowser.start(new Stage());
		    }
		});
	}
	
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
        				Scene scene = new Scene(root,400,400);
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
