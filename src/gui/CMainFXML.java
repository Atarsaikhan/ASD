package gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class CMainFXML extends Application {
	CGuiDirector guiDirector;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("4 bulls");
		
//		FXMLLoader fxmlLoader = new FXMLLoader();
//		fxmlLoader.setLocation(getClass().getClassLoader().getResource("resources/FXMLMain.fxml"));
//		Parent root = fxmlLoader.load();
//		Scene scene = new Scene (root);
		
		guiDirector = new CGuiDirector( new CJavaFxmlGuiBuilder());
		guiDirector.constructGui();
		
		primaryStage.setTitle("Four Bulls Board Game");
		primaryStage.setScene(guiDirector.getGui());
		
		primaryStage.show();
	}

}
