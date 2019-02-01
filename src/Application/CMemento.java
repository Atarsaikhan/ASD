package Application;

import Framework.IGameController;

class CMemento {
	private IGameController gameSnapshot;
	
	public CMemento(IGameController gameSnapshot) {
		this.gameSnapshot = gameSnapshot;
	
	}

	public IGameController getGameSnapshot() {
		return gameSnapshot;
	}
}