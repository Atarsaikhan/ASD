package framework;

import java.util.List;

import application.IObserverMoveNumber;

public interface IGameController {
	public abstract boolean move(APosition pos1, APosition pos2);

	public abstract boolean undoMove(APosition pos1, APosition pos2, EGameState state);

	public abstract boolean capture(APosition pos);

	public abstract boolean undoCapture(APosition pos, EGameState state);

	public abstract void activate(APosition pos1, APosition pos2);

	public abstract void timeExpired();

	public abstract void restart();

	public abstract List<APosition> getPositions();

	public abstract EGameState getGameState();

	public abstract CPlayer getCurrent();

	public abstract String getMessage();

	public abstract CPlayer getWhite();

	public abstract CPlayer getBlack();

	// Observer methods
	public abstract void attach(IObserverMoveNumber observer);

	public void detach(IObserverMoveNumber observer);

	public void notifyObservers();
}
