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

public class CYurtGame extends ABoardGame {

	CYurtGame(GraphicsContext graphicsContext) {
		super();

		this.gameController = new CBoardGameController();
		this.gameController.setPlayers(new CPlayer("Bull", 1, EBullColor.WHITE), new CPlayer("Cowboy", 2, EBullColor.BLACK));
		this.gameController.setGUIManager(graphicsContext, "toono.png");
		this.gameController.startGame(false, initPositions());
	}

	protected List<APosition> initPositions() {

		List<APosition> positions = new ArrayList<>();

		APosition pos0 = new CPositionImpl(0, 300, 100, EBullColor.WHITE, this.gameController); // top 0 tsagaan
		APosition pos1 = new CPositionImpl(1, 100, 200, EBullColor.NONE, this.gameController); // zuun deed 1 hooson
		APosition pos2 = new CPositionImpl(2, 500, 200, EBullColor.WHITE, this.gameController); // baruun deed 2 tsagaan
		APosition pos3 = new CPositionImpl(3, 300, 300, EBullColor.BLACK, this.gameController); // gol 3 har
		APosition pos4 = new CPositionImpl(4, 100, 400, EBullColor.BLACK, this.gameController); // zuun dood 4 har
		APosition pos5 = new CPositionImpl(5, 500, 400, EBullColor.BLACK, this.gameController); // baruun dood 5 har
		APosition pos6 = new CPositionImpl(6, 300, 500, EBullColor.WHITE, this.gameController); // dood 6 tsagaan

		pos0.addNeighbor(pos1);
		pos0.addNeighbor(pos2);
		pos0.addNeighbor(pos3);

		pos1.addNeighbor(pos0);
		pos1.addNeighbor(pos3);
		pos1.addNeighbor(pos4);

		pos2.addNeighbor(pos0);
		pos2.addNeighbor(pos3);
		pos2.addNeighbor(pos5);

		pos3.addNeighbor(pos0);
		pos3.addNeighbor(pos1);
		pos3.addNeighbor(pos2);
		pos3.addNeighbor(pos4);
		pos3.addNeighbor(pos5);
		pos3.addNeighbor(pos6);

		pos4.addNeighbor(pos1);
		pos4.addNeighbor(pos3);
		pos4.addNeighbor(pos6);

		pos5.addNeighbor(pos2);
		pos5.addNeighbor(pos3);
		pos5.addNeighbor(pos6);

		pos6.addNeighbor(pos3);
		pos6.addNeighbor(pos4);
		pos6.addNeighbor(pos5);

		positions.add(pos0);
		positions.add(pos1);
		positions.add(pos2);
		positions.add(pos3);
		positions.add(pos4);
		positions.add(pos5);
		positions.add(pos6);

		return positions;

	}

}
