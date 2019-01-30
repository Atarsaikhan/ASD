package Framework;

public interface ICommand {
	public boolean execute();
	public boolean undo();
	public APosition getPos1();
	public APosition getPos2();
}
