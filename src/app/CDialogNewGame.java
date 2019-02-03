package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	private CheckBox chkToCapture;

	@FXML
	private RadioButton rdbFourBulls;

	@FXML
	private ToggleGroup gameType;

	@FXML
	private RadioButton rdbYurt;
	
    @FXML
    private ImageView imgFBulls;

    @FXML
    private ImageView imgYurt;

	private Stage dialogStage;
	private boolean newGameClicked = false;
	private String playerName1;
	private String playerName2;
	private boolean isCaptureGame;
	private int selectedGame;

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		imgFBulls.setImage(new Image("resources/images/icon4bulls.png"));
		imgYurt.setImage(new Image("resources/images/iconYurt.png"));
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
		this.isCaptureGame = chkToCapture.isSelected();
		
		this.selectedGame = 0;
		if (rdbYurt.selectedProperty().get()) {
			this.selectedGame = 1;
			System.out.println("Yurt.isSelected selectedGame:"+this.selectedGame);
		}
			

		newGameClicked = true;
		dialogStage.close();
	}

	public void setValues(String playerName1, String playerName2, boolean toCapture, int choiceIndex) {
		this.playerName1 = playerName1;
		this.playerName2 = playerName2;
		this.isCaptureGame = toCapture;
		this.selectedGame = choiceIndex;

		txtPlayerName1.setText(playerName1);
		txtPlayerName2.setText(playerName2);
		chkToCapture.setSelected(toCapture);

		if (selectedGame == 0)
			rdbFourBulls.setSelected(true);
		else
			rdbYurt.setSelected(false);
	}

	public String getPlayerName1() {
		return playerName1;
	}

	public String getPlayerName2() {
		return playerName2;
	}

	public boolean isCaptureGame() {
		return isCaptureGame;
	}

	public int selectedGame() {
		return selectedGame;
	}

}
