package Application;

import java.util.List;
import java.util.Stack;

import Framework.BullColor;
import Framework.Command;
import Framework.CommandCapture;
import Framework.CommandMove;
import Framework.GameController;
import Framework.GameState;
import Framework.Position;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public interface IDrawer {
	static final int P_SIZE = 100;
	static final Color FILL_COLOR = Color.WHITE;
	static final Color BASE_COLOR = Color.CORNSILK;
	static final Color TEXT_COLOR = Color.RED;
	static final Color BUTTON_COLOR = Color.MEDIUMTURQUOISE;
	static final Color NORMAL_STROKE_COLOR = Color.BLACK;
	static final Color ACTIVE_STROKE_COLOR = Color.YELLOW;
	static final int LINE_WIDTH = 5;

	public void processClick(int x, int y);
	public void processCapture(int x, int y);
	public void processMove(int x, int y);
	public boolean move(Position pos1, Position pos2);
	public boolean undo();
	public void updateStatus();
	public void drawStatusText(String text);
	public void drawGameOver();
	public void drawCurrentPlayers(BullColor color);
	public void drawPositions();
	public void drawPos(Position pos, Color strokColor);
	public void animatePos(Position pos);
	public void restartGame();
	public GraphicsContext getGc();
	public void setGc(GraphicsContext gc);
}
