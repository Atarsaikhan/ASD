package Application;

import javafx.scene.Scene;

public class GuiDirector {
	private GuiBuilder guiBuilder;
	public GuiDirector(GuiBuilder guiBuilder) {
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
