package horn;

import java.util.List;

import framework.APosition;
import framework.EBullColor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class GUIManager {
	static final int P_SIZE = 50;
	static final Color FILL_COLOR = Color.WHITE;
	static final Color BASE_COLOR = Color.CORNSILK;
	static final Color TEXT_COLOR = Color.RED;
	static final Color BUTTON_COLOR = Color.MEDIUMTURQUOISE;
	static final Color NORMAL_STROKE_COLOR = Color.BLACK;
	static final Color ACTIVE_STROKE_COLOR = Color.YELLOW;
	static final int LINE_WIDTH = 5;

	private GraphicsContext graphicsContext;

	public GraphicsContext getGraphicsContext() {
		return graphicsContext;
	}

	public void setGraphicsContext(GraphicsContext graphicsContext) {
		this.graphicsContext = graphicsContext;
	}

	public void drawBoard(List<APosition> positions) {
		// this.gc.setFill(FILL_COLOR);
		this.graphicsContext.setLineWidth(LINE_WIDTH);
		graphicsContext.setFill(BASE_COLOR);
		graphicsContext.fillRect(0, 0, graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight());
		graphicsContext.drawImage(new Image("resources/images/bullhorn2.png"), 0, 0);

		graphicsContext.setStroke(NORMAL_STROKE_COLOR);
		for (APosition pos : positions) {
			pos.setImage();
			for (APosition neighbor : pos.getNeighbors()) {
				graphicsContext.strokeLine(pos.getX(), pos.getY(), neighbor.getX(), neighbor.getY());
			}
		}

		for (APosition pos : positions) {
			drawPos(pos, NORMAL_STROKE_COLOR);
		}

//		drawPlayers();
//		drawArrowToCurrent();
//		stopTimer();
	}

	public void drawPos(APosition pos, Color strokColor) {
		graphicsContext.setStroke(strokColor);
		graphicsContext.setFill(FILL_COLOR);
		graphicsContext.fillOval(pos.getX() - P_SIZE / 2, pos.getY() - P_SIZE / 2, P_SIZE, P_SIZE);
		graphicsContext.strokeOval(pos.getX() - P_SIZE / 2, pos.getY() - P_SIZE / 2, P_SIZE, P_SIZE);
		if (pos.getColor() != EBullColor.NONE) {
			pos.setImage();
			int w = (int) (pos.getImage().getWidth() / 2);
			int h = (int) (pos.getImage().getHeight() / 2);
			graphicsContext.drawImage(pos.getImage(), pos.getX() - w, pos.getY() - h);
		}
	}
}
