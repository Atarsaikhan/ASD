package gui;

import javafx.scene.Scene;

public class CGuiDirector {
	private IGuiBuilder guiBuilder;
	public CGuiDirector(IGuiBuilder guiBuilder) {
		this.guiBuilder = guiBuilder;
	}

	public void constructGui() {
		guiBuilder.buildPlayer("Player 1", "Player 2");
		guiBuilder.initGameControl(); 
		guiBuilder.buildGuiControls(); 
		guiBuilder.buildHandlers();
	}
	public Scene getGui() {
		return guiBuilder.getGui();
	}
}
