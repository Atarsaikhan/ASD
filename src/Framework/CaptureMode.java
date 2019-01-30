package Framework;

import java.util.List;

public class CaptureMode implements GameMode {
	private String message;

	@Override
	public GameState changeState(List<Position> positions, Player current) {
		GameState temp = GameState.NOMOVE;
		this.message = "No move!";
		for (Position pos : positions) {
			if (pos.getColor().equals(current.getColor()) && pos.isMovable()) {
				temp = GameState.ACTIVE;
				this.message = "";
				return temp;
			}
		}
		if (current.getCurrentPieces() < 2)
			temp = GameState.GAMEOVER;
		
		return temp;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
