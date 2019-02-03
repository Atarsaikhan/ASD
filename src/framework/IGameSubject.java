package framework;

public interface IGameSubject {
	public void attach(IGameObserver observer);
	public void detach(IGameObserver observer);
	public void notifyObservers(EDataType type, Object value);
}