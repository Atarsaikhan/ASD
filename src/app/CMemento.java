package app;

import framework.IBoardGame;

class CMemento {
	private IBoardGame gameSnapshot;
	
	public CMemento(IBoardGame gameSnapshot) {
		this.gameSnapshot = gameSnapshot;
	
	}

	public IBoardGame getGameSnapshot() {
		return gameSnapshot;
	}
}