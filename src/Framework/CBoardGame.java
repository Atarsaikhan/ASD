package Framework;

import java.util.List;

public class CBoardGame {
	private EGameState gameState;
	private IGameMode gameMode;
	private CPlayer current;
	private CPlayer white;
	private CPlayer black;
	private List<APosition> positions;
	private String message;
	private int totalMove;

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

	public CBoardGame(CPlayer white, CPlayer black, boolean isCaptureGame, List<APosition> positions) {
		if (isCaptureGame)
			this.gameMode = new CCaptureMode();
		else
			this.gameMode = new CNonCaptureMode();

		this.white = white;
		this.black = black;

		restart(positions);
	}

	

	public void restart(List<APosition> positions) {
		this.gameState = EGameState.ACTIVE;
		this.message = "";
		this.positions = positions;

		this.current = white;
		white.reset();
		black.reset();
	}

	public boolean move(APosition pos1, APosition pos2) {
		if (validate(pos1, pos2)) {
			this.message = "";
			pos2.setColor(pos1.getColor());
			pos1.setColor(EBullColor.NONE);
			this.totalMove++;

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

			if (this.gameState != EGameState.GAMEOVER)
				if (current == white)
					current = black;
				else
					current = white;

			this.gameState = state;
			this.message = "";

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

			if (current == white)
				current = black;
			else
				current = white;
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

}
