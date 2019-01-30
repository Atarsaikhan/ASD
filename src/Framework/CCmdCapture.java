package Framework;

public class CCmdCapture implements ICommand {
	private IGameController game;
	private APosition pos;
	private EGameState state;

	public APosition getPos1() {
		return pos;
	}

	public APosition getPos2() {
		return pos;
	}

	public CCmdCapture(IGameController game, APosition pos) {
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