package enums;

public enum PixelEnum {

	RED, 
	GREEN, 
	BLUE;

	public int getId() {
		return ordinal();
	}

	public String getName() {
		return name();
	}
	
}
