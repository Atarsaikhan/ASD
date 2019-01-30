package Framework;

import java.util.List;

import Framework.GameController;
import Framework.Player;
import Framework.Position;


class Memento {
	private GameController gameSnapshot;
	
	public Memento(GameController gameSnapshot) {
		this.gameSnapshot = gameSnapshot;
	
	}

	public GameController getGameSnapshot() {
		return gameSnapshot;
	}
}