package Framework;

import java.util.List;

public class CNonCaptureMode implements IGameMode {
	private String message;

	@Override
	public EGameState changeState(List<APosition> positions, CPlayer current) {
		EGameState temp = EGameState.GAMEOVER;
		this.message = "Game over!";
		for (APosition pos : positions) {
			if (pos.getColor().equals(current.getColor()) && pos.isMovable()) {
				temp = EGameState.ACTIVE;
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