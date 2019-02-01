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
		
		guiDirector = new CGuiDirector( new CJavaFxmlGuiBuilder());
		guiDirector.constructGui();
		
		primaryStage.setTitle("Board Game");
		primaryStage.setScene(guiDirector.getGui());
		
		primaryStage.show();
	}

}
