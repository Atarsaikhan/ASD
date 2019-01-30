package Framework;

import java.util.List;

public interface IGameController {
	public abstract boolean move(APosition pos1, APosition pos2);
	public abstract boolean undoMove(APosition pos1, APosition pos2, EGameState state);

	public abstract boolean capture(APosition pos);
	public abstract boolean undoCapture(APosition pos, EGameState state);

	public abstract List<APosition> getPositions();

	public abstract EGameState getGameState();

	public abstract CPlayer getCurrent();

	public abstract String getMessage();

	public abstract void restart();
}
