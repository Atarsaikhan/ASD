package horn;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import application.IObserverMoveNumber;
import framework.APosition;
import framework.CBoardGame;
import framework.CPlayer;
import framework.CPositionImpl;
import framework.EBullColor;
import framework.EGameState;
import framework.GUIManager;
import framework.IGameController;
import javafx.scene.canvas.GraphicsContext;

public class HornGame implements IGameController {

	static final int P_SIZE = 50;
	private CBoardGame game;
	private GUIManager guiMan;
	private Stack<List<APosition>> moves;

	@Override
	public CPlayer getCurrent() {
		return game.getCurrent();
	}

	@Override
	public String getMessage() {
		return game.getMessage();
	}

	@Override
	public EGameState getGameState() {
		return game.getGameState();
	}

	@Override
	public List<APosition> getPositions() {
		return game.getPositions();
	}

	@Override
	public CPlayer getWhite() {
		return game.getWhite();
	}

	@Override
	public CPlayer getBlack() {
		return game.getBlack();
	}

	HornGame(GraphicsContext graphicsContext) {
		moves = new Stack<>();
		
		game = new CBoardGame(new CPlayer("Herder", 2, EBullColor.WHITE), new CPlayer("Bull", 1, EBullColor.BLACK),
				false, initPositions());
		game.setGraphicsContext(graphicsContext);
		// guiMan.setBackImage("bullBackground.jpg");
	}

	private List<APosition> initPositions() {

		List<APosition> positions = new ArrayList<>();

		APosition pos0 = new CPositionImpl(0, 180, 51, EBullColor.NONE, this);
		APosition pos1 = new CPositionImpl(1, 192, 124, EBullColor.NONE, this);
		APosition pos2 = new CPositionImpl(2, 325, 161, EBullColor.NONE, this);
		APosition pos3 = new CPositionImpl(3, 192, 223, EBullColor.NONE, this);
		APosition pos4 = new CPositionImpl(4, 348, 246, EBullColor.NONE, this);
		APosition pos5 = new CPositionImpl(5, 180, 327, EBullColor.WHITE, this);
		APosition pos6 = new CPositionImpl(6, 354, 327, EBullColor.WHITE, this);
		APosition pos7 = new CPositionImpl(7, 132, 403, EBullColor.NONE, this);
		APosition pos8 = new CPositionImpl(8, 354, 403, EBullColor.BLACK, this);
		
		pos0.addNeighbor(pos1);
		pos0.addNeighbor(pos2);

		pos1.addNeighbor(pos0);
		pos1.addNeighbor(pos2);
		pos1.addNeighbor(pos3);
		
		pos2.addNeighbor(pos0);
		pos2.addNeighbor(pos1);
		pos2.addNeighbor(pos3);
		pos2.addNeighbor(pos4);
		
		pos3.addNeighbor(pos1);
		pos3.addNeighbor(pos2);
		pos3.addNeighbor(pos4);
		pos3.addNeighbor(pos5);
		
		pos4.addNeighbor(pos2);
		pos4.addNeighbor(pos3);
		pos4.addNeighbor(pos5);
		pos4.addNeighbor(pos6);
		
		pos5.addNeighbor(pos3);
		pos5.addNeighbor(pos4);
		pos5.addNeighbor(pos6);
		pos5.addNeighbor(pos7);
		
		pos6.addNeighbor(pos4);
		pos6.addNeighbor(pos5);
		pos6.addNeighbor(pos8);
		
		pos7.addNeighbor(pos5);
		pos7.addNeighbor(pos8);
		
		pos8.addNeighbor(pos6);
		pos8.addNeighbor(pos7);
		
		positions.add(pos0);
		positions.add(pos1);
		positions.add(pos2);
		positions.add(pos3);
		positions.add(pos4);
		positions.add(pos5);
		positions.add(pos6);
		positions.add(pos7);
		positions.add(pos8);

		return positions;

	}

	public void undo() {
		if (moves.isEmpty())
			return;

		ArrayList<APosition> move = (ArrayList<APosition>) moves.pop();
		this.undoMove(move.get(0), move.get(1), game.getGameState());

		guiMan.drawPos(move.get(0), guiMan.NORMAL_STROKE_COLOR);
		guiMan.drawPos(move.get(1), guiMan.NORMAL_STROKE_COLOR);
	}

	public void eventHandler(int x, int y) {
		if (game.getGameState().equals(EGameState.GAMEOVER))
			return;

		System.out.println("x,y: " + x + ", " + y);
		game.move(findPosition(x, y));

	}

	private APosition findPosition(int x, int y) {
		for (APosition pos : game.getPositions()) {
			double distance = Math.sqrt(Math.pow(x - pos.getX(), 2) + Math.pow(y - pos.getY(), 2));
			if (distance < P_SIZE / 2) {
				return pos;
			}
		}
		return null;
	}

	public void restart() {
		game.restart(initPositions());
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
		return true;
	}

	@Override
	public boolean undoCapture(APosition pos, EGameState state) {
		return true;
	}

	@Override
	public void timeExpired() {
		//
	}

	@Override
	public void attach(IObserverMoveNumber observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void detach(IObserverMoveNumber observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub

	}
}
