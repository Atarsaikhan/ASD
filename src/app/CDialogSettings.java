package app;

import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class CDialogSettings extends Dialog<CGameSettings>{

	private CGameSettings gameSettings;
	
	public CDialogSettings(String configFileName){
		super();
		gameSettings = CGameSettings.getInstance();
		gameSettings.readSettings(configFileName);
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
    	 
    	this.setResultConverter(new Callback<ButtonType, CGameSettings>() {
    	    @Override
    	    public CGameSettings call(ButtonType b) {
    	 
    	        if (b == buttonTypeOk) {
    	        	
    				Integer intgerValue = null;
    				try {
    					intgerValue = Integer.valueOf(text1.getText());
    				} catch (NumberFormatException e) {
    				}
    	        	
    	        	gameSettings.setTimer(intgerValue);
    	        	
    				intgerValue = null;
    				try {
    					intgerValue = Integer.valueOf(text2.getText());
    				} catch (NumberFormatException e) {
    				}
    	        	gameSettings.setMoveNumber(intgerValue);
    	        	
    	        	gameSettings.writeSettings(configFileName);
    	            return gameSettings;
    	        }
    	 
    	        return null;
    	    }
    	});
	}
}
