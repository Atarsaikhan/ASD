package Framework;

public enum GameState {
	ACTIVE(0), NOMOVE(1), GAMEOVER(2);

	private int state;

	GameState(int state) {
		this.state = state;
	}

}
