package horn;

import framework.EGameState;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class HornWindowController {

	@FXML
	private Hyperlink hlNewGame;

	@FXML
	private Hyperlink hlUndo;

	@FXML
	private Label lblTopLine;

	@FXML
	private Canvas canvas;

	HornGame game;

	@FXML
	void onRestart_Clicked(ActionEvent event) {
		game.restart();
		lblTopLine.setText("New game started. " + "\nClick a piece to activate and click again on an empty position.");
	}

	@FXML
	void onUndo_Clicked(ActionEvent event) {
		game.undo();
		lblTopLine.setText("Undo. " + "\nClick a piece to activate and click again on an empty position.");
	}

	@FXML
	private void initialize() {
		game = new HornGame(canvas.getGraphicsContext2D());

		lblTopLine.setText(game.getCurrent().getName() + " to move."
				+ "\nClick a piece to activate and click again on an empty position.");

		canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println("canvas handle");
				game.eventHandler((int) e.getX(), (int) e.getY());

				if (game.getGameState().equals(EGameState.GAMEOVER)) {
					lblTopLine.setText(game.getCurrent().getName() + " won." + "\nClick \"New Game\" to start a game.");
				} else {
					lblTopLine.setText(((!game.getMessage().equals("")) ? ("Message: " + game.getMessage() + "\n") : "")
							+ game.getCurrent().getName() + " to move."
							+ "\nClick a piece to activate and click again on an empty position.");
				}
			}
		});
	}

}
