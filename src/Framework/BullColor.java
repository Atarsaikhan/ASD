package Framework;

public enum BullColor {
	WHITE("0"), BLACK("X"), NONE("?"); //buudal
	
	private String color;

	BullColor(String color) {
		this.color = color;
	}
	
	public String value() {
		return color;
	}
}
