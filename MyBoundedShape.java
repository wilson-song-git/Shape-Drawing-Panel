import java.awt.*;

abstract class MyBoundedShape extends MyShape{
	
	private boolean fill;
	
	//default constructor
	public MyBoundedShape() {
		super();
		fill = false;
	}
	
	//constructor
	public MyBoundedShape(int x1, int x2, int y1, int y2, Color color, boolean fill) {
		super(x1,x2,y1,y2,color);
		this.fill = fill;
	}
	
	//method for drawing the shape 
	abstract public void draw(Graphics g);
	
	public void setFill(boolean fill) {
		this.fill = fill;
	}
	//methods that will return the upper left x and y coordinates of the shape
	public int getUpperX() {
		return Math.min((getX1()), getX2());
	}
	
	public int getUpperY() {
		return Math.min(getY1(),getY2());
	}
	
	//mthods to get width and height of the shape
	
	public int getWidth() {
		return Math.abs(getX1()-getX2());
	}
	
	public int getLength() {
		return Math.abs(getY1()-getY2());
	}
	
	//method to get fill variable
	public boolean getFill() {
		return fill;
	}
	
	

	
	
}