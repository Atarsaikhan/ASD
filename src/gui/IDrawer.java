package gui;

import Framework.APosition;
import Framework.EBullColor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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
	public boolean move(APosition pos1, APosition pos2);
	public boolean undo();
	public void drawStatus();
	public void drawStatusText(String text);
	public void drawGameOver();
	public void drawCurrentPlayers();
	public void drawPositions();
	public void drawPos(APosition pos, Color strokColor);
	public void startTimer();
	public void restartGame();
	public GraphicsContext getGc();
	public void setGc(GraphicsContext gc);
}
