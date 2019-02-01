package framework;

public class CCmdMove implements ICommand {
	private APosition pos1;
	private APosition pos2;
	private EGameState state;

	public APosition getPos1() {
		return pos1;
	}

	public APosition getPos2() {
		return pos2;
	}

	public CCmdMove(APosition pos1, APosition pos2) {
		this.state = pos1.getGameState();
		this.pos1 = pos1;
		this.pos2 = pos2;
	}

	@Override
	public boolean execute() {
		return pos1.move(pos2);
	}

	@Override
	public boolean undo() {
		return pos1.undoMove(pos2, state);
	}

}
