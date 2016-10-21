package chess;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  // awt.* does not import Action or Event Listeners
import java.io.*;
import javax.imageio.*;

public class LoadPanel extends JPanel {

	private JButton enter;
	private JButton cancel;
	private JLabel prompt;
	private JTextField fileName;;

	private int promptTextSize = 24;
	private int buttonTextSize = 16;
	private int textFieldSize = 16;

	public LoadPanel() {
		GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);
		this.setBackground(Color.WHITE);

		GridBagConstraints gbc = new GridBagConstraints();

		//Adding prompt
		prompt = new JLabel("Enter save file name with extention:");
		prompt.setHorizontalAlignment(JLabel.CENTER);
		prompt.setFont(new Font("Arial", Font.BOLD, promptTextSize));

		gbc.insets = new Insets(5, 5, 5, 5);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.ipady = 15;
		this.add(prompt, gbc);

		//Adding text field
		fileName = new JTextField(30);
		fileName.setFont(new Font("Arial", Font.PLAIN, textFieldSize));

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		this.add(fileName, gbc);

		//Add enter button to confirm text field input
		enter = new JButton("Confirm");
		enter.setAlignmentX(Component.CENTER_ALIGNMENT);
		enter.setFont(new Font("Arial", Font.BOLD, buttonTextSize));
		enter.addActionListener(loadChessGame());

		//Add cancel button to return back to the start up menu
		cancel = new JButton("Return");
		cancel.setAlignmentX(Component.CENTER_ALIGNMENT);
		cancel.setFont(new Font("Arial", Font.BOLD, buttonTextSize));
		cancel.addActionListener(returnBackToStartup());

		gbc.ipadx = 150;

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		this.add(enter, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		this.add(cancel, gbc);
	}

	private ActionListener loadChessGame() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			// Placeholder for when we add functionality
				//Load and read the text file so it can be displayed onto the board
				//And accepted by stockfish

				System.out.println("Entered File Name: " + fileName.getText());

				//Run the chessboard
				ConsoleGraphics chessboard = new ConsoleGraphics();

				// TODO: set controller to whoever's turn it should be
				// LaboonChess.controller = new TurnController('w'); or 'b'
				// LaboonChess.controller.addGraphicalTurn(chessboard);

				//Make load frame not visible after user clicks new game
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(enter.getParent());
				frame.dispose();
			}
		};
		return action;
	}

	private ActionListener returnBackToStartup() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			// Placeholder for when we add functionality
				StartUpMenu start = new StartUpMenu();
				//Make load frame not visible after user clicks load
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(cancel.getParent());
				frame.dispose();
			}
		};
		return action;
	}
}
