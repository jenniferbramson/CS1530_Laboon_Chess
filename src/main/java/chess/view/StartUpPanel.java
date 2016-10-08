package chess;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  // awt.* does not import Action or Event Listeners
import java.io.*;
import javax.imageio.*;

public class StartUpPanel extends JPanel {
	
	private JButton newGame;
	private JButton load;
	private JButton exit;
	private JButton title;
	
	//Variables to change the spacing of buttons and title
	private int topSpace = 100;
	private int buttonSpace = 10;
	
	private int titleTextSize = 96;
	private int buttonTextSize = 36;
	
	public StartUpPanel() {
		BoxLayout box = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(box);
		this.setBackground(Color.WHITE);
		
		//Add spacing from top of title and top of window
		this.add(Box.createRigidArea(new Dimension(0,topSpace)));
		
		//Add Image/modify title
		title = new JButton("Laboon's Chess");
		title.setOpaque(true);
		title.setBackground(Color.WHITE);
		title.setBorder(null);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		//Size of title scales with size of font
		//title.setPreferredSize(new Dimension(500, 300));
		title.setFont(new Font("Arial", Font.BOLD, titleTextSize));
		
		/* Add Title image here
		try {
			Image img = ImageIO.read(getClass().getResource("/ImageName.what"));
			//Modify the values to get desired pixel width/height
			img = img.getScaledInstance(32, 32, Image.SCALE_DEFAULT);
			title.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
		// Error
		}
		*/
		
		title.addActionListener(getFeature());
		this.add(title);
		
		//Add spacing between buttons
		this.add(Box.createRigidArea(new Dimension(0,buttonSpace)));
		
		//Add new game button
		newGame = new JButton("New Game");
		newGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		newGame.setFont(new Font("Arial", Font.BOLD, buttonTextSize));
		newGame.addActionListener(startNewGame());
		this.add(newGame);
		
		//Add spacing between buttons
		this.add(Box.createRigidArea(new Dimension(0,buttonSpace)));
		
		//Add load game button
		load = new JButton("Load");
		load.setAlignmentX(Component.CENTER_ALIGNMENT);
		load.setFont(new Font("Arial", Font.BOLD, buttonTextSize));
		load.addActionListener(getLoadGame());
		this.add(load);
		
		//Add spacing between buttons
		this.add(Box.createRigidArea(new Dimension(0,buttonSpace)));
		
		//Add exit button
		exit = new JButton("Exit");
		exit.setAlignmentX(Component.CENTER_ALIGNMENT);
		exit.setFont(new Font("Arial", Font.BOLD, buttonTextSize));
		exit.addActionListener(exitMenu());
		this.add(exit);
	}
	
	private ActionListener startNewGame() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			// Placeholder for when we add functionality
				//Run the chessboard
				ConsoleGraphics chessboard = new ConsoleGraphics();
				//Make startup frame not visible after user clicks new game
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(newGame.getParent());
				frame.setVisible(false);
			}
		};
		return action;
	}
	
	private ActionListener getLoadGame() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			// Placeholder for when we add functionality
				ConsoleGraphics chessboard = new ConsoleGraphics();
				//Make startup frame not visible after user clicks load
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(load.getParent());
				frame.setVisible(false);
			}
		};
		return action;
	}
	
	private ActionListener exitMenu() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			// Placeholder for when we add functionality
				System.exit(0);
			}
		};
		return action;
	}
	
	private ActionListener getFeature() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			// Placeholder for when we add functionality

			}
		};
		return action;
	}
}