package Application;

import java.util.List;

import Framework.BullColor;
import Framework.FourBullsGame;
import Framework.GameController;
import Framework.Position;
import Framework.GameState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Drawer {
	private final int P_SIZE = 100;
	private final Color FILL_COLOR = Color.WHITE;
	private final Color BASE_COLOR = Color.CORNSILK;
	private final Color TEXT_COLOR = Color.RED;
	private final Color NORMAL_STROKE_COLOR = Color.BLACK;
	private final Color ACTIVE_STROKE_COLOR = Color.YELLOW;
	private final int LINE_WIDTH = 5;
	
	private GameController game;
	private List<Position> positions;
	private Position activePos = null;
	private GraphicsContext gc;

	Drawer(GraphicsContext gc) {
		game = new FourBullsGame();
		this.positions = game.getPositions();
		this.gc = gc;
		this.gc.setFill(FILL_COLOR);
		this.gc.setLineWidth(LINE_WIDTH);
	}

	public void processClick(int x, int y) {

		for (Position pos : positions) {
			double distance = Math.sqrt(Math.pow(x - pos.getX(), 2) + Math.pow(y - pos.getY(), 2));
			if (distance < P_SIZE / 2) {
				if (pos.getColor() != BullColor.NONE) { // activate

					if (activePos != null) {
						drawPos(activePos, NORMAL_STROKE_COLOR);
					}
					
					if (pos == activePos //|| pos.getColor() != game.getCurrent().getColor()
							) {
						activePos = null;
						//if (pos.getColor() != game.getCurrent().getColor()) {
						//	animatePos(pos);
						//}
					} else {
						activePos = pos;
						drawPos(activePos, ACTIVE_STROKE_COLOR);
					}

				} else if (activePos != null && activePos != pos && game.move(activePos, pos)
				) { // move
					
					drawPos(pos, NORMAL_STROKE_COLOR);
					drawPos(activePos, NORMAL_STROKE_COLOR);
					
					activePos = null;
				}

			}
		}
		
	}

	
	public void updateStatus() {
		if (game.getGameState() == GameState.GAMEOVER) {
			drawStatusText("Game over");
		} else {
			drawCurrentPlayer(game.getCurrent().getColor());
			drawStatusText(game.getMessage());
		}
	}
	
	private void drawStatusText(String text) {
		// clear previous text
		gc.setFill(BASE_COLOR);
		gc.fillRoundRect(95, 645, 410, 30, 10, 10);
		gc.setFill(TEXT_COLOR);
		gc.fillText(text, 100, 660, 400);
	}
	
	
	private void drawCurrentPlayer(BullColor color) {
		String arrowLeftUrl = "resources/images/arrow_left.png";
		String arrowRightUrl = "resources/images/arrow_right.png";
		
		String whiteURL = "resources/images/bull_white.png";
		String blackURL = "resources/images/bull_black.png";
		Image imA;
		Image imB;
		
		//Clear previous arrow
		gc.setFill(BASE_COLOR);
		int arrowBackSize = 70;
		gc.fillRoundRect(300-arrowBackSize/2, 600-arrowBackSize/2, arrowBackSize, arrowBackSize, 20, 20);
		
		if (color == BullColor.WHITE) {
			imA = new Image(arrowLeftUrl);
			imB = new Image(whiteURL);
			gc.drawImage(imA, 300-imA.getWidth()/2, 600-imA.getHeight()/2);
			gc.drawImage(imB, 300-imB.getWidth()/2-150, 600-imB.getHeight()/2);
		} else //if (game.getCurrent().getColor() == BullColor.WHITE) 
		{
			imA = new Image(arrowRightUrl);
			imB = new Image(blackURL);		
			gc.drawImage(imA, 300-imA.getWidth()/2, 600-imA.getHeight()/2);
			gc.drawImage(imB, 300-imB.getWidth()/2+150, 600-imB.getHeight()/2);
		}
	}
	
	public void drawPositions() {
		gc.setStroke(NORMAL_STROKE_COLOR);
		for (Position pos : positions) {
			pos.setImage();
			for (Position neighbor : pos.getNeighbors()) {
				gc.strokeLine(pos.getX(), pos.getY(), neighbor.getX(), neighbor.getY());
			}
		}

		for (Position pos : positions) {
			drawPos(pos, NORMAL_STROKE_COLOR);
		}
		
		drawCurrentPlayer(BullColor.BLACK);
		drawCurrentPlayer(BullColor.WHITE);
	}

	private void drawPos(Position pos, Color strokColor) {
		gc.setStroke(strokColor);
		gc.setFill(FILL_COLOR);
		gc.fillOval(pos.getX() - P_SIZE / 2, pos.getY() - P_SIZE / 2, P_SIZE, P_SIZE);
		gc.strokeOval(pos.getX() - P_SIZE / 2, pos.getY() - P_SIZE / 2, P_SIZE, P_SIZE);
		if (pos.getColor() != BullColor.NONE) {
			pos.setImage();
			int w = (int) (pos.getImage().getWidth() / 2);
			int h = (int) (pos.getImage().getHeight() / 2);
			gc.drawImage(pos.getImage(), pos.getX() - w, pos.getY() - h);
		}
	}
	
	private void animatePos(Position pos) {
		Color colors[] = {ACTIVE_STROKE_COLOR, NORMAL_STROKE_COLOR};
        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount( 15 );
        
        final long timeStart = System.currentTimeMillis();
        
        KeyFrame kf = new KeyFrame(
            Duration.seconds(0.02),                // 0.017 = 60 FPS, 0.02 = 50 FPS, 0.05 = 20 FPS
            new EventHandler<ActionEvent>()
            {
                public void handle(ActionEvent ae)
                {
                    double t = (System.currentTimeMillis() - timeStart) / 2.0;
                    int index = (int)((t % (2 * 100)) / 100);
                    drawPos(pos, colors[index]);
                    //System.out.println("t:"+t+"index:"+index);
                }
            });
        
        gameLoop.getKeyFrames().add( kf );
        gameLoop.play();
	}

	public void restartGame() {
		game.restart();
	}
	
}
