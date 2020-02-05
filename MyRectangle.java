import java.awt.*;

public class MyRectangle extends MyBoundedShape{
	
	//default constructor
	public MyRectangle() {
		super();
	}
	
	//contructor that takes in coordiantes , color and fill
	public MyRectangle(int x1, int x2, int y1, int y2, Color color, boolean fill) {
		super(x1,x2,y1,y2,color,fill);
	}
	
	//drawing method that will be overridden
	@Override
	public void draw(Graphics g) {
		
		g.setColor(getColor());
		
		if(getFill()== true)
			g.fillRect(getUpperX(), getUpperY(), getWidth(), getLength());
		else
			g.drawRect(getUpperX(), getUpperY(), getWidth(), getLength());
	}
	
	
}