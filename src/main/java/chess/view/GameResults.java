package chess;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  // awt.* does not import Action or Event Listeners
import java.io.*;
import javax.imageio.*;

public class GameResults extends JDialog {
	
	private Toolkit t;
	private Dimension screen;
	
	//Modify startup screen sizes
	private int screenWidth = 800;
	private int screenHeight = 400;
	
	public GameResults() {
		//Find out whether the game was won, lost, draw
		//Make dialog title based on the results
		JDialog dialog = new JDialog(this, "Results of the Game!");
		dialog.setModal(true);
		Container content = dialog.getContentPane();

		GameResultsPanel resultsPanel = new GameResultsPanel();
		
		JPanel mainPanel = new JPanel();
		
		//Room to add anything else
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(resultsPanel, BorderLayout.CENTER);
		
		content.add(mainPanel);
		
		dialog.pack();
		
		//Set fixed screen size
		dialog.setSize(screenWidth, screenHeight);
		
		//Get size of computer screen
		//Set the screen so it appears in the middle
		t = getToolkit();
		screen = t.getScreenSize();
		dialog.setLocation(screen.width/2-dialog.getWidth()/2,screen.height/2-dialog.getHeight()/2);
		
		//Causes user to have to select one fo the options of the menu
		dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		dialog.setVisible(true);                    
	}
	
	public static void main(String[] args) {
		GameResults results = new GameResults();
	}
}
	