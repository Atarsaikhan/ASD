package yurt;

import gui.CGuiDirector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	CGuiDirector guiDirector;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getClassLoader().getResource("resources/YurtWindow.fxml"));
		Parent root = fxmlLoader.load();
		Scene scene = new Scene (root);
		
	
		primaryStage.setTitle("Board Game - Yurt");
		primaryStage.setScene(scene);
		
		primaryStage.show();
	}

}
