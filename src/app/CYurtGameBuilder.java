package app;

import framework.ABoardGame;
import framework.CPlayer;
import framework.EBullColor;
import javafx.scene.canvas.GraphicsContext;

public class CYurtGameBuilder  implements IGameBuilder {
	CYurtGame game;

	CYurtGameBuilder() {
		game = new CYurtGame();
	}

	@Override
	public void buildPlayers(String name1, String name2) {
		game.setPlayers(new CPlayer(name1, 2, EBullColor.WHITE), new CPlayer(name2, 2, EBullColor.BLACK));
	}

	@Override
	public void buildGuiManager(GraphicsContext graphicsContext, String backImage) {
		game.setGUIManager(graphicsContext, backImage);
	}

	@Override
	public void buildGameController(boolean isCaptureGame) {
		game.startGame(isCaptureGame);
	}

	@Override
	public ABoardGame getBoardGame() {
		return game;
	}

}