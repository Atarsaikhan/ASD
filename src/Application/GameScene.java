package Application;

import Framework.Player;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

public class GameScene extends Scene {
	Group root;
//	GraphicsContext gc;
	Drawer drawer;
	Player player1;
	Player player2;
	
	public GameScene(Group root) {
		super(root);
		this.root = root;
	}

	public Drawer getDrawer() {
		return drawer;
	}

	public void setDrawer(Drawer drawer) {
		this.drawer = drawer;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

}
