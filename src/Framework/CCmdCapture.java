package Framework;

public class CCmdCapture implements ICommand {
	private APosition pos;
	private EGameState state;

	public APosition getPos1() {
		return pos;
	}

	public APosition getPos2() {
		return pos;
	}

	public CCmdCapture(APosition pos) {
		this.state = pos.getGameState();
		this.pos = pos;
	}

	@Override
	public boolean execute() {
		return pos.capture();
	}

	@Override
	public boolean undo() {
		return pos.undoCapture(this.state);
	}

}