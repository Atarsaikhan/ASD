package Application;

import java.util.List;
import java.util.Stack;

import Framework.EBullColor;
import Framework.ICommand;
import Framework.CCmdCapture;
import Framework.IGameController;
import Framework.EGameState;
import Framework.CCmdMove;
import Framework.APosition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class CDrawer implements IDrawer {

	private IGameController game;
	private Stack<ICommand> commandsExecuted;
	private List<APosition> positions;
	private APosition activePos = null;
	private GraphicsContext gc;
	private CGameSettings gameSettings; 

	CDrawer(IGameController game) {
		this.game = game;
		this.gameSettings = CGameSettings.getInstance();
		commandsExecuted = new Stack<ICommand>();
		this.positions = game.getPositions();
		//this.gc.setFill(FILL_COLOR);
		//this.gc.setLineWidth(LINE_WIDTH);
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
				
				ICommand cmd = new CCmdCapture(game, pos);
				if (cmd.execute()) {
						this.commandsExecuted.push(cmd);
						drawPos(pos, NORMAL_STROKE_COLOR);
					}
				}
			}
	}

	public void processMove(int x, int y) {
		for (APosition pos : positions) {
			double distance = Math.sqrt(Math.pow(x - pos.getX(), 2) + Math.pow(y - pos.getY(), 2));
			if (distance < P_SIZE / 2) {
				if (pos.getColor() != EBullColor.NONE) { // activate

					if (activePos != null) {
						drawPos(activePos, NORMAL_STROKE_COLOR);
					}

					if (pos == activePos // || pos.getColor() != game.getCurrent().getColor()
					) {
						activePos = null;
						// if (pos.getColor() != game.getCurrent().getColor()) {
						// animatePos(pos);
						// }
					} else {
						activePos = pos;
						drawPos(activePos, ACTIVE_STROKE_COLOR);
					}

				} else if (activePos != null && activePos != pos && this.move(activePos, pos)) { // move

					drawPos(pos, NORMAL_STROKE_COLOR);
					drawPos(activePos, NORMAL_STROKE_COLOR);

					activePos = null;
				}
			}
		}

	}

	public boolean move(APosition pos1, APosition pos2) {
		boolean ret = false;
		ICommand cmd = new CCmdMove(game, pos1, pos2);
		ret = cmd.execute();
		if (ret)
			this.commandsExecuted.push(cmd);
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
			this.updateStatus();
			System.out.println(cmd.getPos1().getId() + "|"+cmd.getPos1().getColor());
			System.out.println(cmd.getPos2().getId() + "|"+cmd.getPos2().getColor());
		}
		return ret;
	}

	public void updateStatus() {
		if (game.getGameState() == EGameState.GAMEOVER) {
			drawStatusText(game.getMessage());
			drawGameOver();
		} else if (game.getGameState() == EGameState.NOMOVE) {
			drawStatusText(game.getMessage());
			// drawGameOver();
		} else {
			drawCurrentPlayers(game.getCurrent().getColor());
			drawStatusText(game.getMessage());
		}
	}

	public void drawStatusText(String text) {
		// clear previous text
		gc.setFill(BASE_COLOR);
		gc.fillRoundRect(145, 15, 310, 30, 10, 10);
		gc.setFill(TEXT_COLOR);
		gc.fillText(text, 150, 30, 300);
	}

	public void drawGameOver() {
		gc.setFill(BASE_COLOR);
		gc.fillRoundRect(200, 200, 200, 200, 30, 30);
		gc.setFill(TEXT_COLOR);
		if (game.getCurrent().getColor() == EBullColor.BLACK)
			gc.fillText("Black won", 230, 300, 400);
		else
			gc.fillText("White won", 230, 300, 400);
		gc.fillText("Game is over", 230, 320, 400);
	}

	public void drawCurrentPlayers(EBullColor color) {
		
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

		if (color == EBullColor.WHITE) {
			imA = new Image(arrowLeftUrl);
			gc.drawImage(imA, 300 - imA.getWidth() / 2, 600 - imA.getHeight() / 2);
		} else // if (game.getCurrent().getColor() == BullColor.WHITE)
		{
			imA = new Image(arrowRightUrl);
			gc.drawImage(imA, 300 - imA.getWidth() / 2, 600 - imA.getHeight() / 2);
		}
	}

	public void drawPositions() {
		//this.gc.setFill(FILL_COLOR);
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

		drawCurrentPlayers(EBullColor.BLACK);
		drawCurrentPlayers(EBullColor.WHITE);

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

	public void animatePos(APosition pos) {
		Color colors[] = { ACTIVE_STROKE_COLOR, NORMAL_STROKE_COLOR };
		Timeline gameLoop = new Timeline();
		gameLoop.setCycleCount(15);

		final long timeStart = System.currentTimeMillis();

		KeyFrame kf = new KeyFrame(Duration.seconds(0.02), // 0.017 = 60 FPS, 0.02 = 50 FPS, 0.05 = 20 FPS
				new EventHandler<ActionEvent>() {
					public void handle(ActionEvent ae) {
						double t = (System.currentTimeMillis() - timeStart) / 2.0;
						int index = (int) ((t % (2 * 100)) / 100);
						drawPos(pos, colors[index]);
						// System.out.println("t:"+t+"index:"+index);
					}
				});

		gameLoop.getKeyFrames().add(kf);
		gameLoop.play();
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
}
