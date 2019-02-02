package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CMain extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("4 Bulls");
		
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getClassLoader().getResource("resources/FXMLMain.fxml"));
		Parent root = fxmlLoader.load();
		Scene scene = new Scene (root);
		
	
		primaryStage.setTitle("Board Game - 4 Bulls");
		primaryStage.setScene(scene);
		
		primaryStage.show();
	}

}
