package app;

import framework.ABoardGame;
import framework.IGameFactory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainWindowController {

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

	ABoardGame game;

	private Stage stage;

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@FXML
	private void initialize() {
		game = new C4BullsGame(gameCanvas.getGraphicsContext2D());

		gameCanvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println("canvas handle");
				game.handle((int) e.getX(), (int) e.getY());

			}
		});
	}

	@FXML
	void onNewGameClick(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getClassLoader().getResource("resources/DialogNewGame.fxml"));
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root);

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Start new game");
			dialogStage.setScene(scene);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(this.stage);

//		    Set the values into the controller.
			CDialogNewGame dialogController = fxmlLoader.getController();
			dialogController.setDialogStage(dialogStage);
			dialogController.setValues("White", "Black", true, 0);

			dialogStage.showAndWait();

			if (dialogController.isNewGameClicked()) {
				String gameType;
				if (dialogController.selectedGame() == 0)
					gameType = "FourBulls";
				else
					gameType = "Yurt";

				IGameFactory factory = new CGameFactoryImpl();

				if (dialogController.isCaptureGame())
					gameType += "True";
				else
					gameType += "False";
				
				this.game = factory.createGame(gameType, dialogController.getPlayerName1(),
						dialogController.getPlayerName2());

//				if (gameController != null)
//					bullScene.getDrawer().setGame(gameController);
//				else
//					throw new RuntimeException("Game type is not supported");
//				bullScene.getDrawer().setGc(getGraphicsContext());
//				bullScene.getDrawer().drawBoard();
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on the dialog window \n"
					+ getClass().getClassLoader().getResource("resources/DialogNewGame.fxml") + "\n" + e.getMessage());
		}
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
