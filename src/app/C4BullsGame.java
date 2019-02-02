package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import framework.APosition;
import framework.CBoardGameController;
import framework.CPlayer;
import framework.CPositionImpl;
import framework.EBullColor;
import framework.EGameState;
import framework.IBoardGame;
import javafx.scene.canvas.GraphicsContext;

public class C4BullsGame  implements IBoardGame {

	static final int P_SIZE = 50;
	private CBoardGameController gameController;
	private APosition active;
	private Stack<List<APosition>> moves;

	C4BullsGame(GraphicsContext graphicsContext) {
		moves = new Stack<>();
		active = null;

		gameController = new CBoardGameController();
		gameController.setPlayers(new CPlayer("Bull", 1, EBullColor.WHITE), new CPlayer("Cowboy", 2, EBullColor.BLACK));
		gameController.setGUIManager(graphicsContext, "bullBackground.jpg");
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

	public void undo() {
		System.out.println("undo");
		if (moves.isEmpty()) {
			System.out.println("empty");
			return;
		}

		ArrayList<APosition> move = (ArrayList<APosition>) moves.pop();
		this.undoMove(move.get(0), move.get(1), gameController.getGameState());

	}

	public void handle(int x, int y) {

		if (gameController.getGameState().equals(EGameState.GAMEOVER))
			return;

		System.out.println("x,y: " + x + ", " + y);
		APosition pos = findPosition(x, y);
		if (pos != null) {
			if (active != null) {
				if (pos.isEmpty()) {
					active.move(pos);
					active = null;
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

	private APosition findPosition(int x, int y) {
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

	@Override
	public boolean move(APosition pos1, APosition pos2) {
		boolean ret = gameController.move(pos1, pos2);
		if (ret) {
			moves.push(new ArrayList<APosition>(Arrays.asList(pos1, pos2)));
			System.out.println("push");
		}
		return ret;
	}

	@Override
	public boolean undoMove(APosition pos1, APosition pos2, EGameState state) {
		boolean ret = gameController.undoMove(pos1, pos2, state);
		return ret;
	}

	@Override
	public void activate(APosition pos1, APosition pos2) {
		gameController.activate(pos1, pos2);
	}

	@Override
	public boolean capture(APosition pos) {
		return true;
	}

	@Override
	public boolean undoCapture(APosition pos, EGameState state) {
		return true;
	}

	@Override
	public void timeExpired() {
		// TODO Auto-generated method stub

	}

	@Override
	public EGameState getGameState() {
		return this.gameController.getGameState();
	}

	@Override
	public CPlayer getCurrent() {
		return this.gameController.getCurrent();
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return this.gameController.getMessage();
	}

}