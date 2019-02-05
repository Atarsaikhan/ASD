package framework;

import java.util.HashMap;

public class CMemento {
	private HashMap<Integer, EBullColor> posColors;
	private String player1Name;
	private String player2Name;
	private int player1PieceCount;
	private int player2PieceCount;
	private EBullColor current;

	public CMemento(HashMap<Integer, EBullColor> posColors
			,String player1Name,String player2Name,int player1PieceCount
			,int player2PieceCount, EBullColor current) {
		this.posColors = posColors;
		this.player1Name = player1Name;
		this.player2Name = player2Name;
		this.player1PieceCount = player1PieceCount;
		this.player2PieceCount = player2PieceCount;
		this.current = current;
	}

	public HashMap<Integer, EBullColor> getPosColors() {
		return posColors;
	}

	public void setPosColors(HashMap<Integer, EBullColor> posColors) {
		this.posColors = posColors;
	}

	public String getWhiteName() {
		return player1Name;
	}

	public void setPlayer1Name(String player1Name) {
		this.player1Name = player1Name;
	}

	public String getBlackName() {
		return player2Name;
	}

	public void setPlayer2Name(String player2Name) {
		this.player2Name = player2Name;
	}

	public int getWhitePieceCount() {
		return player1PieceCount;
	}

	public void setPlayer1PieceCount(int player1PieceCount) {
		this.player1PieceCount = player1PieceCount;
	}

	public int getBlackPieceCount() {
		return player2PieceCount;
	}

	public void setPlayer2PieceCount(int player2PieceCount) {
		this.player2PieceCount = player2PieceCount;
	}

	public EBullColor getCurrent() {
		return current;
	}

	public void setCurrent(EBullColor current) {
		this.current = current;
	}

}