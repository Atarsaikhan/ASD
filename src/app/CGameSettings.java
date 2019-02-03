package app;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CGameSettings {

	private static CGameSettings uniqueInstance = null;

	private int timer;
	private int moveNumber;
	private String filename;

	/**
	 * The Singleton Constructor. Note that it is private! No client can instantiate
	 * a Singleton object directly!
	 */
	private CGameSettings() {

	}
	/**
	 * Returns a reference to the single instance. Creates the instance if it does
	 * not yet exist. (This is called lazy instantiation.)
	 */
	public static CGameSettings getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new CGameSettings();
		return uniqueInstance;
	}
	
	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public int getMoveNumber() {
		return moveNumber;
	}

	public void setMoveNumber(int moveNumber) {
		this.moveNumber = moveNumber;
	}

	public void writeSettings(String fileName) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(fileName);
			
			out.println("timer: " + this.timer);
			out.println("movenumber: " + this.moveNumber);
			
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
				Integer integerValue = null;
				try {
					integerValue = Integer.valueOf(value);
				} catch (NumberFormatException e) {
				}
				if (integerValue != null)
					changeSetting(name, integerValue);
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

	void changeSetting(String name, int newvalue) {
		if (name.equals("timer")) {
			this.timer = newvalue;
		} else if (name.equals("movenumber")) {
			this.moveNumber = newvalue;
		}
	}

	void changeSetting(String name, String newvalue) {

	}
}