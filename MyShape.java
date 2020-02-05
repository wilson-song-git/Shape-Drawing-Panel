import java.awt.*;

abstract class MyShape implements java.io.Serializable{
	private int x1,x2,y1,y2; // variables for the coordinates of the shape
	private Color color; // color of the shape
	
	//a default public constrcutor that sets everything to 0 and color to black
	public MyShape() {
		x1 = 0;
		x2 = 0;
		y1 = 0;
		y2 = 0;
		color = Color.BLACK; 
	}
	
	public MyShape(int x1, int x2, int y1, int y2, Color color) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y2;
		this.y2 = y2;
		this.color = color;
	}
	
	//method for drawing the shape
	abstract public void draw(Graphics g);
	
	//setter methods
	public void setX1(int x1) {
		this.x1 = x1;
	}
	
	public void setX2(int x2) {
		this.x2 = x2;
	}
	
	public void setY1(int y1) {
		this.y1 = y1;
	}
	
	public void setY2(int y2) {
		this.y2 = y2;
	}
	
	//getter methods
	
	public int getX1() {
		return x1;
		
	}
	
	public int getX2() {
		return x2;
	}
	
	public int getY1() {
		return y1;
	}
	
	public int getY2() {
		return y2;
	}
	
	public Color getColor() {
		return color;
	}
	
	
}