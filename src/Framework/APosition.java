package Framework;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

public abstract class APosition {
	protected IGameController controller;
	protected int id;
	private int x;
	private int y;
	protected EBullColor color;
	private List<APosition> neighbors;
	private Image image;
	private String whiteURL = "resources/images/bull_blue.jpg";
	private String blackURL = "resources/images/bull_purple.jpg";

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public EBullColor getColor() {
		return color;
	}

	public void setColor(EBullColor color) {
		this.color = color;

	}

	public int getId() {
		return id;
	}

	APosition(int id, int x, int y, EBullColor color) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.setColor(color);
		this.neighbors = new ArrayList<APosition>();
	}

	public Image getImage() {
		return image;
	}

	public void setImage() {
		if (this.color == EBullColor.BLACK)
			this.image = new Image(blackURL);
		else if (this.color == EBullColor.WHITE)
			this.image = new Image(whiteURL);
		else
			this.image = null;
	}

	public List<APosition> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(List<APosition> neighbors) {
		this.neighbors = neighbors;
	}

	public void addNeighbor(APosition neighbor) {
		this.neighbors.add(neighbor);
	}

	public boolean isNeighbor(APosition neighbor) {
		return this.neighbors.contains(neighbor);
	}

	public boolean isMovable() {
		for (APosition pos : neighbors) {
			if (pos.getColor().equals(EBullColor.NONE))
				return true;
		}
		return false;
	}
}
