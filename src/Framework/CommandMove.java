package Framework;

public class CommandMove implements Command {
	private GameController game;
	private Position pos1;
	private Position pos2;
	private GameState state;

	public Position getPos1() {
		return pos1;
	}

	public Position getPos2() {
		return pos2;
	}

	public CommandMove(GameController game, Position pos1, Position pos2) {
		this.game = game;
		this.state = game.getGameState();
		this.pos1 = pos1;
		this.pos2 = pos2;
	}

	@Override
	public boolean execute() {
		return game.move(pos1, pos2);
	}

	@Override
	public boolean undo() {
		return game.undoMove(pos1, pos2, state);
	}

}
