package Application;

import Framework.IGameController;

public class CGameFactoryImpl implements IGameFactory {

	@Override
	public IGameController createGame(String type) {
		IGameController game = null;
		switch (type) {
		case ("FourBullsTrue"):
			game = createFourBulls(true);
			break;
		case ("FourBullsFalse"):
			game = createFourBulls(false);
			break;
		default:
			game = null;
		}
		return game;
	}

	private IGameController createFourBulls(boolean isCaptureGame) {
		IGameController game = new CFourBullsGame(isCaptureGame);
		return game;
	}

}
