package Framework;

public interface Command {
	public boolean execute();
	public boolean undo();
	public Position getPos1();
	public Position getPos2();
}
