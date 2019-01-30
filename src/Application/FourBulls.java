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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FourBulls extends Application {
	Drawer drawer;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage theStage) {
		theStage.setTitle("4 bulls");
		
        // create a menu 
        Menu m = new Menu("Menu"); 
  
        // create menuitems 
        MenuItem mniNewGame = new MenuItem("New game"); 
        MenuItem mniUndo = new MenuItem("Undo"); 
        MenuItem m2 = new MenuItem("Edit"); 
        MenuItem mniExit = new MenuItem("Exit"); 
  
        // add menu items to menu 
        m.getItems().add(mniNewGame); 
        m.getItems().add(mniUndo); 
        m.getItems().add(m2); 
        m.getItems().add(mniExit); 
  
        // create a menubar 
        MenuBar mb = new MenuBar(); 
  
        // add menu to menubar 
        mb.getMenus().add(m); 
        
        // create a VBox 
        VBox topBar = new VBox(mb); 
        VBox containerBox = new VBox(mb); 
        
		Group root = new Group();
		Scene theScene = new Scene(root);
		
		root.getChildren().add(topBar);
		root.getChildren().add(containerBox);
		
		theStage.setScene(theScene);
		
		Canvas canvas = new Canvas(700, 700);
//		root.getChildren().add(canvas);
		containerBox.getChildren().add(canvas);

		GraphicsContext gc = canvas.getGraphicsContext2D();

		// gc.drawImage(positions.get(0).getImage(), positions.get(0).getX(),
		// positions.get(0).getY());

		
        mniNewGame.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
        		Player player1 = new Player("Player 1", 2, BullColor.WHITE);
        		Player player2 = new Player("Player 2", 2, BullColor.BLACK);
        		drawer = new Drawer(gc, new FourBullsGame(player1, player2, true));
        		drawer.drawPositions();
            	
                drawer.restartGame();
                drawer.drawPositions();
            }
        });
        
        mniUndo.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                drawer.undo();
            }
        });
        
        mniExit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	Platform.exit();
            }
        });
		
		canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				drawer.processClick((int) e.getX(), (int) e.getY());
				drawer.updateStatus();
			}
		});

		theStage.show();
	}
}
