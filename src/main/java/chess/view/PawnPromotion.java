package chess;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  // awt.* does not import Action or Event Listeners
import java.io.*;
import javax.imageio.*;

public class PawnPromotion extends JDialog {
	
	private Toolkit t;
	private Dimension screen;
	
	//Modify startup screen sizes
	private int screenWidth = 800;
	private int screenHeight = 400;
	
	//Using JDialog and making it modal will pause any activity on the board
	//and will resume it after the user has selected what to promote the pawn into
	public PawnPromotion() {
		JDialog dialog = new JDialog(this, "Pawn Promotion");
		dialog.setModal(true);
		Container content = dialog.getContentPane();

		PawnPromotionPanel promotePanel = new PawnPromotionPanel();
		
		JPanel mainPanel = new JPanel();
		
		//Room to add anything else
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(promotePanel, BorderLayout.CENTER);
		
		content.add(mainPanel);
		
		dialog.pack();
		
		//Set fixed screen size
		dialog.setSize(screenWidth, screenHeight);
		
		//Get size of computer screen
		//Set the screen so it appears in the middle
		t = getToolkit();
		screen = t.getScreenSize();
		dialog.setLocation(screen.width/2-dialog.getWidth()/2,screen.height/2-dialog.getHeight()/2);
		
		//User can't decline pawn promotion, forces selection on what to 
		//promote the pawn into
		dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		dialog.setVisible(true);                    
	}
	
	public static void main(String[] args) {
		PawnPromotion promote = new PawnPromotion();
	}
}
	