import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;


public class DrawFrame extends JFrame implements java.io.Serializable{
	
	private JButton undo; // button to undo
	private JButton redo;// button to redo
	private JButton clear; // button to clear
	private JComboBox colors; // combo box for colors
	private JComboBox shapes; // combo box for shapes
	private JCheckBox fill; // check box to determine whether fill or not
	private JLabel status; // label for current mouse coordinates
	private JTextArea logField;
	private JScrollPane log;
	
	private static DrawPanel panel; // Drawpanel class
	
	private JButton load; // button for loading
	private JButton save; // button for saving
	
	//a string array to input into the combobox
	private String colorChoices[] = {"Yellow", "White", "Red","Pink",
			"Orange","Magenta","Light Gray","Green","Gray","Dark Gray","Cyan","Blue","Black"};
	
	//a color array with the corresponding color codes to the string array to change the color
	private Color colorCodes[] = {Color.YELLOW, Color.WHITE, Color.RED, Color.PINK, Color.ORANGE,
			Color.MAGENTA, Color.LIGHT_GRAY, Color.GREEN, Color.GRAY, Color.DARK_GRAY, Color.CYAN, 
			Color.BLUE, Color.BLACK };
	
	//string array for the shapes to input into the combobox
	private String shapeChoices[] = {"Line", "Rectangle", "Oval"};
	
	//a jpanel to hold the buttons
	private JPanel buttonTray;
	private JPanel padder;
	private JFileChooser chooser; // jfilechooser allows user to chooser file to load/save
	
	public DrawFrame() {
		super("Drawing Application"); // super constructor
		status = new JLabel("");
		
		panel = new DrawPanel(status); //initilizes the panel with the status label
		
		// initlizaes all the buttons etc.
		undo = new JButton("Undo");
		redo = new JButton("Redo");
		clear = new JButton("Clear");
		load = new JButton("Load");
		save = new JButton("Save");
		
		logField = new JTextArea("Logs: \n");
		log = new JScrollPane(logField);
		log.setPreferredSize(new Dimension(200,500));
		
		fill = new JCheckBox("Fill");
	
		colors = new JComboBox(colorChoices);
		shapes = new JComboBox(shapeChoices);
		
		//adding tooltips
		undo.setToolTipText("Clears the last shape you drew.");
		redo.setToolTipText("Redraws the last shape you deleted.");
		clear.setToolTipText("Clears all the shapes on the drawing pad.");
		load.setToolTipText("Loads a drawing");
		save.setToolTipText("Save this drawing.");
		colors.setToolTipText("Dropdown menu to select the drawing color.");
		shapes.setToolTipText("Dropdown menu to select the shape to be drawn.");
		logField.setToolTipText("Logs will keep track of all your actions.");
		
		
		chooser = new JFileChooser(); //file chooser
		
		buttonTray = new JPanel(); //button tray holds the buttons
		padder = new JPanel();
		buttonTray.setLayout(new GridLayout(1,6,10,10));
		padder.setLayout(new FlowLayout(FlowLayout.LEADING,20,5)); //makes gaps for the tray
		
		//adding all buttons to the tray
		buttonTray.add(clear);
		buttonTray.add(undo);
		buttonTray.add(redo);
		buttonTray.add(colors);
		buttonTray.add(shapes);
		buttonTray.add(fill);
		buttonTray.add(load);
		buttonTray.add(save);
		padder.add(buttonTray);
		
		//adding log
		
		add(log,BorderLayout.EAST);
		
		add(padder,BorderLayout.NORTH);
		add(panel,BorderLayout.CENTER);
		
		//adding button handlers for all the buttons
		ButtonHandler buttonHandler = new ButtonHandler();
		undo.addActionListener(buttonHandler);
		redo.addActionListener(buttonHandler);
		clear.addActionListener(buttonHandler);
		load.addActionListener(buttonHandler);
		save.addActionListener(buttonHandler);
		
		//adding item handlers for the comboboxes and the checkbox
		ItemListenerHandler itemHandler = new ItemListenerHandler();
		colors.addItemListener(itemHandler);
		shapes.addItemListener(itemHandler);
		fill.addItemListener(itemHandler);
		
		//sets default close operation and sets size and sets visible
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1100,500);
		setVisible(true);
		
	}
	
	//actionlistener for clear, undo, redo, load, save
	private class ButtonHandler implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Clear")) {
				panel.clear(); // clears all
				logField.setText(logField.getText()+"All Cleared. \n"); //sets the log
			}
			else if(e.getActionCommand().equals("Undo")) {
				panel.clearLast(); //clears last shape
				if(panel.isAddedShapes())
					logField.setText(logField.getText()+"No Shape to be cleared. \n"); //sets log
				else
					logField.setText(logField.getText()+"Shape cleared. \n");
			}
			else if(e.getActionCommand().equals("Redo")) {
				
				if(panel.isClearedShapes()) //checks if array of cleared shapes is empty
					logField.setText(logField.getText()+"No Shape to redraw. \n");
				else
					logField.setText(logField.getText()+"Shape reDrawn. \n");
				panel.redoLastShape();
			}
			else if(e.getActionCommand().equals("Load")) {
				int result = chooser.showOpenDialog(panel); // will be used to determine that the user chooses  file
				if(result==JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile(); //gets the file location
					try {
						FileInputStream fn = new FileInputStream(file);
						ObjectInputStream in = new ObjectInputStream(fn);
						LinkedList<MyShape> temp = (LinkedList<MyShape>)in.readObject(); //reads the linkedlist of shapes
						in.close();//closes
						fn.close();
						panel.setShapes(temp); //sets the shapes that was read back onto the panel and repaints
						logField.setText(logField.getText()+"Loaded: "+file+"\n"); //logs
						}
						catch(Exception ei) {
							logField.setText(logField.getText()+ei); //logs exceptions
						}
					
				}
			}
			else if(e.getActionCommand().equals("Save")) {
				int result = chooser.showSaveDialog(panel);
				if(result==JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					try {
						FileOutputStream fo = new FileOutputStream(file);
						ObjectOutputStream out = new ObjectOutputStream(fo);
						out.writeObject(panel.allShapes()); //Serializes and outputs the linkedlist of shapes
						out.flush(); //flush and closes
						out.close();
					}
					catch(Exception ex) {
						logField.setText(logField.getText()+ex); //logs
					}
					logField.setText(logField.getText()+"Saved to: "+file+"\n"); // logs exceptions
				}
			}
		}
	}
	
	//item listener for changing color, shape, and selecting whether you want shape to be filled
	private class ItemListenerHandler implements ItemListener{
		
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange()==ItemEvent.SELECTED) {
				
				if(e.getSource()==colors) {
					panel.setCurrentColor(colorCodes[colors.getSelectedIndex()]); //sets current color to chosen color
					logField.setText(logField.getText()+"Color switched: "+colorChoices[colors.getSelectedIndex()]+"\n");
				}
				else if(e.getSource()==shapes) {
					panel.setCurrentType(shapes.getSelectedIndex());
					logField.setText(logField.getText()+"Shape switched: "+shapeChoices[shapes.getSelectedIndex()]+"\n");
				}
			}
			
			if(e.getSource()==fill) {
				if(fill.isSelected()) {
					panel.setCurrentFilled(true); 
					logField.setText(logField.getText()+"Drawn shapes will be filled\n");
				}
				else {
					panel.setCurrentFilled(false);
					logField.setText(logField.getText()+"Drawn shapes will be unfilled\n");
				}
			}
			
		}
	}

	
	
	
	
	

	
	
	
	
}