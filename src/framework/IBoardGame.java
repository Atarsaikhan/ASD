package framework;

public interface IBoardGame {
	public void handle(int x, int y);

	public void undo();

	public boolean move(APosition pos1, APosition pos2);

	public boolean capture(APosition pos);

	public void timeExpired();

	public void restart();

	public EGameState getGameState();

	public CPlayer getCurrent();

	public String getMessage();

}
