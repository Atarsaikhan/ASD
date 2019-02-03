package framework;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CBoardGameController implements IGameMediator {
	private EGameState gameState;
	private IGameMode gameMode;
	private CGuiManager guiManager;
	private CPlayer current;
	private CPlayer white;
	private CPlayer black;
	private List<APosition> positions;
	private String message;
	private int totalMove;

	public CBoardGameController() {
		observers = new ArrayList<IGameObserver>();
	}

	public CPlayer getWhite() {
		return white;
	}

	public void setWhite(CPlayer white) {
		this.white = white;
	}

	public CPlayer getBlack() {
		return black;
	}

	public void setBlack(CPlayer black) {
		this.black = black;
	}

	public int getTotalMove() {
		return totalMove;
	}

	public void setTotalMove(int totalMove) {
		this.totalMove = totalMove;
	}

	public CPlayer getCurrent() {
		return current;
	}

	public void setCurrent(CPlayer current) {
		this.current = current;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public EGameState getGameState() {
		return gameState;
	}

	public void setGameState(EGameState gameState) {
		this.gameState = gameState;
	}

	public List<APosition> getPositions() {
		return positions;
	}

	public void setPositions(List<APosition> positions) {
		this.positions = positions;
	}

	public void setGraphicsContext(GraphicsContext graphicsContext) {
		this.guiManager.setGraphicsContext(graphicsContext);
	}

	public void restart(List<APosition> positions) {
		this.gameState = EGameState.ACTIVE;
		this.message = "";
		this.positions = positions;
		this.totalMove = 0;

		this.current = white;
		white.reset();
		black.reset();

		guiManager.drawBoard(positions);
	}

	public void setPlayers(CPlayer white, CPlayer black) {
		this.white = white;
		this.black = black;
	}

	public void setGUIManager(GraphicsContext graphicsContext, String backImage) {
		guiManager = new CGuiManager();
		guiManager.setGraphicsContext(graphicsContext);
		guiManager.setBackImage(backImage);
	}

	public void setObjectProperties(int pawnSize, int lineWidth, Color fillColor, Color baseColor, Color normalStroke,
			Color movableStroke, Color activeColor) {
		guiManager.P_SIZE = pawnSize;
		guiManager.LINE_WIDTH = lineWidth;
		guiManager.FILL_COLOR = fillColor;
		guiManager.BASE_COLOR = baseColor;
		guiManager.NORMAL_STROKE_COLOR = normalStroke;
		guiManager.MOVABLE_STROKE_COLOR = movableStroke;
		guiManager.ACTIVE_STROKE_COLOR = activeColor;
	}

	public void startGame(boolean isCaptureGame, List<APosition> positions) {
		if (isCaptureGame)
			this.gameMode = new CCaptureMode();
		else
			this.gameMode = new CNonCaptureMode();

		this.gameState = EGameState.ACTIVE;
		this.message = "";
		this.positions = positions;
		this.totalMove = 0;

		this.current = white;
		white.reset();
		black.reset();

		guiManager.drawBoard(positions);
	}

	public void activate(APosition pos1, APosition pos2) {
		guiManager.drawPos(pos1, guiManager.ACTIVE_STROKE_COLOR);
		guiManager.drawPos(pos2, guiManager.MOVABLE_STROKE_COLOR);
	}

	public boolean move(APosition pos1, APosition pos2) {
		if (validate(pos1, pos2)) {
			this.message = "";
			pos2.setColor(pos1.getColor());
			pos1.setColor(EBullColor.NONE);
			this.totalMove++;
			this.notifyObservers();

			if (current == white)
				current = black;
			else
				current = white;

			this.changeState();

			if (this.gameState == EGameState.GAMEOVER) // set Winner back to current
				if (current == white)
					current = black;
				else
					current = white;

			guiManager.drawPositions(positions);

			return true;
		}
		message = "Move not allowed!";
		return false;
	}

	public boolean undoMove(APosition pos1, APosition pos2, EGameState state) {
		if (pos1.getColor().equals(EBullColor.NONE)) {
			pos1.setColor(pos2.getColor());
			pos2.setColor(EBullColor.NONE);
			this.totalMove--;
			this.notifyObservers();

			if (this.gameState != EGameState.GAMEOVER)
				if (current == white)
					current = black;
				else
					current = white;

			this.gameState = state;
			this.message = "";

			guiManager.drawPositions(positions);

			return true;
		}
		message = "Undo not allowed!";
		System.out.println(message);
		return false;
	}

	public boolean validate(APosition pos1, APosition pos2) {
		if (pos2.getColor() != EBullColor.NONE || pos1.getColor() != current.getColor())
			return false;
		else {
			return pos1.isNeighbor(pos2);
		}
	}

	public void changeState() {
		this.gameState = this.gameMode.changeState(positions, current);
		this.message = this.gameMode.getMessage();
	}

	public boolean capture(APosition pos) {
		if (pos.getColor().equals(current.getColor())) {
			pos.setColor(EBullColor.NONE);
			current.decPieces();
			this.message = "";
			this.gameState = EGameState.ACTIVE;

			this.totalMove++;
			this.notifyObservers();

			if (current == white)
				current = black;
			else
				current = white;

			guiManager.drawPositions(positions);

			return true;
		} else {
			this.message = "Move not allowed";
			return false;
		}
	}

	public boolean undoCapture(APosition pos, EGameState state) {
		if (pos.getColor().equals(EBullColor.NONE)) {
			if (current == white)
				current = black;
			else
				current = white;
			pos.setColor(current.getColor());

			this.message = "";
			this.gameState = state;

			this.current.incPieces();
			this.totalMove--;
			this.notifyObservers();

			guiManager.drawPositions(positions);

			return true;
		} else {
			this.message = "Move not allowed";
			return false;
		}
	}

	public void timeExpired() {
		this.message = "Time is expired!";
		this.gameState = EGameState.GAMEOVER;

		if (current == white)
			current = black;
		else
			current = white;
	}

	// Observer methods
	private List<IGameObserver> observers;
	private final Object MUTEX = new Object();

	public void attach(IGameObserver observer) {
		synchronized (MUTEX) {
			if (!observers.contains(observer))
				observers.add(observer);
		}
	}

	public void detach(IGameObserver observer) {
		synchronized (MUTEX) {
			int i = observers.indexOf(observer);
			if (i >= 0)
				observers.remove(i);
		}
	}

	public void notifyObservers() {
		synchronized (MUTEX) {
			for (IGameObserver observer : observers) {
				observer.update(this.totalMove);
			}
		}

	}

}
