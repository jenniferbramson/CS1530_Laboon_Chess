package chess;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  // awt.* does not import Action or Event Listeners
import java.io.*;
import javax.imageio.*;

public class ChooseColorPanel extends JPanel {

	private JButton white;
	private JButton black;
	private JButton backToMenu;
	private JButton title;

	//Variables to change the spacing of buttons and title
	private int topSpace = 50;
	private int buttonSpace = 10;

	private int titleTextSize = 48;
	private int buttonTextSize = 24;

	public ChooseColorPanel() {
		BoxLayout box = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(box);
		this.setBackground(Color.WHITE);

		//Add spacing from top of title and top of window
		this.add(Box.createRigidArea(new Dimension(0,topSpace)));

		//Add Image/modify title
		title = new JButton("Choose color to play!");
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

		//Add white pieces selection button
		white = new JButton("White");
		white.setAlignmentX(Component.CENTER_ALIGNMENT);
		white.setFont(new Font("Arial", Font.BOLD, buttonTextSize));

		//Set an icon for white pawn or king

		white.addActionListener(playWhite());
		this.add(white);

		//Add spacing between buttons
		this.add(Box.createRigidArea(new Dimension(0,buttonSpace)));

		//Add black pieces selection button
		black = new JButton("Black");
		black.setAlignmentX(Component.CENTER_ALIGNMENT);
		black.setFont(new Font("Arial", Font.BOLD, buttonTextSize));

		//Set icon black pawn/king

		black.addActionListener(playBlack());
		this.add(black);

		//Add spacing between buttons
		this.add(Box.createRigidArea(new Dimension(0,buttonSpace)));

		//Add back to menu button
		backToMenu = new JButton("Back to Menu");
		backToMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
		backToMenu.setFont(new Font("Arial", Font.BOLD, buttonTextSize));
		backToMenu.addActionListener(returnToStartUp());
		this.add(backToMenu);
	}

	private ActionListener playWhite() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			// Placeholder for when we add functionality
			//TODO - Pass in color to console graphics
				//Run the chessboard

				ConsoleGraphics chessboard = new ConsoleGraphics();

				LaboonChess.controller = new TurnController();   // default starts as white
				LaboonChess.controller.addGraphicalTurn(chessboard);

				//Dispose color choice frame after user picks color
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(white.getParent());
				frame.dispose();
			}
		};
		return action;
	}

	private ActionListener playBlack() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			// Placeholder for when we add functionality
			//TODO - Pass in color to console graphics

				ConsoleGraphics chessboard = new ConsoleGraphics();

				LaboonChess.controller = new TurnController();   // default starts as white
				LaboonChess.controller.addGraphicalTurn(chessboard);

				//Dispose color choice frame after user picks color
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(black.getParent());
				frame.dispose();
			}
		};
		return action;
	}

	private ActionListener returnToStartUp() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			// Placeholder for when we add functionality
				StartUpMenu start = new StartUpMenu();
				//Dispose color choice panel after user clicks to return to previous menu
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(backToMenu.getParent());
				frame.dispose();
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
