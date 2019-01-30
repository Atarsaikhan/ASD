package Framework;

import java.util.List;

public interface GameController {
	public abstract boolean move(Position pos1, Position pos2);
	public abstract boolean undoMove(Position pos1, Position pos2);

	public abstract boolean move(int pos1, int pos2);
	public abstract boolean undoMove(int pos1, int pos2);

	public abstract boolean validate(Position pos1, Position pos2);

	public abstract boolean capture(Position pos);

	public abstract void check();

	public abstract List<Position> getPositions();

	public abstract GameState getGameState();

	public abstract Player getCurrent();

	public abstract String getMessage();

	public abstract void restart();
}
