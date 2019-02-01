package gui;

import javafx.scene.Scene;

public class CGuiDirector {
	private IGuiBuilder guiBuilder;
	public CGuiDirector(IGuiBuilder guiBuilder) {
		this.guiBuilder = guiBuilder;
	}

	public void constructGui() {
		guiBuilder.buildPlayer("Blue", "Pink");
		guiBuilder.initGameControl(); 
		guiBuilder.buildGuiControls(); 
		guiBuilder.buildHandlers();
	}
	public Scene getGui() {
		return guiBuilder.getGui();
	}
}
