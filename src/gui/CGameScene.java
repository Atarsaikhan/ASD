package gui;

import framework.CPlayer;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class CGameScene extends Scene {
	Group root;
//	GraphicsContext gc;
	private CDrawer drawer;
	private CPlayer player1;
	private CPlayer player2;
	
	public CGameScene(Group root) {
		super(root);
		this.root = root;
	}

	public CDrawer getDrawer() {
		return drawer;
	}

	public void setDrawer(CDrawer drawer) {
		this.drawer = drawer;
	}

	public CPlayer getPlayer1() {
		return player1;
	}

	public void setPlayer1(CPlayer player1) {
		this.player1 = player1;
	}

	public CPlayer getPlayer2() {
		return player2;
	}

	public void setPlayer2(CPlayer player2) {
		this.player2 = player2;
	}

}
