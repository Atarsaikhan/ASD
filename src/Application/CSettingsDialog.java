package Application;

import javafx.scene.control.ButtonBar.ButtonData;

import java.io.File;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class CSettingsDialog extends Dialog<CSettingsGame>{
	public static final String SETTINGS_FILE=System.getProperty("user.dir") + File.separator + "src" + File.separator + "resources"
			+ File.separator+"config.txt";
	private CSettingsGame gameSettings;
	
	CSettingsDialog(){
		super();
		gameSettings = CSettingsGame.getInstance();
		gameSettings.readSettings(SETTINGS_FILE);
    	this.setTitle("Game Settings");
    	this.setHeaderText("Enter timer and move number \n" +
    	    "press Okay (or click title bar 'X' for cancel).");
    	this.setResizable(true);
    	 
    	Label label1 = new Label("Timer: ");
    	Label label2 = new Label("Move number: ");
    	TextField text1 = new TextField(gameSettings.getTimer()+"");
    	TextField text2 = new TextField(gameSettings.getMoveNumber()+"");
    
    	         
    	GridPane grid = new GridPane();
    	grid.add(label1, 1, 1);
    	grid.add(text1, 2, 1);
    	grid.add(label2, 1, 2);
    	grid.add(text2, 2, 2);
    	this.getDialogPane().setContent(grid);
    	         
    	ButtonType buttonTypeOk = new ButtonType("Okay", ButtonData.OK_DONE);
    	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
    	this.getDialogPane().getButtonTypes().add(buttonTypeOk);
    	this.getDialogPane().getButtonTypes().add(buttonTypeCancel);
    	 
    	this.setResultConverter(new Callback<ButtonType, CSettingsGame>() {
    	    @Override
    	    public CSettingsGame call(ButtonType b) {
    	 
    	        if (b == buttonTypeOk) {
    	        	
    				Double doubleValue = null;
    				try {
    					doubleValue = Double.valueOf(text1.getText());
    				} catch (NumberFormatException e) {
    				}
    	        	
    	        	gameSettings.setTimer(doubleValue);
    	        	
    				doubleValue = null;
    				try {
    					doubleValue = Double.valueOf(text2.getText());
    				} catch (NumberFormatException e) {
    				}
    	        	gameSettings.setMoveNumber(doubleValue);
    	        	
    	        	gameSettings.writeSettings(SETTINGS_FILE);
    	            return gameSettings;
    	        }
    	 
    	        return null;
    	    }
    	});
	}
}
