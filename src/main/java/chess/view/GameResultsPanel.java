package chess;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  // awt.* does not import Action or Event Listeners
import java.io.*;
import javax.imageio.*;

public class GameResultsPanel extends JPanel {
	//TODO - Add draw conditions
	//	Decide on layout and functionality
	
	private JButton newGame;
	private JButton load;
	private JButton exit;
	private JButton title;
	
	//Variables to change the spacing of buttons and title
	private int topSpace = 50;
	private int buttonSpace = 10;
	
	private int titleTextSize = 20;
	private int buttonTextSize = 16;
	
	private String winMessage = "You Won the Game!";
	private String lossMessage = "You Lost the Game!";
	private String drawMessage = "The Game was a Draw!";
	
	public GameResultsPanel() {
		BoxLayout box = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(box);
		this.setBackground(Color.WHITE);
		
		//Add spacing from top of title and top of window
		this.add(Box.createRigidArea(new Dimension(0,topSpace)));
		
		//Add Image/modify title
		title = new JButton("");
		title.setOpaque(true);
		title.setBackground(Color.WHITE);
		title.setBorder(null);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		//Size of title scales with size of font
		//title.setPreferredSize(new Dimension(500, 300));
		title.setFont(new Font("Arial", Font.BOLD, titleTextSize));
		
		//Indicates which color couldn't make any moves
		char turn = LaboonChess.getTurn();
		
		//Indicates the color that the user selected to play as
		char playercolor = LaboonChess.controller.playersColor;
		
		//Add Title image
		//Set title as the result of the game
		//Could do checks if party parrot theme is used to give different look
		try {
			//If statement to select image based on results of the game
			Image img = ImageIO.read(getClass().getResource("/GameResults.png"));
			title.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			//If the user played as black and it was whites turn
			//Indicates that white had no possible moves, therefore player wins
			if(turn == 'w' && playercolor == 'b') {
				title.setText(winMessage);
			}
			//Ai had no possible moves, therefore player wins
			else if(turn == 'b' && playercolor == 'w') {
				title.setText(winMessage);
			}
			//Player has no possible moves, therefore they lost
			else if(turn == 'b' && playercolor == 'b') {
				title.setText(lossMessage);
			}
			else if(turn == 'w' && playercolor == 'w') {
				title.setText(lossMessage);
			}
			else {
				title.setText(drawMessage);
			}
		}
		
		title.addActionListener(getFeature());
		this.add(title);
		
		//Add spacing between buttons
		this.add(Box.createRigidArea(new Dimension(0,buttonSpace)));
		
		//Add new game button
		newGame = new JButton("");
		newGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		newGame.setFont(new Font("Arial", Font.BOLD, buttonTextSize));
		newGame.addActionListener(startNewGame());
		//new game button image
		try {
			Image ngImg = ImageIO.read(getClass().getResource("/NewGame.png"));
			newGame.setIcon(new ImageIcon(ngImg ));
			//get rid of button background stuff
			newGame.setBackground(Color.WHITE);
			newGame.setBorder(null);
		} catch (IOException ex) {
			newGame = new JButton("New Game");
		}
		this.add(newGame);
		
		//Add spacing between buttons
		this.add(Box.createRigidArea(new Dimension(0,buttonSpace)));
		
		//Add load game button
		load = new JButton("");
		load.setAlignmentX(Component.CENTER_ALIGNMENT);
		load.setFont(new Font("Arial", Font.BOLD, buttonTextSize));
		load.addActionListener(getLoadGame());
		//add the load game image button
		try {
			Image lgImg = ImageIO.read(getClass().getResource("/LoadGame.png"));
			load.setIcon(new ImageIcon(lgImg));
			//get rid of button background stuff
			load.setBackground(Color.WHITE);
			load.setBorder(null);
		} catch (IOException ex) {
			load = new JButton("Load Game");
		}
		this.add(load);
		
		//Add spacing between buttons
		this.add(Box.createRigidArea(new Dimension(0,buttonSpace)));
		
		//Add exit button
		exit = new JButton("");
		exit.setAlignmentX(Component.CENTER_ALIGNMENT);
		exit.setFont(new Font("Arial", Font.BOLD, buttonTextSize));
		exit.addActionListener(exitMenu());
		//add the exit image button
		try {
			Image exitImg = ImageIO.read(getClass().getResource("/Exit.png"));
			exit.setIcon(new ImageIcon(exitImg));
			//get rid of button background stuff
			exit.setBackground(Color.WHITE);
			exit.setBorder(null);
		} catch (IOException ex) {
			exit = new JButton("Exit");
		}
		this.add(exit);
	}
	
	private ActionListener startNewGame() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			// Placeholder for when we add functionality
				//Run the color selection for chess game
				ChooseColor choice = new ChooseColor();
				//Dispose of startup screen so it's not visible
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(newGame.getParent());
				frame.dispose();
				
				boolean checkChessboardVisible;
				
				//Try to see if the board is open/visible
				try {
					checkChessboardVisible = ConsoleGraphics.frame.isShowing();
				}
				catch(NullPointerException ex) {
					checkChessboardVisible = false;
				}
				
				if(checkChessboardVisible != false) {
					//Remove previous chessboard before creating the new one
					//If there is some lag when loading the images
					//Might just call drawpieces on the current board with new fen
					ConsoleGraphics.frame.dispose();
				}
			}
		};
		return action;
	}
	
	private ActionListener getLoadGame() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			// Placeholder for when we add functionality
				LoadGame loadGame = new LoadGame();
				//Dispose of startup screen so it's not visible
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(load.getParent());
				frame.dispose();
				
				boolean checkChessboardVisible;
				
				//Try to see if the board is open/visible
				try {
					checkChessboardVisible = ConsoleGraphics.frame.isShowing();
				}
				catch(NullPointerException ex) {
					checkChessboardVisible = false;
				}
				
				if(checkChessboardVisible != false) {
					//Remove previous chessboard before creating the new one
					//If there is some lag when loading the images
					//Might just call drawpieces on the current board with new fen
					ConsoleGraphics.frame.dispose();
				}
			}
		};
		return action;
	}
	
	private ActionListener exitMenu() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			// Placeholder for when we add functionality
			//Exit/terminate program if user clicks on exit
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