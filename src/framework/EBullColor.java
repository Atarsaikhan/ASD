package framework;

public enum EBullColor {
	WHITE("0"), BLACK("X"), NONE("?"); //buudal
	
	private String color;

	EBullColor(String color) {
		this.color = color;
	}
	
	public String value() {
		return color;
	}
}
