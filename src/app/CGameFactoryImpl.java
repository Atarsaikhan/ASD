package app;

import framework.CPlayer;
import framework.EBullColor;
import framework.IBoardGame;
import framework.IGameFactory;

public class CGameFactoryImpl implements IGameFactory {

	@Override
	public IBoardGame createGame(String type, String playerName1, String playerName2) {
		IBoardGame game = null;
		switch (type) {
		case ("FourBullsTrue"):
			game = createFourBulls(playerName1, playerName2, true);
			break;
		case ("FourBullsFalse"):
			game = createFourBulls(playerName1, playerName2, false);
			break;
		case ("YurtTrue"):
			game = createYurt(playerName1, playerName2, true);
			break;
		case ("YurtFalse"):
			game = createYurt(playerName1, playerName2, false);
			break;
		default:
			game = null;
		}
		return game;
	}

	private IBoardGame createYurt(String playerName1, String playerName2, boolean b) {
		// TODO Auto-generated method stub
		return null;
	}

	private IBoardGame createFourBulls(String playerName1, String playerName2, boolean isCaptureGame) {
    	CPlayer player1 = new CPlayer(playerName1, 2, EBullColor.WHITE);
		CPlayer player2 = new CPlayer(playerName2, 2, EBullColor.BLACK);
		IBoardGame game = null;// new CFourBullsGame( player1, player2, isCaptureGame);
		return game;
	}
}
