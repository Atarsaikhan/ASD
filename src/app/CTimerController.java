package app;

import framework.ABoardGame;
import framework.IGameObserver;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class CTimerController implements IGameObserver {
	int timerValue = 0;
	private Timeline timeLoop = null;
	boolean isTimed = false;
	Canvas gameCanvas;
	ABoardGame game;
	CGameSettings gameSettings;
	MainWindowController window;

	CTimerController(Canvas gameCanvas, ABoardGame game, CGameSettings gameSettings, MainWindowController window) {
		this.gameCanvas = gameCanvas;
		this.game = game;
		this.gameSettings = gameSettings;
		this.window = window;
		game.attach(this);
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
						if (timerValue <= 0) {
							game.timeExpired();
							window.drawStatusText(game.getMessage());
						}
					}
				});

		timeLoop.getKeyFrames().add(kf);
		timeLoop.play();
	}
	
	public void stopTimer() {
		if (timeLoop != null) {
			timeLoop.stop();
			timeLoop.getKeyFrames().clear();
		}
		if (isTimed)
			drawTimer(0);
		else
			drawTimer(null);
	}

	// update

	@Override
	public void update(int moveCount) {
		if (moveCount >= gameSettings.getMoveNumber()) {
			isTimed = true;
			startTimer();
		} else {
			isTimed = false;
			stopTimer();
		}
	}
	

	public void drawTimer(Integer t) {

		GraphicsContext gc = gameCanvas.getGraphicsContext2D();
		gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 16));
		// clear previous text
		gc.setFill(Color.web("#e0e0d1"));
		gc.fillRoundRect(240, 440, 120, 30, 10, 10);

		gc.setFill(Color.GRAY);
		if (t != null) {
			gc.fillText("Timer: " + t, 245, 465, 110);
		} else {
			gc.fillText("No timer", 245, 465, 110);
		}
	}
	
	
	
}
