package reddit;
import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import com.sun.media.jfxmedia.logging.Logger;

import java.util.ArrayList;
import java.util.regex.*;

import org.json.JSONException;

import controller.Main;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
	 

public class WebViewBrowser extends Application {
    private Scene scene;
    @Override public void start(Stage stage) {
        // create the scene
        stage.setTitle("Reddit Login");
        scene = new Scene(new Browser(),1000,750, Color.web("#666970"));
        stage.setScene(scene);
        scene.getStylesheets().add("view/webview.css");        
        stage.show();
    }

public class Browser extends Region {
 
    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();
     
    public Browser() {
        //apply the styles
        getStyleClass().add("browser");
        // load the web page
		URL url;
		try {
			url = new URL(RedditOAuth.getUserAuthUrl("test"));
			String nullFragment = null;
			URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), nullFragment );
	        webEngine.locationProperty().addListener(new urlListener());
			webEngine.load(uri.toString());
	       // webEngine.load("https://diagonr.github.io/SocialEyeRedirect/query=213213&code=test");
	        
	        
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		

        //add the web view to the scene
	        getChildren().add(browser);
	
	    }
    
    	private class urlListener implements ChangeListener<String>{
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(newValue.contains("https://diagonr.github.io/SocialEyeRedirect/?state=test")) {
					RedditOAuth.returnUrl = webEngine.getLocation();
					Pattern pattern = Pattern.compile("code=(.*)");
					Matcher matcher = pattern.matcher(RedditOAuth.returnUrl);
					if(matcher.find()) {
						try {
							RedditGet.access_token = 
									RedditOAuth.getAccessTokenFromJSONString(
											RedditOAuth.getToken(matcher.group(1)));
							ArrayList<RedditThread> frontpage = RedditGet.frontpage(RedditGet.access_token);
							System.out.println(frontpage.get(0).getTitle());
							System.out.println(RedditGet.username(RedditGet.access_token));
							browser.getScene().getWindow().hide();						
						} catch (IOException | JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
    		
    	}
			
	    private Node createSpacer() {
	        Region spacer = new Region();
	        HBox.setHgrow(spacer, Priority.ALWAYS);
	        return spacer;
	    }
	 
	    @Override protected void layoutChildren() {
	        double w = getWidth();
	        double h = getHeight();
	        layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
	    }
	 
	    @Override protected double computePrefWidth(double height) {
	        return 750;
	    }
	 
	    @Override protected double computePrefHeight(double width) {
	        return 750;
	    }
	}
}
