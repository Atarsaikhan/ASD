package app;

import java.util.ArrayList;
import java.util.List;

import framework.ABoardGame;
import framework.APosition;
import framework.CBoardGameController;
import framework.CPlayer;
import framework.CPositionImpl;
import framework.EBullColor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class C4BullsGame extends ABoardGame {

	C4BullsGame() {
		super();
		gameController = new CBoardGameController();
	}

	public void setPlayers(CPlayer white, CPlayer black) {
		this.gameController.setPlayers(white, black);
	}

	public void setGUIManager(GraphicsContext graphicsContext, String backImage) {
		this.gameController.setGUIManager(graphicsContext, backImage);
	}

	public void setObjectProperties(int pawnSize, int lineWidth, Color fillColor, Color baseColor, Color normalStroke,
			Color movableStroke, Color activeColor) {
		this.setObjectProperties(pawnSize, lineWidth, fillColor, baseColor, normalStroke, movableStroke, activeColor);
	}

	public void startGame(boolean isCaptureGame) {
		this.gameController.startGame(isCaptureGame, initPositions());
	}

	protected List<APosition> initPositions() {

		List<APosition> positions = new ArrayList<>();

		APosition pos0 = new CPositionImpl(0, 100, 100, EBullColor.WHITE, this.gameController);
		APosition pos1 = new CPositionImpl(1, 500, 100, EBullColor.BLACK, this.gameController);
		APosition pos2 = new CPositionImpl(2, 300, 300, EBullColor.NONE, this.gameController);
		APosition pos3 = new CPositionImpl(3, 100, 500, EBullColor.BLACK, this.gameController);
		APosition pos4 = new CPositionImpl(4, 500, 500, EBullColor.WHITE, this.gameController);

		pos0.addNeighbor(pos1);
		pos0.addNeighbor(pos2);
		pos0.addNeighbor(pos3);

		pos1.addNeighbor(pos0);
		pos1.addNeighbor(pos2);

		pos2.addNeighbor(pos0);
		pos2.addNeighbor(pos1);
		pos2.addNeighbor(pos3);
		pos2.addNeighbor(pos4);

		pos3.addNeighbor(pos0);
		pos3.addNeighbor(pos2);
		pos3.addNeighbor(pos4);

		pos4.addNeighbor(pos2);
		pos4.addNeighbor(pos3);

		positions.add(pos0);
		positions.add(pos1);
		positions.add(pos2);
		positions.add(pos3);
		positions.add(pos4);

		return positions;

	}
}