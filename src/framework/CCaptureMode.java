package framework;

import java.util.List;

public class CCaptureMode implements IGameMode {
	private String message;

	@Override
	public EGameState changeState(List<APosition> positions, CPlayer current) {
		EGameState temp = EGameState.NOMOVE;
		this.message = "No move!";
		for (APosition pos : positions) {
			if (pos.getColor().equals(current.getColor()) && pos.isMovable()) {
				temp = EGameState.ACTIVE;
				this.message = "";
				return temp;
			}
		}
		if (current.getCurrentPieces() < 2)
			temp = EGameState.GAMEOVER;
		
		return temp;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
