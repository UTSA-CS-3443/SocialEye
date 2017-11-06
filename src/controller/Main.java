package controller;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	
	public static Stage stage;
	public AnchorPane layout;
	
	/**
	 * Creates a scene that shows the main interface of the application
	 */
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
			
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			// Sets the title of the program
			// This displays at the top of the application
			primaryStage.setTitle("SocialEye");
			// Sets the application icon next to the title of the program
			//primaryStage.getIcons().add(new Image("/icons/appicon.png"));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		this.stage = primaryStage;
	}

	public static void main(String[] args) {
		launch(args);

	}
}
