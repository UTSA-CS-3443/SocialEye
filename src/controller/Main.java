package controller;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	
	public AnchorPane layout;
	
	/**
	 * Creates a scene that shows the main interface of the application
	 */
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//FXMLLoader loader = new FXMLLoader();
			//loader.setLocation(Main.class.getResource("/SocialEye/src/view/Main.fxml"));
			Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
