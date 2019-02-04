package framework;

public enum EGameState {
	ACTIVE(0), NOMOVE(1), GAMEOVER(2);

	@SuppressWarnings("unused")
	private int state;

	EGameState(int state) {
		this.state = state;
	}

}
