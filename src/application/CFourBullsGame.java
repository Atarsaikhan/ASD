package application;

import java.util.ArrayList;
import java.util.List;

import framework.APosition;
import framework.CBoardGame;
import framework.CPlayer;
import framework.CPositionImpl;
import framework.EBullColor;
import framework.EGameState;
import framework.IGameController;

public class CFourBullsGame implements IGameController {

	private CBoardGame game;
	private List<IObserverMoveNumber> observers;
	private final Object MUTEX = new Object();

	public int getTotalMove() {
		return game.getTotalMove();
	}

	public CPlayer getCurrent() {
		return game.getCurrent();
	}

	public String getMessage() {
		return game.getMessage();
	}

	public EGameState getGameState() {
		return game.getGameState();
	}

	public List<APosition> getPositions() {
		return game.getPositions();
	}

	public CFourBullsGame(boolean isCaptureGame) {
		this(new CPlayer("Player1", 2, EBullColor.WHITE), new CPlayer("Player2", 2, EBullColor.BLACK), isCaptureGame);
	}

	public CFourBullsGame() {
		this(new CPlayer("Player1", 2, EBullColor.WHITE), new CPlayer("Player2", 2, EBullColor.BLACK), false);
	}

	public CFourBullsGame(CPlayer white, CPlayer black) {
		this(white, black, false);
	}

	public CFourBullsGame(CPlayer white, CPlayer black, boolean isCaptureGame) {
		observers = new ArrayList<>();
		game = new CBoardGame(white, black, isCaptureGame, initPositions());
	}

	private List<APosition> initPositions() {

		List<APosition> positions = new ArrayList<>();

		APosition pos0 = new CPositionImpl(0, 100, 100, EBullColor.WHITE, this);
		APosition pos1 = new CPositionImpl(1, 500, 100, EBullColor.BLACK, this);
		APosition pos2 = new CPositionImpl(2, 300, 300, EBullColor.NONE, this);
		APosition pos3 = new CPositionImpl(3, 100, 500, EBullColor.BLACK, this);
		APosition pos4 = new CPositionImpl(4, 500, 500, EBullColor.WHITE, this);

		pos0.addNeighbor(pos1);
		pos0.addNeighbor(pos2);
		pos0.addNeighbor(pos3);

		pos1.addNeighbor(pos0);
		pos1.addNeighbor(pos2);

		pos2.addNeighbor(pos0);
		pos2.addNeighbor(pos1);
		pos2.addNeighbor(pos3);
		pos2.addNeighbor(pos4);

		pos3.addNeighbor(pos0);
		pos3.addNeighbor(pos2);
		pos3.addNeighbor(pos4);

		pos4.addNeighbor(pos2);
		pos4.addNeighbor(pos3);

		positions.add(pos0);
		positions.add(pos1);
		positions.add(pos2);
		positions.add(pos3);
		positions.add(pos4);

		return positions;

	}

	public void restart() {
		game.restart(initPositions());
	}

	private void printBoard(List<APosition> positions) {

		System.out.println(" " + positions.get(0).getColor().value() + " - - - " + positions.get(1).getColor().value());
		System.out.println(" | \\   / ");
		System.out.println(" |   " + positions.get(2).getColor().value());
		System.out.println(" | /   \\ ");
		System.out.println(" " + positions.get(3).getColor().value() + " - - - " + positions.get(4).getColor().value());

		System.out.println(this.getCurrent().getColor().toString() + " to move, insert positions: ");
	}

	@Override
	public boolean move(APosition pos1, APosition pos2) {
		boolean ret = game.move(pos1, pos2);
		if (ret)
			this.notifyObservers();
		return ret;
	}

	@Override
	public boolean undoMove(APosition pos1, APosition pos2, EGameState state) {
		boolean ret = game.undoMove(pos1, pos2, state);
		if (ret)
			this.notifyObservers();
		return ret;
	}

	@Override
	public boolean capture(APosition pos) {
		boolean ret = game.capture(pos);
		if (ret)
			this.notifyObservers();
		return ret;
	}

	@Override
	public boolean undoCapture(APosition pos, EGameState state) {
		boolean ret = game.undoCapture(pos, state);
		if (ret)
			this.notifyObservers();
		return ret;
	}

	@Override
	public void timeExpired() {
		game.timeExpired();

	}

	// Observer methods
	@Override
	public void attach(IObserverMoveNumber observer) {
		synchronized (MUTEX) {
			if (!observers.contains(observer))
				observers.add(observer);
		}
	}

	@Override
	public void detach(IObserverMoveNumber observer) {
		synchronized (MUTEX) {
			int i = observers.indexOf(observer);
			if (i >= 0)
				observers.remove(i);
		}
	}

	@Override
	public void notifyObservers() {
		synchronized (MUTEX) {
			for (IObserverMoveNumber observer : observers) {
				observer.update(this.getTotalMove());
			}
		}

	}

	@Override
	public CPlayer getWhite() {
		return game.getWhite();
	}

	@Override
	public CPlayer getBlack() {
		return game.getBlack();
	}
}
