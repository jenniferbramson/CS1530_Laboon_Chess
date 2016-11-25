package chess;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  // awt.* does not import Action or Event Listeners
import java.io.*;
import javax.imageio.*;

public class ChooseDifficultyPanel extends JPanel {

	private JButton easy;
	private JButton medium;
	private JButton hard;
	private JButton expert;
	private JButton title;
	private JButton backToMenu;

	//Variables to change the spacing of buttons and title
	private int topSpace = 50;
	private int buttonSpace = 10;

	private int titleTextSize = 48;
	private int buttonTextSize = 24;

	public ChooseDifficultyPanel() {
		BoxLayout box = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(box);
		this.setBackground(Color.WHITE);

		//Add spacing from top of title and top of window
		this.add(Box.createRigidArea(new Dimension(0,topSpace)));

		title = new JButton("");
		//Add Title image here
		try {

			//TODO: get image for title
			Image img = ImageIO.read(getClass().getResource(""));
			title.setIcon(new ImageIcon(img));
			title.setAlignmentX(Component.CENTER_ALIGNMENT);
			title.setBorder(null);

		} catch (Exception ex) {
			title = new JButton("Choose difficulty level");
			title.setOpaque(true);
			title.setBackground(Color.WHITE);
			title.setBorder(null);
			title.setAlignmentX(Component.CENTER_ALIGNMENT);
			//Size of title scales with size of font
			//title.setPreferredSize(new Dimension(500, 300));
			title.setFont(new Font("Arial", Font.BOLD, titleTextSize));
		}

		title.addActionListener(getFeature());
		this.add(title);

		//Add spacing between buttons
		this.add(Box.createRigidArea(new Dimension(0,buttonSpace)));

		//Add white pieces selection button
		easy = new JButton("");
		try{
			Image img = ImageIO.read(getClass().getResource(""));
			easy.setIcon(new ImageIcon(img));
			easy.setBorder(null);
		} catch(Exception e){
			easy = new JButton("EASY");
			easy.setAlignmentX(Component.CENTER_ALIGNMENT);
			easy.setFont(new Font("Arial", Font.BOLD, buttonTextSize));
		}
		//Set an icon for white pawn or king
		easy.setAlignmentX(Component.CENTER_ALIGNMENT);
		easy.addActionListener(playEasy());
		this.add(easy);

		//Add spacing between buttons
		this.add(Box.createRigidArea(new Dimension(0,buttonSpace)));

		//Add black pieces selection button
		medium = new JButton("");
		try{
			Image img = ImageIO.read(getClass().getResource(""));
			medium.setIcon(new ImageIcon(img));
			medium.setBorder(null);
		} catch(Exception e){
			medium = new JButton("MEDIUM");
			medium.setFont(new Font("Arial", Font.BOLD, buttonTextSize));
		}
		//Set icon medium pawn/king
		medium.setAlignmentX(Component.CENTER_ALIGNMENT);

		medium.addActionListener(playMedium());
		this.add(medium);

		//Add spacing between buttons
		this.add(Box.createRigidArea(new Dimension(0,buttonSpace)));

		//Add black pieces selection button
		hard = new JButton("");
		try{
			Image img = ImageIO.read(getClass().getResource(""));
			hard.setIcon(new ImageIcon(img));
			hard.setBorder(null);
		} catch(Exception e){
			hard = new JButton("hard");
			hard.setFont(new Font("Arial", Font.BOLD, buttonTextSize));
		}
		//Set icon hard pawn/king
		hard.setAlignmentX(Component.CENTER_ALIGNMENT);

		hard.addActionListener(playHard());
		this.add(hard);

		//Add spacing between buttons
		this.add(Box.createRigidArea(new Dimension(0,buttonSpace)));

		//Add black pieces selection button
		expert = new JButton("");
		try{
			Image img = ImageIO.read(getClass().getResource(""));
			expert.setIcon(new ImageIcon(img));
			expert.setBorder(null);
		} catch(Exception e){
			expert = new JButton("expert");
			expert.setFont(new Font("Arial", Font.BOLD, buttonTextSize));
		}
		//Set icon expert pawn/king
		expert.setAlignmentX(Component.CENTER_ALIGNMENT);

		expert.addActionListener(playExpert());
		this.add(expert);





		//Add spacing between buttons
		this.add(Box.createRigidArea(new Dimension(0,buttonSpace)));

		//Add back to menu button
		backToMenu = new JButton("");
		try{
			Image img = ImageIO.read(getClass().getResource("/BackToMenu.png"));
			backToMenu.setIcon(new ImageIcon(img));
			backToMenu.setBorder(null);
		} catch(Exception e){
			backToMenu = new JButton("Back to Menu");
			backToMenu.setFont(new Font("Arial", Font.BOLD, buttonTextSize));
		}
		backToMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
		backToMenu.addActionListener(returnToStartUp());
		this.add(backToMenu);
	}

	private ActionListener playEasy() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				LaboonChess.stockfish.setDifficultyLevel(DifficultyLevel.EASY);
				ChooseColor choice = new ChooseColor();

				//Dispose difficulty choice frame after selection
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(easy.getParent());
				frame.dispose();
			}
		};
		return action;
	}

	private ActionListener playMedium() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				LaboonChess.stockfish.setDifficultyLevel(DifficultyLevel.MEDIUM);
				ChooseColor choice = new ChooseColor();

				//Dispose difficulty choice frame after selection
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(easy.getParent());
				frame.dispose();
			}
		};
		return action;
	}


	private ActionListener playHard() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				LaboonChess.stockfish.setDifficultyLevel(DifficultyLevel.HARD);
				ChooseColor choice = new ChooseColor();

				//Dispose difficulty choice frame after selection
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(easy.getParent());
				frame.dispose();
			}
		};
		return action;
	}


	private ActionListener playExpert() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				LaboonChess.stockfish.setDifficultyLevel(DifficultyLevel.EXPERT);
				ChooseColor choice = new ChooseColor();

				//Dispose difficulty choice frame after selection
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(easy.getParent());
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
