package framework;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class CGuiManager {
	int P_SIZE = 58;
	int LINE_WIDTH = 5;
	Color FILL_COLOR = Color.WHITE;
	Color BASE_COLOR = Color.CORNSILK;
	Color NORMAL_STROKE_COLOR = Color.BLACK;
	Color MOVABLE_STROKE_COLOR = Color.YELLOW;
	Color ACTIVE_STROKE_COLOR = Color.RED;

	private String backImage = "";
	private GraphicsContext graphicsContext;

	public String getBackImage() {
		return backImage;
	}

	public void setBackImage(String backImage) {
		this.backImage = "resources/images/" + backImage;
	}

	public GraphicsContext getGraphicsContext() {
		return graphicsContext;
	}

	public void setGraphicsContext(GraphicsContext graphicsContext) {
		this.graphicsContext = graphicsContext;
	}

	public CGuiManager() {

	}

	public void drawBoard(List<APosition> positions) {
		graphicsContext.setLineWidth(LINE_WIDTH);
		graphicsContext.setFill(BASE_COLOR);
		graphicsContext.fillRect(0, 0, graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight());
		if (backImage.length() > 0)
			graphicsContext.drawImage(new Image(backImage), 0, 0);
		graphicsContext.setStroke(NORMAL_STROKE_COLOR);

		for (APosition pos : positions) {
			pos.setImage();
			for (APosition neighbor : pos.getNeighbors()) {
				graphicsContext.strokeLine(pos.getX(), pos.getY(), neighbor.getX(), neighbor.getY());
			}
		}

		drawPositions(positions);

	}

	public void drawPositions(List<APosition> positions) {
		for (APosition pos : positions) {
			if (pos.isMovable())
				drawPos(pos, this.MOVABLE_STROKE_COLOR);
			else
				drawPos(pos, this.NORMAL_STROKE_COLOR);
		}
	}

	public void drawPos(APosition pos, Color strokColor) {
		if (pos == null)
			return;
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
