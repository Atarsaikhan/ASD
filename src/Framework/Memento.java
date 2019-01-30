package Framework;

class Memento {
	private IGameController gameSnapshot;
	
	public Memento(IGameController gameSnapshot) {
		this.gameSnapshot = gameSnapshot;
	
	}

	public IGameController getGameSnapshot() {
		return gameSnapshot;
	}
}