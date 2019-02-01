package Application;

import Framework.IGameController;

public interface IGameFactory {
	public IGameController createGame(String type, String playerName1, String playerName2);
}
