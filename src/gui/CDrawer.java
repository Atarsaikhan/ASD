package gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import Application.CGameSettings;
import Application.IObserverMoveNumber;
import Application.IObserverTime;
import Framework.APosition;
import Framework.CCmdCapture;
import Framework.CCmdMove;
import Framework.EBullColor;
import Framework.EGameState;
import Framework.ICommand;
import Framework.IGameController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class CDrawer implements IDrawer, IObserverMoveNumber {
	public static final String SETTINGS_FILE = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "resources" + File.separator + "config.txt";
	private IGameController game;
	private Stack<ICommand> commandsExecuted;
	private List<APosition> positions;
	private APosition activePos = null;
	private GraphicsContext gc;
	private CGameSettings gameSettings;
	private boolean isTimed;
	private Timeline timeLoop;
	private List<IObserverTime> timeObservers;
	private final Object MUTEX = new Object();
	private int timerValue;

	CDrawer(IGameController game) {
		this.game = game;
		this.gameSettings = CGameSettings.getInstance();
		this.gameSettings.readSettings(SETTINGS_FILE);
		commandsExecuted = new Stack<ICommand>();
		this.positions = game.getPositions();
		timeObservers = new ArrayList<>();
		game.attach(this);
	}

	public void processClick(int x, int y) {
		if (game.getGameState() == EGameState.ACTIVE) {
			processMove(x, y);
		} else if (game.getGameState() == EGameState.NOMOVE) {
			processCapture(x, y);
		}
	}

	public void processCapture(int x, int y) {
		for (APosition pos : positions) {
			double distance = Math.sqrt(Math.pow(x - pos.getX(), 2) + Math.pow(y - pos.getY(), 2));
			if (distance < P_SIZE / 2 && pos.getColor() == game.getCurrent().getColor()) {

				ICommand cmd = new CCmdCapture(pos);
				if (cmd.execute()) {
					this.commandsExecuted.push(cmd);
					drawPos(pos, NORMAL_STROKE_COLOR);
					if (isTimed) {
						startTimer();
					}
				}
			}
		}
	}

	public void processMove(int x, int y) {
		for (APosition pos : positions) {
			double distance = Math.sqrt(Math.pow(x - pos.getX(), 2) + Math.pow(y - pos.getY(), 2));
			if (distance < P_SIZE / 2) {
				// activate
				if (pos.getColor() != EBullColor.NONE) {

					if (activePos != null) {
						drawPos(activePos, NORMAL_STROKE_COLOR);
					}

					if (pos == activePos) {
						activePos = null;
					} else {
						activePos = pos;
						drawPos(activePos, ACTIVE_STROKE_COLOR);
					}

				}
				// move
				else if (activePos != null && activePos != pos && this.move(activePos, pos)) {

					drawPos(pos, NORMAL_STROKE_COLOR);
					drawPos(activePos, NORMAL_STROKE_COLOR);

					activePos = null;
				}
			}
		}

	}

	public boolean move(APosition pos1, APosition pos2) {
		boolean ret = false;
		ICommand cmd = new CCmdMove(pos1, pos2);
		ret = cmd.execute();
		if (ret) {
			this.commandsExecuted.push(cmd);
			if (isTimed) {
				startTimer();
			}
		}

		return ret;
	}

	public boolean undo() {
		boolean ret = false;
		System.out.println("Undo");
		if (!this.commandsExecuted.isEmpty()) {
			ICommand cmd = this.commandsExecuted.pop();
			ret = cmd.undo();
			this.drawPos(cmd.getPos1(), NORMAL_STROKE_COLOR);
			this.drawPos(cmd.getPos2(), NORMAL_STROKE_COLOR);
			this.drawStatus();
			System.out.println(cmd.getPos1().getId() + "|" + cmd.getPos1().getColor());
			System.out.println(cmd.getPos2().getId() + "|" + cmd.getPos2().getColor());
		}
		return ret;
	}

	public void drawStatus() {
		if (game.getGameState() == EGameState.GAMEOVER) {
			drawStatusText(game.getMessage());
			drawGameOver();
			stopTimer();
		} else if (game.getGameState() == EGameState.NOMOVE) {
			drawStatusText(game.getMessage());
			// drawGameOver();
		} else {
			drawCurrentPlayers();
			drawStatusText(game.getMessage());
		}
	}

	public void drawStatusText(String text) {
		gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
		// clear previous text
		gc.setFill(BASE_COLOR);
		gc.fillRoundRect(145, 15, 310, 30, 10, 10);
		gc.setFill(TEXT_COLOR);
		gc.fillText(text, 150, 40, 300);
	}

	public void drawGameOver() {
		gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
		gc.setFill(BASE_COLOR);
		gc.fillRoundRect(200, 200, 200, 200, 30, 30);
		gc.setFill(TEXT_COLOR);
		if (game.getCurrent().getColor() == EBullColor.BLACK)
			gc.fillText("Black won", 230, 300, 400);
		else
			gc.fillText("White won", 230, 300, 400);
		gc.fillText("Game is over", 230, 340, 400);
	}

	public void drawCurrentPlayers() {

		String whiteURL = "resources/images/bull_purple.jpg";
		String blackURL = "resources/images/bull_blue.jpg";

		Image imW = new Image(whiteURL);
		Image imB = new Image(blackURL);
		gc.drawImage(imW, 300 - imW.getWidth() / 2 + 150, 600 - imW.getHeight() / 2);
		gc.drawImage(imB, 300 - imB.getWidth() / 2 - 150, 600 - imB.getHeight() / 2);

		String arrowLeftUrl = "resources/images/arrow_left.png";
		String arrowRightUrl = "resources/images/arrow_right.png";
		Image imA;

		// Clear previous arrow
		gc.setFill(BASE_COLOR);
		int arrowBgSize = 90;
		gc.fillRoundRect(300 - arrowBgSize / 2, 600 - arrowBgSize / 2, arrowBgSize, arrowBgSize, 20, 20);

		if (game.getCurrent().getColor() == EBullColor.WHITE) {
			imA = new Image(arrowLeftUrl);
			gc.drawImage(imA, 300 - imA.getWidth() / 2, 600 - imA.getHeight() / 2);
		} else // if (game.getCurrent().getColor() == BullColor.WHITE)
		{
			imA = new Image(arrowRightUrl);
			gc.drawImage(imA, 300 - imA.getWidth() / 2, 600 - imA.getHeight() / 2);
		}
	}

	public void drawPositions() {
		// this.gc.setFill(FILL_COLOR);
		this.gc.setLineWidth(LINE_WIDTH);
		gc.setFill(BASE_COLOR);
		gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		gc.drawImage(new Image("resources/images/bullBackground.jpg"), 0, 0);

		gc.setStroke(NORMAL_STROKE_COLOR);
		for (APosition pos : positions) {
			pos.setImage();
			for (APosition neighbor : pos.getNeighbors()) {
				gc.strokeLine(pos.getX(), pos.getY(), neighbor.getX(), neighbor.getY());
			}
		}

		for (APosition pos : positions) {
			drawPos(pos, NORMAL_STROKE_COLOR);
		}

		drawCurrentPlayers();
		stopTimer();

	}

	public void drawPos(APosition pos, Color strokColor) {
		gc.setStroke(strokColor);
		gc.setFill(FILL_COLOR);
		gc.fillOval(pos.getX() - P_SIZE / 2, pos.getY() - P_SIZE / 2, P_SIZE, P_SIZE);
		gc.strokeOval(pos.getX() - P_SIZE / 2, pos.getY() - P_SIZE / 2, P_SIZE, P_SIZE);
		if (pos.getColor() != EBullColor.NONE) {
			pos.setImage();
			int w = (int) (pos.getImage().getWidth() / 2);
			int h = (int) (pos.getImage().getHeight() / 2);
			gc.drawImage(pos.getImage(), pos.getX() - w, pos.getY() - h);
		}
	}

	public void stopTimer() {
		if (timeLoop != null) {
			timeLoop.stop();
			timeLoop.getKeyFrames().clear();
		}
		drawTimer(0);
	}

	public void drawTimer(int t) {
		gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
		// clear previous text
		gc.setFill(BASE_COLOR);
		gc.fillRoundRect(240, 440, 120, 30, 10, 10);
		gc.setFill(TEXT_COLOR);
		gc.fillText("Timer: " + t, 245, 465, 110);
	}

	public void startTimer() {
		int timeMax = gameSettings.getTimer();
		drawTimer((int) (timeMax));
		if (timeLoop == null)
			timeLoop = new Timeline();
		else {
			timeLoop.stop();
			timeLoop.getKeyFrames().clear();
		}
		timeLoop.setCycleCount(timeMax);

		final long timeStart = System.currentTimeMillis();

		KeyFrame kf = new KeyFrame(Duration.seconds(1), // 0.017 = 60 FPS, 0.02 = 50 FPS, 0.05 = 20 FPS
				new EventHandler<ActionEvent>() {
					public void handle(ActionEvent ae) {
						double t = (System.currentTimeMillis() - timeStart) / 1000;
						timerValue = (int) (timeMax - t);
						drawTimer(timerValue);
						notifyTimeObservers();
					}
				});

		timeLoop.getKeyFrames().add(kf);
		timeLoop.play();
	}

	public void timeOut() {
		game.timeExpired();
		drawStatus();
		drawCurrentPlayers();
		System.out.println(game.getGameState());
	}

	public void restartGame() {
		game.restart();
	}

	public GraphicsContext getGc() {
		return gc;
	}

	public void setGc(GraphicsContext gc) {
		this.gc = gc;
	}

	public void setGameSettings(CGameSettings gameSettings) {
		this.gameSettings = gameSettings;
	}

	public CGameSettings getGameSettings() {
		return this.gameSettings;
	}

	@Override
	public void update(int moveNumber) {
		if (moveNumber >= gameSettings.getMoveNumber()) {
			isTimed = true;
		} else {
			isTimed = false;
		}
	}

	public void attach(IObserverTime timeObserver) {
		synchronized (MUTEX) {
			System.out.println("time observer attached");
			if (!timeObservers.contains(timeObserver))
				timeObservers.add(timeObserver);
		}
	}

	public void detach(IObserverTime timeObserver) {
		synchronized (MUTEX) {
			int i = timeObservers.indexOf(timeObserver);
			if (i >= 0)
				timeObservers.remove(i);
		}
	}

	public void notifyTimeObservers() {
		synchronized (MUTEX) {
			for (IObserverTime ob : timeObservers) {
				ob.update(timerValue);
			}
		}
	}

}
