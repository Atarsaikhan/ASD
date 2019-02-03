package app;

import framework.ABoardGame;
import javafx.scene.canvas.GraphicsContext;

public interface IGameBuilder {
	public void buildPlayers(String name1, String name2);
	public void buildGameController(boolean isCaptureGame);
	public void buildGuiManager(GraphicsContext graphicsContext, String backImage);
	public ABoardGame getBoardGame();
}
