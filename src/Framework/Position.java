package Framework;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

public abstract class Position {
	protected GameController controller;
	protected int id;
	private int x;
	private int y;
	protected BullColor color;
	private List<Position> neighbors;
	private Image image;
	private String whiteURL = "resources/images/bull_blue.jpg";
	private String blackURL = "resources/images/bull_purple.jpg";

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public BullColor getColor() {
		return color;
	}

	public void setColor(BullColor color) {
		this.color = color;

	}

	public int getId() {
		return id;
	}

	Position(int id, int x, int y, BullColor color) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.setColor(color);
		this.neighbors = new ArrayList<Position>();
	}

	public Image getImage() {
		return image;
	}

	public void setImage() {
		if (this.color == BullColor.BLACK)
			this.image = new Image(blackURL);
		else if (this.color == BullColor.WHITE)
			this.image = new Image(whiteURL);
		else
			this.image = null;
	}

	public List<Position> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(List<Position> neighbors) {
		this.neighbors = neighbors;
	}

	public void addNeighbor(Position neighbor) {
		this.neighbors.add(neighbor);
	}

	public boolean isNeighbor(Position neighbor) {
		return this.neighbors.contains(neighbor);
	}

	public boolean isMovable() {
		for (Position pos : neighbors) {
			if (pos.getColor().equals(BullColor.NONE))
				return true;
		}
		return false;
	}
}
