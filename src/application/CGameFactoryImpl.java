package application;

import framework.CPlayer;
import framework.EBullColor;
import framework.IGameController;

public class CGameFactoryImpl implements IGameFactory {

	@Override
	public IGameController createGame(String type, String playerName1, String playerName2) {
		IGameController game = null;
		switch (type) {
		case ("FourBullsTrue"):
			game = createFourBulls(playerName1, playerName2, true);
			break;
		case ("FourBullsFalse"):
			game = createFourBulls(playerName1, playerName2, false);
			break;
		default:
			game = null;
		}
		return game;
	}

	private IGameController createFourBulls(String playerName1, String playerName2, boolean isCaptureGame) {
    	CPlayer player1 = new CPlayer(playerName1, 2, EBullColor.WHITE);
		CPlayer player2 = new CPlayer(playerName2, 2, EBullColor.BLACK);
		IGameController game = new CFourBullsGame(player1, player2, isCaptureGame);
		return game;
	}
}
