package gui;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CDialogNewGame {

    @FXML
    private TextField txtPlayerName1;

    @FXML
    private Button btnStartNewGame;

    @FXML
    private TextField txtPlayerName2;

    @FXML
    private Button btnCancel;

    @FXML
    private ChoiceBox<String> chsGameType;

    @FXML
    private CheckBox chkToCapture;
    
    private Stage dialogStage;
    private boolean newGameClicked = false;
    private String playerName1;
    private String playerName2;
    private boolean toCapture;
    private int choiceIndex;
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        ObservableList<String> gameTypes = FXCollections.observableArrayList();
        gameTypes.add("Four Bulls");
        gameTypes.add("Yurt");
        chsGameType.setItems(gameTypes);
    }

    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isNewGameClicked() {
        return newGameClicked;
    }
    
    /**
     * Called when the user clicks cancel.
     */
    @FXML
    void onCancelClick(ActionEvent event) {
        newGameClicked = false;
        dialogStage.close();
    }
    
    /**
     * Called when the user clicks to start a new game.
     */
    @FXML
    void onStartNewGameClick(ActionEvent event) {
    	this.playerName1 = txtPlayerName1.getText();
    	this.playerName2 = txtPlayerName2.getText();
    	this.toCapture = chkToCapture.isSelected();
    	this.choiceIndex = chsGameType.getSelectionModel().getSelectedIndex();
    	
        newGameClicked = true;
        dialogStage.close();
    }
    
    public void setValues(String playerName1, String playerName2, boolean toCapture, int choiceIndex) {
    	this.playerName1 = playerName1;
    	this.playerName2 = playerName2;
    	this.toCapture = toCapture;
    	this.choiceIndex = choiceIndex;

    	txtPlayerName1.setText(playerName1);
    	txtPlayerName2.setText(playerName2);
    	chkToCapture.setSelected(toCapture);
    	chsGameType.getSelectionModel().select(choiceIndex);
    }

	public String getPlayerName1() {
		return playerName1;
	}

	public String getPlayerName2() {
		return playerName2;
	}

	public boolean isToCapture() {
		return toCapture;
	}

	public int getChoiceIndex() {
		return choiceIndex;
	}

}
