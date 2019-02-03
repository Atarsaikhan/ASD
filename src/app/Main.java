package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getClassLoader().getResource("resources/MainWindow.fxml"));
		
		Parent root = fxmlLoader.load();
		
		MainWindowController cont = (MainWindowController ) fxmlLoader.getController();
		cont.setStage(primaryStage);
		
		Scene scene = new Scene (root);
	
		primaryStage.setTitle("Mongolian Board Game");
		primaryStage.setScene(scene);
		
		primaryStage.show();
	}

}
