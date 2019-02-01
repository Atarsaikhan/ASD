package gui;

import java.util.Optional;

import application.CGameFactoryImpl;
import application.CGameSettings;
import application.IGameFactory;
import framework.IGameController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CFXMLMainController {

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
    private Canvas canvas;

    @FXML
    private Button btnNewGame;

    @FXML
    private Button btnRestart;

    @FXML
    private Button btnUndo;

    @FXML
    private Button btnSettings;
    
    CGameScene bullScene;
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	
		canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (bullScene.getDrawer() != null) {
					bullScene.getDrawer().processClick((int) e.getX(), (int) e.getY());
					bullScene.getDrawer().drawStatus();
				}
			}
		});

		EventHandler<ActionEvent> newHandler = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {

				try {
					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(getClass().getClassLoader().getResource("resources/DialogNewGame.fxml"));
					Parent root = fxmlLoader.load();
					Scene scene = new Scene(root);
					Stage dialogStage = new Stage();
					dialogStage.setTitle("Start new game");
					dialogStage.setScene(scene);
					dialogStage.initModality(Modality.WINDOW_MODAL);
					dialogStage.initOwner(bullScene.getWindow());

//				    Set the values into the controller.
					CDialogNewGame dialogController = fxmlLoader.getController();
					dialogController.setDialogStage(dialogStage);

					dialogController.setValues(bullScene.getPlayer1().getName(), bullScene.getPlayer2().getName(), true,
							0);

					dialogStage.showAndWait();

					if (dialogController.isNewGameClicked()) {
//		              drawer.restartGame();
//		              drawer.drawPositions();
						IGameFactory factory = new CGameFactoryImpl();
						IGameController gameController;
						String gameType;
						if (dialogController.isToCapture()) {
							gameType = "FourBullsTrue";
						} else {
							gameType = "FourBullsFalse";
						}
						gameController = factory.createGame(gameType, dialogController.getPlayerName1(),
								dialogController.getPlayerName2());
						if (gameController != null)
							bullScene.getDrawer().setGame(gameController);
						else
							throw new RuntimeException("Game type is not supported");
						bullScene.getDrawer().setGc(getGraphicsContext());
						bullScene.getDrawer().drawBoard();
					}

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Error on the dialog window \n"
							+ getClass().getClassLoader().getResource("resources/DialogNewGame.fxml") + "\n"
							+ e.getMessage());
				}

			}
		};

		EventHandler<ActionEvent> restartHandler = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				if (bullScene.getDrawer() != null) {
					bullScene.getDrawer().restartGame();
				}
			}
		};

		EventHandler<ActionEvent> undoHandler = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				if (bullScene.getDrawer() != null) {
					bullScene.getDrawer().undo();
				}
			}
		};

		EventHandler<ActionEvent> settingsHandler = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				CDialogSettings dialog = new CDialogSettings(bullScene.getDrawer().SETTINGS_FILE);
				Optional<CGameSettings> result = dialog.showAndWait();
				if (result.isPresent()) {
					bullScene.getDrawer().setGameSettings(result.get());
				}
			}
		};

		mniQuit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				Platform.exit();
			}
		});

		mniNewGame.setOnAction(newHandler);
		btnNewGame.setOnAction(newHandler);

		mniRestart.setOnAction(restartHandler);
		btnRestart.setOnAction(restartHandler);

		mniUndo.setOnAction(undoHandler);
		btnUndo.setOnAction(undoHandler);

		mniSettings.setOnAction(settingsHandler);
		btnSettings.setOnAction(settingsHandler);
    }
    
    public GraphicsContext getGraphicsContext() {
    	return canvas.getGraphicsContext2D();
    }

	public void setScene(CGameScene bullScene) {
		this.bullScene = bullScene;
	}

}
