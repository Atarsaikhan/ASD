package Framework;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

public class Position1 {
	private int id;
	private int x;
	private int y;
	private BullColor color;
	private List<Position> neighbors;
	private Image image;
	
	public Position1(int id, int x, int y, BullColor color) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
		this.color = color;
		this.neighbors = new ArrayList<Position>();
	}
	
	public int getId() {
		return id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public BullColor getColor() {
		return color;
	}
	
	public void setColor(BullColor color) {
		this.color = color;
	}

	public Image getImage() {
		return image;
	}
	
	public void setImage(Image image) {
		this.image = image;
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
	
	
}
