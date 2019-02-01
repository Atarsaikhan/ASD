package Framework;

import java.util.List;

import Application.IObserverMoveNumber;

public interface IGameController {
	public abstract boolean move(APosition pos1, APosition pos2);
	public abstract boolean undoMove(APosition pos1, APosition pos2, EGameState state);

	public abstract boolean capture(APosition pos);
	public abstract boolean undoCapture(APosition pos, EGameState state);
	
	public abstract void timeExpired();
	public abstract void restart();

	public abstract List<APosition> getPositions();

	public abstract EGameState getGameState();

	public abstract CPlayer getCurrent();

	public abstract String getMessage();
	
	//Observer methods
	public abstract void attach(IObserverMoveNumber observer);
	public void detach(IObserverMoveNumber observer);
	public void notifyObservers();
	CPlayer getWhite();
	CPlayer getBlack();
}
