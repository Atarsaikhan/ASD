package framework;

public interface IGameMediator {
	public boolean move(APosition pos1, APosition pos2);
	public boolean capture(APosition pos);
}