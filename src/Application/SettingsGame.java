package Application;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SettingsGame {

	private static SettingsGame uniqueInstance = null;

	private double timer;
	private double moveNumber;
	private String filename;

	
	private void SettingsGame() {
		this.readSettings(this.filename);
	}

	Map<String, Object> settings = new HashMap<>();
	List<SettingsGame> setting = new ArrayList<>();

	public double getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public double getMoveNumber() {
		return moveNumber;
	}

	public void setMoveNumber(int moveNumber) {
		this.moveNumber = moveNumber;
	}

	/**
	 * Returns a reference to the single instance. Creates the instance if it does
	 * not yet exist. (This is called lazy instantiation.)
	 */
	public static SettingsGame getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new SettingsGame();
		return uniqueInstance;
	}

	/**
	 * The Singleton Constructor. Note that it is private! No client can instantiate
	 * a Singleton object directly!
	 */
	private SettingsGame() {

	}

	public void writeSettings(String fileName) {
		settings.put("timer", this.timer);
		settings.put("movenumber", this.moveNumber);
		PrintWriter out = null;
		try {
			out = new PrintWriter(fileName);
			for (String name : settings.keySet()) {
				Object value = settings.get(name);
				out.println(name + ": " + value);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null)
				out.close();
		}
	}

	public void readSettings(String fileName) {
		Scanner in = null;
		try {
			in = new Scanner(new File(fileName)).useDelimiter("\\s+|\\s*:\\s*");
			while (in.hasNext()) {
				String name = in.next();
				if (name.length() == 0)
					continue;
				String value = in.next();
				Double doubleValue = null;
				try {
					doubleValue = Double.valueOf(value);
				} catch (NumberFormatException e) {
				}
				if (doubleValue != null)
					changeSetting(name, doubleValue);
				else
					changeSetting(name, value);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				in.close();
		}
	}

	void changeSetting(String name, double newvalue) {
		if (name.equals("timer")) {
			this.timer = newvalue;
		} else if (name.equals("movenumber")) {
			this.timer = newvalue;
		}
	}

	void changeSetting(String name, String newvalue) {

	}
}
