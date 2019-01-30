package Framework;

public class CPlayer {
	private String name;
	private int defaultPieces;
	private int currentPieces;
	private EBullColor color;
	private EGameState state;
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

	public EGameState getState() {
		return state;
	}

	public void setState(EGameState state) {
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
	
	public EBullColor getColor() {
		return color;
	}

	public CPlayer(String name, int pieces, EBullColor color) {
		super();
		this.name = name;
		this.defaultPieces = pieces;
		this.color = color;
		reset();
	}

	public void reset() {
		this.currentPieces = this.defaultPieces;
		this.state = EGameState.ACTIVE;
		this.moves = 0;
	}

}
