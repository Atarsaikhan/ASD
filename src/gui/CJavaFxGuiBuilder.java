package gui;

import java.util.Optional;

import application.CGameFactoryImpl;
import application.CGameSettings;
import application.IGameFactory;
import framework.CPlayer;
import framework.EBullColor;
import framework.IGameController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CJavaFxGuiBuilder implements IGuiBuilder {
	CGameScene bullScene;
    MenuItem mniNewGame = new MenuItem("New game"); 
    MenuItem mniRestart = new MenuItem("Restart"); 
    MenuItem mniUndo = new MenuItem("Undo"); 
    MenuItem mniSettings = new MenuItem("Settings"); 
    MenuItem mniQuit = new MenuItem("Quit"); 
    
    Button btnNewGame = new Button("New game");
    Button btnRestart = new Button("Restart");
    Button btnUndo = new Button("Undo"); 
    Button btnSettings = new Button("Settings"); 
    Canvas canvas = new Canvas(600, 700);
    GraphicsContext gc = canvas.getGraphicsContext2D();
	
	CJavaFxGuiBuilder(){
		bullScene = new CGameScene(new Group());
	}
	
	public void buildPlayer(String playerName1, String playerName2) {
		bullScene.setPlayer1(new CPlayer(playerName1, 2, EBullColor.WHITE));
		bullScene.setPlayer2(new CPlayer(playerName2, 2, EBullColor.BLACK));
	}

	@Override
	public void initGameControl() {
		bullScene.setDrawer(new CDrawer());
//		IGameFactory factory = new CGameFactoryImpl();
//		//FourBullsTrue, FourBullsFalse
//		IGameController gameController = factory.createGame("FourBullsTrue");
//		if (gameController != null)
//			bullScene.setDrawer( new CDrawer(gameController) );
//		else
//			throw new RuntimeException("Game type is not defined");
	}

	@Override
	public void buildGuiControls() {
		
		// create a menu 
        Menu m = new Menu("Menu"); 
  
//        // create menuitems 
//        MenuItem mniNewGame = new MenuItem("New game"); 
//        MenuItem mniUndo = new MenuItem("Undo"); 
//        MenuItem m2 = new MenuItem("Edit"); 
//        MenuItem mniExit = new MenuItem("Exit"); 
  
        // add menu items to menu 
        m.getItems().add(mniNewGame); 
        m.getItems().add(mniRestart); 
        m.getItems().add(mniUndo); 
        m.getItems().add(mniSettings); 
        m.getItems().add(mniQuit); 
  
        // create a menubar 
        MenuBar mb = new MenuBar(); 
  
        // add menu to menubar 
        mb.getMenus().add(m); 
        
        // create a VBox 
        HBox containerBox = new HBox();
        VBox mainArea = new VBox(mb); 
        containerBox.getChildren().add(mainArea);
        VBox controlBox = new VBox(); 
        containerBox.getChildren().add(controlBox);
       
        // GAME CONTROLS
        AnchorPane controlsPane = new AnchorPane();
        controlBox.getChildren().add(controlsPane);

//        Button btnNew = new Button("New game"); 
        AnchorPane.setTopAnchor(btnNewGame, 200.0); 
        AnchorPane.setLeftAnchor(btnNewGame, 30.0); 
        AnchorPane.setRightAnchor(btnNewGame, 30.0); 
        //AnchorPane.setBottomAnchor(button, 125.0); 
        controlsPane.getChildren().add(btnNewGame);
        
//        Button btnUndo = new Button("Restart"); 
        AnchorPane.setTopAnchor(btnRestart, 240.0); 
        AnchorPane.setLeftAnchor(btnRestart, 30.0); 
        AnchorPane.setRightAnchor(btnRestart, 30.0); 
        //AnchorPane.setBottomAnchor(button, 125.0); 
        controlsPane.getChildren().add(btnRestart);
        
//        Button btnUndo = new Button("Undo"); 
        AnchorPane.setTopAnchor(btnUndo, 280.0); 
        AnchorPane.setLeftAnchor(btnUndo, 30.0); 
        AnchorPane.setRightAnchor(btnUndo, 30.0); 
        //AnchorPane.setBottomAnchor(button, 125.0); 
        controlsPane.getChildren().add(btnUndo);
        
//      Button btnSettings = new Button("Settings"); 
      AnchorPane.setTopAnchor(btnSettings, 320.0); 
      AnchorPane.setLeftAnchor(btnSettings, 30.0); 
      AnchorPane.setRightAnchor(btnSettings, 30.0); 
      //AnchorPane.setBottomAnchor(btnSettings, 125.0); 
      controlsPane.getChildren().add(btnSettings);
      
//		Canvas canvas = new Canvas(600, 700);
		mainArea.getChildren().add(canvas);

//		GraphicsContext gc = canvas.getGraphicsContext2D();

		//Group root = new Group();
		//Scene theScene = new Scene(root);
		//root.getChildren().add(containerBox);
		bullScene.root.getChildren().add(containerBox);
	}

	@Override
	public void buildHandlers() {
		canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (bullScene.getDrawer() !=null) {
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
					Scene scene = new Scene (root);
					Stage dialogStage = new Stage();
					dialogStage.setTitle("Start new game");
					dialogStage.setScene(scene);
				    dialogStage.initModality(Modality.WINDOW_MODAL);
				    dialogStage.initOwner( bullScene.getWindow() );

//				    Set the values into the controller.
			        CDialogNewGame dialogController = fxmlLoader.getController();
			        dialogController.setDialogStage(dialogStage);
			        
			        dialogController.setValues(bullScene.getPlayer1().getName(), bullScene.getPlayer2().getName()
			        		, true, 0);
				    
				    dialogStage.showAndWait();
				    
				    if (dialogController.isNewGameClicked())  {
//		              drawer.restartGame();
//		              drawer.drawPositions();
		        		IGameFactory factory = new CGameFactoryImpl();
		        		IGameController gameController;
		        		String gameType;
		        		if (dialogController.isToCapture()) {
		        			gameType = "FourBullsTrue";
		        		}
		        		else {
		        			gameType = "FourBullsFalse";
		        		}
		        		gameController = factory.createGame(gameType, dialogController.getPlayerName1(), dialogController.getPlayerName2());
	        			if (gameController != null)
	        				bullScene.getDrawer().setGame( gameController);
	        			else
	        				throw new RuntimeException("Game type is not supported");
		        		bullScene.getDrawer().setGc(gc);
		        		bullScene.getDrawer().drawBoard();
				    }
					
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Error on the dialog window \n"
							+getClass().getClassLoader().getResource("resources/DialogNewGame.fxml")+"\n"
							+e.getMessage());
				}
            	

            }
        };
        
        EventHandler<ActionEvent> restartHandler = new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent t) {
        		if (bullScene.getDrawer() !=null) {
        			bullScene.getDrawer().restartGame();
        		}
        	}
        };
		
		EventHandler<ActionEvent> undoHandler = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	if (bullScene.getDrawer() !=null) {
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

	@Override
	public Scene getGui() {
		return bullScene;
	}

}
