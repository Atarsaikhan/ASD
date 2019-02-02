package horn;

import framework.EBullColor;
import framework.EGameState;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class HornMainController {

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
		lblTopLine.setText("New game started. "
				+"\nClick a piece to activate and click again on an empty position.");
	}

	@FXML
	void onUndo_Clicked(ActionEvent event) {
		game.undo();
		lblTopLine.setText("Move undone. " 
				+"\nClick a piece to activate and click again on an empty position.");
	}

	@FXML
	private void initialize() {
		game = new HornGame(canvas.getGraphicsContext2D());
		canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println("canvas handle");
				game.eventHandler((int) e.getX(), (int) e.getY());
				
				String currentColor = "White";
				if (game.getCurrent().getColor().equals(EBullColor.BLACK)) {
					currentColor = "Black";
				}
				if (game.getGameState().equals(EGameState.GAMEOVER)) {
					lblTopLine.setText(currentColor+" won."
							+"\nClick \"New Game\" to start a game.");
				} else {
					lblTopLine.setText(((!game.getMessage().equals(""))?("Message: "+game.getMessage()+"\n"):"")
							+currentColor+" to move."
							+"\nClick a piece to activate and click again on an empty position.");
				}
			}
		});
	}

}
