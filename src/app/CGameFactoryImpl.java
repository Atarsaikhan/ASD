package app;

import framework.ABoardGame;
import framework.IGameFactory;
import javafx.scene.canvas.GraphicsContext;

public class CGameFactoryImpl implements IGameFactory {

	@Override
	public ABoardGame createGame(String type, String playerName1, String playerName2, GraphicsContext graphicsContext) {
		ABoardGame game = null;
		switch (type) {
		case ("FourBullsTrue"):
			game = createFourBulls(playerName1, playerName2, true, graphicsContext);
			break;
		case ("FourBullsFalse"):
			game = createFourBulls(playerName1, playerName2, false, graphicsContext);
			break;
		case ("YurtTrue"):
			game = createYurt(playerName1, playerName2, true, graphicsContext);
			break;
		case ("YurtFalse"):
			game = createYurt(playerName1, playerName2, false, graphicsContext);
			break;
		default:
			game = null;
		}
		return game;
	}

	private ABoardGame createYurt(String playerName1, String playerName2, boolean isCaptureGame,
			GraphicsContext graphicsContext) {

		IGameBuilder gameBuilder = new CYurtGameBuilder();
		gameBuilder.buildPlayers(playerName1, playerName2);
		gameBuilder.buildGuiManager(graphicsContext, "toono.png");
		gameBuilder.buildGameController(isCaptureGame);

		return gameBuilder.getBoardGame();
	}

	private ABoardGame createFourBulls(String playerName1, String playerName2, boolean isCaptureGame,
			GraphicsContext graphicsContext) {

		IGameBuilder gameBuilder = new C4BullsGameBuilder();
		gameBuilder.buildPlayers(playerName1, playerName2);
		gameBuilder.buildGuiManager(graphicsContext, "bullBackground.jpg");
		gameBuilder.buildGameController(isCaptureGame);

		return gameBuilder.getBoardGame();
	}
}
