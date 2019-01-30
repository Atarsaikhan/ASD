package Framework;

import java.util.List;

public interface GameMode {
	public GameState changeState(List<Position> positions, Player current);
	public String getMessage();
}
