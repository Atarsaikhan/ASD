package Application;

import Framework.BullColor;
import Framework.FourBullsGame;
import Framework.Player;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	GuiDirector guiDirector;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage theStage) {
		theStage.setTitle("4 bulls");
		
		guiDirector = new GuiDirector(new JavaFxGuiBuilder());
		guiDirector.constructGui();
        
		theStage.setScene(guiDirector.getGui());
		theStage.show();
	}
}
