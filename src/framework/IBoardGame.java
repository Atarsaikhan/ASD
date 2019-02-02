package framework;

public interface IBoardGame {
	public void handle(int x, int y);

	public void undo();

	public boolean move(APosition pos1, APosition pos2);

	public boolean undoMove(APosition pos1, APosition pos2, EGameState state);

	public boolean capture(APosition pos);

	public boolean undoCapture(APosition pos, EGameState state);

	public void activate(APosition pos1, APosition pos2);

	public void timeExpired();

	public void restart();

	public abstract EGameState getGameState();

	public abstract CPlayer getCurrent();

	public abstract String getMessage();

}
