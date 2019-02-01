package fxgui;

import gui.CGuiDirector;
import javafx.application.Application;
import javafx.stage.Stage;

public class CMain extends Application {
	CGuiDirector guiDirector;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage theStage) {
		theStage.setTitle("4 bulls");
		
		guiDirector = new CGuiDirector(new CJavaFxGuiBuilder());
		guiDirector.constructGui();
        
		theStage.setScene(guiDirector.getGui());
		theStage.show();
	}
}
