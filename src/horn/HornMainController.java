package horn;

import framework.IGameController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class HornMainController {

	@FXML
	private Canvas canvas;

	@FXML
	private Button btnRestart;

	@FXML
	private Button btnUndo;

	HornGame game;

	@FXML
	void onRestart_Clicked(ActionEvent event) {
		game.restart();
	}

	@FXML
	void onUndo_Clicked(ActionEvent event) {
		game.undo();
	}

	@FXML
	private void initialize() {
		game = new HornGame(canvas.getGraphicsContext2D());
		canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println("canvas handle");
				game.eventHandler((int) e.getX(), (int) e.getY());
			}
		});
	}

}
