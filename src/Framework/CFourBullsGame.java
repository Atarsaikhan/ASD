package Framework;

import java.util.ArrayList;
import java.util.List;

public class CFourBullsGame implements IGameController {
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

	public CPlayer getCurrent() {
		return current;
	}

	public String getMessage() {
		return message;
	}

	public EGameState getGameState() {
		return gameState;
	}

	public List<APosition> getPositions() {
		return positions;
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
		if (isCaptureGame)
			this.gameMode = new CCaptureMode();
		else
			this.gameMode = new CNonCaptureMode();

		this.white = white;
		this.black = black;

		initPositions();
		restart();
	}

	private void initPositions() {

		positions = new ArrayList<>();
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

	}

	public void restart() {
		this.gameState = EGameState.ACTIVE;
		this.message = "";

		this.current = white;
		white.reset();
		black.reset();

		positions.get(0).setColor(EBullColor.WHITE);
		positions.get(1).setColor(EBullColor.BLACK);
		positions.get(2).setColor(EBullColor.NONE);
		positions.get(3).setColor(EBullColor.BLACK);
		positions.get(4).setColor(EBullColor.WHITE);

		printBoard();
	}

	private void printBoard() {

		System.out.println(" " + positions.get(0).getColor().value() + " - - - " + positions.get(1).getColor().value());
		System.out.println(" | \\   / ");
		System.out.println(" |   " + positions.get(2).getColor().value());
		System.out.println(" | /   \\ ");
		System.out.println(" " + positions.get(3).getColor().value() + " - - - " + positions.get(4).getColor().value());

		System.out.println(current.getColor().toString() + " to move, insert positions: ");
	}

	@Override
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

			changeState();

			if (this.gameState == EGameState.GAMEOVER) // set Winner back to current
				if (current == white)
					current = black;
				else
					current = white;

			printBoard();
			return true;
		}
		message = "Move not allowed!";
		System.out.println(message);
		return false;
	}

	@Override
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

			System.out.println(current.getColor().toString());

			printBoard();
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

	@Override
	public boolean capture(APosition pos) {
		if (pos.getColor().equals(current.getColor())) {
			pos.setColor(EBullColor.NONE);
			current.decPieces();
			this.message = "";
			this.gameState = EGameState.ACTIVE;

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

	@Override
	public boolean undoCapture(APosition pos, EGameState state) {
		if (pos.getColor().equals(EBullColor.NONE)) {
			if (current == white)
				current = black;
			else
				current = white;
			pos.setColor(current.getColor());
			current.incPieces();
			this.message = "";
			this.gameState = state;

			return true;
		} else {
			this.message = "Move not allowed";
			return false;
		}
	}

	@Override
	public void timeExpire() {
		this.message = "Time is espired!";
		this.gameState = EGameState.GAMEOVER;

		if (current == white)
			current = black;
		else
			current = white;
		
	}
}
