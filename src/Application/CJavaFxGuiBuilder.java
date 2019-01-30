package Application;

import java.util.Optional;

import Framework.CFourBullsGame;
import Framework.CPlayer;
import Framework.EBullColor;
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

public class CJavaFxGuiBuilder implements IGuiBuilder {
	CGameScene bullScene;
    MenuItem mniNewGame = new MenuItem("New game"); 
    MenuItem mniUndo = new MenuItem("Undo"); 
    MenuItem mniSettings = new MenuItem("Settings"); 
    MenuItem mniExit = new MenuItem("Exit"); 
    
    Button btnNew = new Button("New game");
    Button btnUndo = new Button("Undo"); 
    Button btnSettings = new Button("Settings"); 
    Canvas canvas = new Canvas(600, 700);
    GraphicsContext gc = canvas.getGraphicsContext2D();
	
	CJavaFxGuiBuilder(){
		bullScene = new CGameScene(new Group());
	}
	
	public void buildPlayer(String playerName1, String playerName2) {
		bullScene.setPlayer1(new CPlayer(playerName1, 2, EBullColor.WHITE));
		bullScene.setPlayer2(new CPlayer(playerName1, 2, EBullColor.BLACK));
	}

	@Override
	public void initGameControl() {
		bullScene.setDrawer( new CDrawer(new CFourBullsGame(bullScene.getPlayer1(), bullScene.getPlayer2(), true)) );
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
        m.getItems().add(mniUndo); 
        m.getItems().add(mniSettings); 
        m.getItems().add(mniExit); 
  
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
        AnchorPane.setTopAnchor(btnNew, 200.0); 
        AnchorPane.setLeftAnchor(btnNew, 30.0); 
        AnchorPane.setRightAnchor(btnNew, 30.0); 
        //AnchorPane.setBottomAnchor(button, 125.0); 
        controlsPane.getChildren().add(btnNew);
        
//        Button btnUndo = new Button("Undo"); 
        AnchorPane.setTopAnchor(btnUndo, 240.0); 
        AnchorPane.setLeftAnchor(btnUndo, 30.0); 
        AnchorPane.setRightAnchor(btnUndo, 30.0); 
        //AnchorPane.setBottomAnchor(button, 125.0); 
        controlsPane.getChildren().add(btnUndo);
        
//      Button btnSettings = new Button("Settings"); 
      AnchorPane.setTopAnchor(btnSettings, 280.0); 
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
					bullScene.getDrawer().updateStatus();
				}
			}
		});
		
		EventHandler<ActionEvent> newHandler = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
//              drawer.restartGame();
//              drawer.drawPositions();
        		CPlayer player1 = new CPlayer("Player 1", 2, EBullColor.WHITE);
        		CPlayer player2 = new CPlayer("Player 2", 2, EBullColor.BLACK);
        		bullScene.setDrawer(new CDrawer(new CFourBullsGame(player1, player2, true)));
        		bullScene.getDrawer().setGc(gc);
        		bullScene.getDrawer().drawPositions();
            }
        };
		
		EventHandler<ActionEvent> undoHandler = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	if (bullScene.getDrawer() !=null) {
            		bullScene.getDrawer().undo();
            		bullScene.getDrawer().drawPositions();
                }
            }
        };
        
        EventHandler<ActionEvent> settingsHandler = new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent t) {
        		CSettingsDialog dialog = new CSettingsDialog();
            	Optional<CSettingsGame> result = dialog.showAndWait();
            	if (result.isPresent()) {
            	    bullScene.getDrawer().setGameSettings(result.get());
            	}
        	}
        };
        
        mniExit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	Platform.exit();
            }
        });

        
        mniNewGame.setOnAction(newHandler);
        btnNew.setOnAction(newHandler);

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
