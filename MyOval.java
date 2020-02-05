import java.awt.*;

public class MyOval extends MyBoundedShape{
	
	//default construtor
	public MyOval(){
		super();
	}
	
	public MyOval(int x1, int x2, int y1, int y2, Color color, boolean fill) {
		super(x1,x2,y1,y2,color,fill);
	}
	
	@Override
	public void draw(Graphics g) {
		
		g.setColor(getColor());
		if(getFill() == true) 
			g.fillOval(getUpperX(), getUpperY(), getWidth(), getLength());
		else
			g.drawOval(getUpperX(), getUpperY(), getWidth(), getLength());
		
	}
}