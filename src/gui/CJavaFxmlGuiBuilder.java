package gui;

import java.io.IOException;

import framework.CPlayer;
import framework.EBullColor;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

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
