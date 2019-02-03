package app;

import framework.ABoardGame;

class CMemento {
	private ABoardGame gameSnapshot;
	
	public CMemento(ABoardGame gameSnapshot) {
		this.gameSnapshot = gameSnapshot;
	
	}

	public ABoardGame getGameSnapshot() {
		return gameSnapshot;
	}
}