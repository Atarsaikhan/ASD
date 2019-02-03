package framework;

public interface IGameFactory {
	public ABoardGame createGame(String type, String playerName1, String playerName2);
}
