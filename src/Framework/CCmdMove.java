package Framework;

public class CCmdMove implements ICommand {
	private IGameController game;
	private APosition pos1;
	private APosition pos2;
	private EGameState state;

	public APosition getPos1() {
		return pos1;
	}

	public APosition getPos2() {
		return pos2;
	}

	public CCmdMove(IGameController game, APosition pos1, APosition pos2) {
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
