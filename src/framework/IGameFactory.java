package framework;

public interface IGameFactory {
	public IBoardGame createGame(String type, String playerName1, String playerName2);
}
