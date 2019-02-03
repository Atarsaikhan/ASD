package app;

import java.io.File;
import java.net.URISyntaxException;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;


public final class CSoundController {

	private static CSoundController instance = new CSoundController();

	static Media soundStart;
	static Media soundActivate;
	static Media soundMove;
	static Media soundNoMove;
	
	private CSoundController() {

			soundStart = new Media(getClass().getClassLoader().getResource("resources/sounds/Sound-Tapple.mp3").toString());
			soundActivate = new Media(getClass().getClassLoader().getResource("resources/sounds/Sound-Activate.mp3").toString());
			soundMove = new Media(getClass().getClassLoader().getResource("resources/sounds/Sound-Move.mp3").toString());
			soundNoMove = new Media(getClass().getClassLoader().getResource("resources/sounds/Sound-Tud.mp3").toString());

	}

	public static CSoundController getInstance() {
		return instance;
	}

	private static void playSoundEffect(Media sound) {
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
	}
	
	public static void start() {
		getInstance();
		playSoundEffect(soundStart);
	}
	
	public static void activate() {
		getInstance();
		CSoundController.playSoundEffect(soundActivate);
	}
	
	public static void move() {
		getInstance();
		playSoundEffect(soundMove);
	}
	
	public static void noMove() {
		getInstance();
		playSoundEffect(soundNoMove);
	}

}
