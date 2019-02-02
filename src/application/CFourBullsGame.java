package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import framework.APosition;
import framework.CBoardGameController;
import framework.CPlayer;
import framework.CPositionImpl;
import framework.EBullColor;
import framework.EGameState;
import framework.IBoardGame;
import framework.IGameController;
import javafx.scene.canvas.GraphicsContext;

public class CFourBullsGame implements IBoardGame {

	private CBoardGameController gameController;
	private APosition active;
	private Stack<List<APosition>> moves;
	private List<IObserverMoveNumber> observers;
	private final Object MUTEX = new Object();

	public int getTotalMove() {
		return gameController.getTotalMove();
	}

	@Override
	public CPlayer getCurrent() {
		return gameController.getCurrent();
	}

	@Override
	public String getMessage() {
		return gameController.getMessage();
	}

	@Override
	public EGameState getGameState() {
		return gameController.getGameState();
	}

	public CFourBullsGame(GraphicsContext graphicsContext, CPlayer white, CPlayer black, boolean isCaptureGame) {
		observers = new ArrayList<>();
		moves = new Stack<>();
		active = null;

		gameController = new CBoardGameController();
		gameController.setPlayers(new CPlayer("Bull", 1, EBullColor.WHITE), new CPlayer("Cowboy", 2, EBullColor.BLACK));
		gameController.setGUIManager(graphicsContext, "bullhorn2.png");
		gameController.startGame(false, initPositions());
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
		gameController.restart(initPositions());
	}

	@Override
	public boolean move(APosition pos1, APosition pos2) {
		boolean ret = gameController.move(pos1, pos2);
		if (ret)
			this.notifyObservers();
		return ret;
	}

	@Override
	public boolean undoMove(APosition pos1, APosition pos2, EGameState state) {
		boolean ret = gameController.undoMove(pos1, pos2, state);
		if (ret)
			this.notifyObservers();
		return ret;
	}

	@Override
	public boolean capture(APosition pos) {
		boolean ret = gameController.capture(pos);
		if (ret)
			this.notifyObservers();
		return ret;
	}

	@Override
	public boolean undoCapture(APosition pos, EGameState state) {
		boolean ret = gameController.undoCapture(pos, state);
		if (ret)
			this.notifyObservers();
		return ret;
	}

	@Override
	public void timeExpired() {
		gameController.timeExpired();

	}

	// Observer methods
	public void attach(IObserverMoveNumber observer) {
		synchronized (MUTEX) {
			if (!observers.contains(observer))
				observers.add(observer);
		}
	}

	public void detach(IObserverMoveNumber observer) {
		synchronized (MUTEX) {
			int i = observers.indexOf(observer);
			if (i >= 0)
				observers.remove(i);
		}
	}

	public void notifyObservers() {
		synchronized (MUTEX) {
			for (IObserverMoveNumber observer : observers) {
				observer.update(this.getTotalMove());
			}
		}

	}

	@Override
	public void activate(APosition pos1, APosition pos2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handle(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

}
