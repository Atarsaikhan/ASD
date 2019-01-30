package Framework;

public class CommandCapture implements Command {
	private GameController game;
	private Position pos;
	private GameState state;

	public Position getPos1() {
		return pos;
	}

	public Position getPos2() {
		return pos;
	}

	public CommandCapture(GameController game, Position pos) {
		this.game = game;
		this.state = game.getGameState();
		this.pos = pos;
	}

	@Override
	public boolean execute() {
		return game.capture(pos);
	}

	@Override
	public boolean undo() {
		return game.undoCapture(pos, this.state);
	}

}