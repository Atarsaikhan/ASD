package gui;

import java.io.IOException;
import java.util.Optional;

import application.CFourBullsGame;
import application.CGameFactoryImpl;
import application.CGameSettings;
import application.CObserverTime;
import application.IGameFactory;
import framework.CPlayer;
import framework.EBullColor;
import framework.IGameController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
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

public class CJavaFxmlGuiBuilder implements IGuiBuilder {
	private CGameScene bullScene;
	private GraphicsContext gc;
	private FXMLLoader fxmlLoader;
	private CFXMLMainController mainWindowController;

	CJavaFxmlGuiBuilder() {
		try {
			fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getClassLoader().getResource("resources/FXMLMain.fxml"));
			Group root = fxmlLoader.load();
			bullScene = new CGameScene(root);
			mainWindowController = ((CFXMLMainController) fxmlLoader.getController());
			mainWindowController.setScene(bullScene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void buildPlayer(String playerName1, String playerName2) {
		bullScene.setPlayer1(new CPlayer(playerName1, 2, EBullColor.WHITE));
		bullScene.setPlayer2(new CPlayer(playerName2, 2, EBullColor.BLACK));
	}

	@Override
	public void initGameControl() {
		bullScene.setDrawer(new CDrawer());
	}

	@Override
	public void buildGuiControls() {
		gc = mainWindowController.getGraphicsContext();
	}

	@Override
	public void buildHandlers() {

	}

	@Override
	public Scene getGui() {
		return bullScene;
	}

}
