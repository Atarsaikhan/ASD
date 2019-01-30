package Framework;

import java.util.List;

public interface IGameMode {
	public EGameState changeState(List<APosition> positions, CPlayer current);
	public String getMessage();
}
