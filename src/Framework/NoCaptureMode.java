package Framework;

import java.util.List;

public class NoCaptureMode implements GameMode {
	private String message;

	@Override
	public GameState changeState(List<Position> positions, Player current) {
		GameState temp = GameState.GAMEOVER;
		this.message = "Game over!";
		for (Position pos : positions) {
			if (pos.getColor().equals(current.getColor()) && pos.isMovable()) {
				temp = GameState.ACTIVE;
				this.message = "";
				return temp;
			}
		}
		return temp;
	}

	@Override
	public String getMessage() {
		return message;
	}

}