package framework;

import javafx.scene.canvas.GraphicsContext;

public interface IGameFactory {
	public ABoardGame createGame(String type, String playerName1, String playerName2, GraphicsContext graphicsContext);
}
