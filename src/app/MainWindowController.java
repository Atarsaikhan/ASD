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
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainWindowController {
	
    @FXML
    private MenuItem mniNewGame;

    @FXML
    private MenuItem mniRestart;

    @FXML
    private MenuItem mniQuit;

    @FXML
    private MenuItem mniUndo;

    @FXML
    private MenuItem mniSettings;
    
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
		gameCanvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println("canvas handle");
				game.handle((int) e.getX(), (int) e.getY());
				btnRestart.setDisable(false);
			}
		});
		
		btnNewGame.setStyle(" -fx-background-repeat: no-repeat;"
				+ " -fx-background-position: 15px 10px;"
				+ " -fx-background-image: url('resources/icons/play.png');");
		btnRestart.setStyle(" -fx-background-repeat: no-repeat;"
				+ " -fx-background-position: 15px 10px;"
				+ " -fx-background-image: url('resources/icons/start.png');");
		btnUndo.setStyle(" -fx-background-repeat: no-repeat;"
				+ " -fx-background-position: 15px 10px;"
				+ " -fx-background-image: url('resources/icons/recycle.png');");
		btnSettings.setStyle(" -fx-background-repeat: no-repeat;"
				+ " -fx-background-position: 15px 10px;"
				+ " -fx-background-image: url('resources/icons/tools.png');");
		btnQuit.setStyle(" -fx-background-repeat: no-repeat;"
				+ " -fx-background-position: 15px 10px;"
				+ " -fx-background-image: url('resources/icons/exit.png');");
		
		btnRestart.setDisable(true);
		btnUndo.setDisable(true);
		
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
						dialogController.getPlayerName2(), gameCanvas.getGraphicsContext2D());
				
				btnRestart.setDisable(false);
				btnUndo.setDisable(false);
				
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
		btnUndo.setDisable(true);
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
