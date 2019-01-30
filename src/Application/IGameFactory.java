package Application;

import Framework.IGameController;

public interface IGameFactory {
	public IGameController createGame(String type);
}
