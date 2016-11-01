package chess;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  // awt.* does not import Action or Event Listeners
import java.io.*;
import javax.imageio.*;

public class PawnPromotion extends JPanel {
	
	private Toolkit t;
	private Dimension screen;
	
	//Modify startup screen sizes
	private int screenWidth = 800;
	private int screenHeight = 400;
	
	public PawnPromotion() {
		JFrame frame = new JFrame("Pawn Promotion");
		Container content = frame.getContentPane();

		PawnPromotionPanel promotePanel = new PawnPromotionPanel();
		
		JPanel mainPanel = new JPanel();
		
		//Room to add anything else
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(promotePanel, BorderLayout.CENTER);
		
		content.add(mainPanel);
		
		frame.pack();
		
		//Set fixed screen size
		frame.setSize(screenWidth, screenHeight);
		
		//Get size of computer screen
		//Set the screen so it appears in the middle
		t = getToolkit();
		screen = t.getScreenSize();
		frame.setLocation(screen.width/2-frame.getWidth()/2,screen.height/2-frame.getHeight()/2);
		
		//User can't decline pawn promotion, forces selection on what to 
		//promote the pawn into
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		frame.setVisible(true);                    
	}
	
	public static void main(String[] args) {
		PawnPromotion promote = new PawnPromotion();
	}
}
	