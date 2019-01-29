package Framework;

public class Player {
	private String name;
	private int defaultPieces;
	private int currentPieces;
	private BullColor color;
	private GameState state;
	private int moves;

	public int getMoves() {
		return moves;
	}

	public void incMoves() {
		moves++;
	}

	public void decMoves() {
		moves--;
	}

	public String getName() {
		return name;
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public int getDefaultPieces() {
		return defaultPieces;
	}

	public int getCurrentPieces() {
		return currentPieces;
	}

	public void incPieces() {
		currentPieces++;
	}

	public void decPieces() {
		currentPieces--;
	}
	
	public BullColor getColor() {
		return color;
	}

	public Player(String name, int pieces, BullColor color) {
		super();
		this.name = name;
		this.defaultPieces = pieces;
		this.color = color;
		reset();
	}

	public void reset() {
		this.currentPieces = this.defaultPieces;
		this.state = GameState.ACTIVE;
		this.moves = 0;
	}

}
