package model;

public class Pixel {
	Double red;
	Double green;
	Double blue;
	Integer x;
	Integer y;
	
	public Pixel(Double red, Double green, Double blue, Integer x, Integer y) {
		super();
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.x = x;
		this.y = y;
	}

	public Double getRed() {
		return red;
	}

	public Double getGreen() {
		return green;
	}

	public Double getBlue() {
		return blue;
	}

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}

	public void setRed(Double red) {
		this.red = red;
	}

	public void setGreen(Double green) {
		this.green = green;
	}

	public void setBlue(Double blue) {
		this.blue = blue;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public void setY(Integer y) {
		this.y = y;
	}

}
