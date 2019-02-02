package yurt;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class YurtWindowController {

    @FXML
    private Canvas gameCanvas;

    @FXML
    private Button btnNewGame;

    @FXML
    private Button btnRestart;

    @FXML
    private Button btnUndo;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnQuit;
    
    YurtGame game;
    
	@FXML
	private void initialize() {
		game = new YurtGame(gameCanvas.getGraphicsContext2D());
		
		gameCanvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println("canvas handle");
				game.eventHandler((int) e.getX(), (int) e.getY());

			}
		});
	}

    @FXML
    void onNewGameClick(ActionEvent event) {
    	game = new YurtGame(gameCanvas.getGraphicsContext2D());
    	//game.
    }

    @FXML
    void onRestartClick(ActionEvent event) {
    	game.restart();
    }
    
    @FXML
    void onUndoClick(ActionEvent event) {
    	game.undo();
    }

    @FXML
    void onSettingsClick(ActionEvent event) {

    }
    
    @FXML
    void onQuitClick(ActionEvent event) {
    	Platform.exit();
    }

}
