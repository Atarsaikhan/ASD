package Framework;

import java.util.ArrayList;
import java.util.List;

public class FourBullsGame implements GameController {
	private GameState gameState;
	private boolean isCaptureGame;
	private Player current;
	private Player white;
	private Player black;
	private List<Position> positions;
	private String message;
	private int totalMove;

	public boolean isCaptureGame() {
		return isCaptureGame;
	}

	public int getTotalMove() {
		return totalMove;
	}

	public Player getCurrent() {
		return current;
	}

	public String getMessage() {
		return message;
	}

	public GameState getGameState() {
		return gameState;
	}

	public List<Position> getPositions() {
		return positions;
	}

	public FourBullsGame(boolean isCaptureGame) {
		this(new Player("Player1", 2, BullColor.WHITE), new Player("Player2", 2, BullColor.BLACK), isCaptureGame);
	}

	public FourBullsGame() {
		this(new Player("Player1", 2, BullColor.WHITE), new Player("Player2", 2, BullColor.BLACK), false);
	}

	public FourBullsGame(Player white, Player black) {
		this(white, black, false);
	}

	public FourBullsGame(Player white, Player black, boolean isCaptureGame) {
		this.isCaptureGame = isCaptureGame;
		this.white = white;
		this.black = black;

		initPositions();
		restart();
	}

	private void initPositions() {

		positions = new ArrayList<>();
		Position pos0 = new PositionImpl(0, 100, 100, BullColor.WHITE);
		Position pos1 = new PositionImpl(1, 500, 100, BullColor.BLACK);
		Position pos2 = new PositionImpl(2, 300, 300, BullColor.NONE);
		Position pos3 = new PositionImpl(3, 100, 500, BullColor.BLACK);
		Position pos4 = new PositionImpl(4, 500, 500, BullColor.WHITE);

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
		this.gameState = GameState.ACTIVE;
		this.message = "";

		this.current = white;
		white.reset();
		black.reset();

		positions.get(0).setColor(BullColor.WHITE);
		positions.get(1).setColor(BullColor.BLACK);
		positions.get(2).setColor(BullColor.NONE);
		positions.get(3).setColor(BullColor.BLACK);
		positions.get(4).setColor(BullColor.WHITE);

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
	public boolean move(int pos1, int pos2) {
		return move(positions.get(pos1), positions.get(pos2));
	}

	@Override
	public boolean move(Position pos1, Position pos2) {
		if (validate(pos1, pos2)) {
			this.message = "";
			pos2.setColor(pos1.getColor());
			pos1.setColor(BullColor.NONE);
			this.totalMove++;

			if (current == white)
				current = black;
			else
				current = white;

			check();

			if (this.gameState == GameState.GAMEOVER) // set Winner back to current
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
	public boolean undoMove(int pos1, int pos2) {
		return undoMove(positions.get(pos1), positions.get(pos2));
	}

	@Override
	public boolean undoMove(Position pos1, Position pos2) {
		if (pos1.getColor().equals(BullColor.NONE)) {
			this.gameState = GameState.ACTIVE;
			this.message = "";
			pos1.setColor(pos2.getColor());
			pos2.setColor(BullColor.NONE);
			this.totalMove--;

			if (current == white)
				current = black;
			else
				current = white;

			printBoard();
			return true;
		}
		message = "Undo not allowed!";
		System.out.println(message);
		return false;
	}

	@Override
	public boolean validate(Position pos1, Position pos2) {
		if (pos2.getColor() != BullColor.NONE || pos1.getColor() != current.getColor())
			return false;
		else {
			return pos1.isNeighbor(pos2);
		}
	}

	@Override
	public void check() {
		GameState temp = GameState.NOMOVE;
		for (Position pos : positions) {
			if (pos.getColor().equals(current.getColor()) && pos.isMovable()) {
				temp = GameState.ACTIVE;
				break;
			}
		}
		if (temp.equals(GameState.NOMOVE))
			if (current.getCurrentPieces() > 1 && this.isCaptureGame) {
				this.message = "No move!";
				this.gameState = GameState.NOMOVE;
			} else {
				this.gameState = GameState.GAMEOVER;
				this.message = "Game over!";
			}

	}

	@Override
	public boolean capture(Position pos) {
		if (pos.getColor().equals(current.getColor())) {
			pos.setColor(BullColor.NONE);
			current.decPieces();
			this.message = "";
			this.gameState = GameState.ACTIVE;

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
}
