import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DrawPanel extends JPanel implements java.io.Serializable{
	
	private int currentType;
	private MyShape currentShape;
	private Color currentColor;
	private boolean currentFilled;
	
	JLabel status;
	
	private LinkedList<MyShape> addedShapes;
	private LinkedList<MyShape> clearedShapes;
	
	public DrawPanel(JLabel status) {
		this.status = status;
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		add(status, BorderLayout.SOUTH);
		
		
		addedShapes = new LinkedList<MyShape>();
		clearedShapes = new LinkedList<MyShape>();
		
		currentType = 0;
		currentShape = null;
		currentColor = Color.BLACK;
		currentFilled = false;
		
		MouseHandler handler = new MouseHandler();
		addMouseListener(handler);
		addMouseMotionListener(handler);
	}
	
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		
		for(int i =addedShapes.size()-1; i>=0; i--)
			addedShapes.get(i).draw(g);
		
		if(currentShape!=null)
			currentShape.draw(g);
		
	}
	
	
	//setter methods
	public void setCurrentType(int i) {
		currentType = i;
	}
	
	public void setCurrentColor(Color color) {
		currentColor = color;
	}
	
	public void setCurrentFilled(boolean f) {
		currentFilled = f;
	}
	
	//clears the last shape drawn
	public void clearLast() {
		if(!addedShapes.isEmpty()) {
			clearedShapes.addFirst(addedShapes.removeFirst());
			repaint();
		}
	}
	
	public void redoLastShape() {
		if(!clearedShapes.isEmpty()) {
			addedShapes.addFirst(clearedShapes.removeFirst());
			repaint();
		}
	}
	
	public void clear() {
		addedShapes.clear();
		clearedShapes.clear();
		repaint();
	}
	
	public LinkedList<MyShape> allShapes(){
		return addedShapes;
	}
	
	public void setShapes(LinkedList<MyShape> s) {
		addedShapes.clear();
		addedShapes = s;
		repaint();
	}
	
	public boolean isAddedShapes() {
		return addedShapes.isEmpty();
	}
	
	public boolean isClearedShapes() {
		return clearedShapes.isEmpty();
	}
	
	
	private class MouseHandler extends MouseAdapter{
		
		public void mousePressed(MouseEvent e) {
			
			switch(currentType) {
			
			case 0:
				currentShape = 
				new MyLine(e.getX(),e.getY(),e.getX(),e.getY(),currentColor);
				break;
			
			case 1:
				currentShape =
				new MyRectangle(e.getX(),e.getY(),e.getX(),e.getY(),currentColor,currentFilled);
				break;
			
			case 2:
				currentShape =
				new MyOval(e.getX(),e.getY(),e.getX(),e.getY(),currentColor,currentFilled);
				break;
			}
		}
		
		public void mouseReleased(MouseEvent e) {
			currentShape.setX2(e.getX());
			currentShape.setY2(e.getY());
			
			addedShapes.addFirst(currentShape);
			
			currentShape = null;
			clearedShapes.clear();
			repaint();
			
		}
		
		public void mouseMoved(MouseEvent e) {
			status.setText("Mouse Coordinates: "+e.getX()+", "+e.getY());
		}
		
		public void mouseDragged(MouseEvent e) {
			currentShape.setX2(e.getX());
			currentShape.setY2(e.getY());
			
			status.setText("Mouse Coordinates: "+e.getX()+", "+e.getY());
			repaint();
		}
		
	}
			
	
	
	
	
	
	
	
	
	
	
	
}