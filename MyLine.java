import java.awt.*;

public class MyLine extends MyShape{
	
	
	//default constructor
	public MyLine() {
		super();
	}
	
	//contructor that takes in coordinates and the color for drawing the line
	public MyLine(int x1, int x2, int y1, int y2, Color color) {
		super(x1,x2,y1,y2,color);
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawLine(getX1(), getY1(), getX2(), getY2());
	}
}