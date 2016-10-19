package chess;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  // awt.* does not import Action or Event Listeners
import java.io.*;
import javax.imageio.*;
import java.util.*;
import java.lang.*;

public class LoadPanel extends JPanel {
	
	private JButton enter;
	private JButton cancel;
	private JLabel prompt;
	private JTextField fileName;
	
	protected static String fen;
	boolean checkChessboardVisible;
	
	private ArrayList<String> fileContents;
	private String tempLine;
	
	private String acceptedFileExtension = "txt";
	
	private int promptTextSize = 20;
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
				
				//Try to see if the board is open/visible
				try {
					checkChessboardVisible = ConsoleGraphics.frame.isShowing();
				}
				catch(NullPointerException ex) {
					checkChessboardVisible = false;
				}
				//Check if the board has been loaded
				//If not then it must be from the startup menu where the user clicked on load game
				if(checkChessboardVisible == false) {
					//Test field is not empty, attempt to load a file
					if(fileName.getText() != "") {
						String fileNamePath = "/" + fileName.getText();
						System.out.println("Display File Path: " + fileNamePath);
						
						fileContents = new ArrayList<String>();

						//Check file extensions before attempting to open the file
						//Have to do this because user could try to load a png file
						//Might have to move images into a subfolder in resources
						String checkExtension = fileNamePath.substring(fileNamePath.lastIndexOf(".") + 1, fileNamePath.length());
						
						//Only check if it's a text file for now
						//Change later if we plan to encrypt
						
						//File extension is accept, proceed to open the file
						if(checkExtension.equals(acceptedFileExtension)) {
							try {
								//Check if file exists
								File file = new File(this.getClass().getResource(fileNamePath).toURI());
								//Try to open contents of the file
								try {
									BufferedReader readFile = new BufferedReader(new FileReader(file));
									while((tempLine = readFile.readLine()) != null)
									{
										fileContents.add(tempLine);
									}
									readFile.close();
									
									//Get fen from the file
									fen = fileContents.get(0);
									
									//Read out the board in the file to make sure its the same
									//As what is displayed
									System.out.println("Read in fen from file: \n" + fen);
									for(int i = 1; i < 9; i++) {
										System.out.println("Row " + i + " : " + fileContents.get(i));
									}
									
									//Load chessboard
									ConsoleGraphics chessboard = new ConsoleGraphics();			

									//Make load frame not visible after user clicks load game
									JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(enter.getParent());
									frame.dispose();
									
								} catch (Exception eee) {
									System.out.println("OH NO DISASTER!");
									System.out.print("Exception: ");
									System.out.println(eee.getMessage());
								}
							} catch(Exception eeeee) {
								System.out.println("OH NO DISASTER!");
								System.out.print("Exception: ");
								System.out.println(eeeee.getMessage());
								prompt.setText("<html>ERROR: File doesn't exists!<br>Enter save file name with extention:</html>");
							}
						}
						else {
							prompt.setText("<html>ERROR: Invalid file extension<br>Enter save file name with extention:</html>");
						}
					}
				}
				//If so then the user is trying to load a game from the chessboard layout
				else {
					System.out.println("Board is visible!");
					
					//Get current fen of the board
					String currFen = BoardPanel.my_storage.getFen();
					
					//Get fen that was read in from file before save
					String prevSaveFen = BoardPanel.lastSaveFen;
					
					//Fen strings are different, prompt user if they want to save or not
					//Click save to save the game, or return or confirm
					if(currFen.equals(prevSaveFen) != true) {
						System.out.println("Fen strings are not equal! Prompt for save!");
						//Run prompt here to ensure that user doesn't want to save 
					}
				}
			}
		};
		return action;
	}
	
	private ActionListener returnBackToStartup() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			// Placeholder for when we add functionality
				//Set textfield back to default value, null
				fileName = null;
				//Try to see if the board is open/visible
				try {
					checkChessboardVisible = ConsoleGraphics.frame.isShowing();
				}
				catch(NullPointerException ex) {
					checkChessboardVisible = false;
				}
				//Check if the board has been loaded
				//If not then it must be from the startup menu where the user clicked on return
				if(checkChessboardVisible == false) {
					StartUpMenu start = new StartUpMenu();
					//Make load frame not visible after user clicks load
					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(cancel.getParent());
					frame.dispose();
				}
				//If chessboard is visible/open then user is returning from the load inside the game
				//Return should just remove the load panel and user should be back to chessboard
				else {
					//Make load frame not visible after user clicks load game
					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(enter.getParent());
					frame.dispose();
				}
			}
		};
		return action;
	}
}