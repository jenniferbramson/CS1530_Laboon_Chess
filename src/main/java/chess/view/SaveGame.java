package chess;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  // awt.* does not import Action or Event Listeners
import java.io.*;
import javax.imageio.*;

public class SaveGame extends JPanel {
	
	private Toolkit t;
	private Dimension screen;
	
	//Modify startup screen sizes
	private int screenWidth = 600;
	private int screenHeight = 250;
	
	public SaveGame() {
		JFrame frame = new JFrame("Save Game");
		Container content = frame.getContentPane();

		SavePanel savePanel = new SavePanel();
		
		JPanel mainPanel = new JPanel();
		
		//Room to add anything else
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(savePanel, BorderLayout.CENTER);
		
		content.add(mainPanel);
		
		frame.pack();
		
		//Set fixed screen size
		frame.setSize(screenWidth, screenHeight);
		
		//Get size of computer screen
		//Set the screen so it appears in the middle
		t = getToolkit();
		screen = t.getScreenSize();
		frame.setLocation(screen.width/2-frame.getWidth()/2,screen.height/2-frame.getHeight()/2);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);                    
	}
	
	public static void main(String[] args) {
		SaveGame save = new SaveGame();
	}
}
	