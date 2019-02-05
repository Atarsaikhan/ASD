package framework;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public abstract class ABoardGame {

	private final int P_SIZE = 70;
	protected CBoardGameController gameController;
	protected APosition active;
	protected Stack<ICommand> moves;
	protected int blackStones = 2;
	protected int whiteStones = 2;

	protected ABoardGame() {
		moves = new Stack<>();
		active = null;
	}

	protected abstract List<APosition> initPositions();

	public void undo() {
		if (moves.isEmpty()) {
			System.out.println("empty");
			return;
		}

		ICommand cmd = moves.pop();
		cmd.undo();
	}

	public void handle(int x, int y) {

		if (gameController.getGameState().equals(EGameState.GAMEOVER)) {
			System.out.println(this.getMessage() + " - " + this.getGameState());
			return;
		}

		APosition pos = findPosition(x, y);
		if (pos != null)
			if (gameController.getGameState().equals(EGameState.NOMOVE)) {
				if (pos.isCurrent())
					this.capture(pos);
			} else {
				if (active != null) {
					if (pos.isEmpty()) {
						this.move(active, pos);
						active = null;
						System.out.println(this.getMessage() + " " + this.getGameState());
					} else if (pos.isMovable()) {
						pos.activate(active);
						active = pos;
					}
				} else if (pos.isMovable()) {
					active = pos;
					pos.activate(null);
				}
			}

	}

	protected APosition findPosition(int x, int y) {
		for (APosition pos : gameController.getPositions()) {
			double distance = Math.sqrt(Math.pow(x - pos.getX(), 2) + Math.pow(y - pos.getY(), 2));
			if (distance < P_SIZE / 2) {
				return pos;
			}
		}
		return null;
	}

	public void restart() {
		gameController.restart(initPositions());
	}

	public boolean move(APosition pos1, APosition pos2) {
		ICommand cmd = new CCmdMove(pos1, pos2);
		boolean ret = cmd.execute();
		if (ret) {
			moves.push(cmd);
		}
		return ret;
	}

	public boolean capture(APosition pos) {
		ICommand cmd = new CCmdCapture(pos);
		boolean ret = cmd.execute();
		if (ret) {
			moves.push(cmd);
		}
		return ret;
	}

	public void timeExpired() {
		this.gameController.timeExpired();
	}

	public EGameState getGameState() {
		return this.gameController.getGameState();
	}

	public CPlayer getCurrent() {
		return this.gameController.getCurrent();
	}

	public String getMessage() {
		// TODO Auto-generated method stub
		return this.gameController.getMessage();
	}

	public void attach(IGameObserver observer) {
		this.gameController.attach(observer);
	}

	public void detach(IGameObserver observer) {
		this.gameController.detach(observer);
	}

	public CMemento takeSnapshot() {
		return this.gameController.takeSnapshot();
	}

	public void restore(CMemento snapshot) {
		this.gameController.restore(snapshot);
	}
}
