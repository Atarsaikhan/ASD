package gui;

import javafx.scene.Scene;

public interface IGuiBuilder {
	
	public void buildPlayer(String playerName1, String playerName2);

	public void initGameControl();

	public void buildGuiControls();

	public void buildHandlers();

	public Scene getGui();

}
