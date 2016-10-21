package chess;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  // awt.* does not import Action or Event Listeners
import java.io.*;
import javax.imageio.*;
import java.util.*;
import java.lang.*;

/*
TODO - Add variable that keeps track of the last saved fen - Remove all forced fen values used for testing
	 - Get updates on the current fen, compare it to the last saved fen
		- If fen are the same skip prompt and continue action
		- Else set up prompt 
	 - Handle edge case if the fen string and previously loaded or saved fen string are the same
		- Want to not prompt if they are the same
*/

public class LoadPanel extends JPanel {
	
	//Buttons for the board
	private JButton enter;
	private JButton cancel;
	private JLabel prompt;
	private JTextField fileName;
	
	private JButton save;
	private JButton continueLoad;
	
	protected static String fen;
	boolean checkChessboardVisible;
	
	private ArrayList<String> fileContents;
	private String tempLine;
	
	private String acceptedFileExtension = "txt";
	private String saveFilePath = "/";
	private ArrayList<String> listOfAllSaveFiles;
	
	protected static String fileNamePath;
	
	//Set text sizes
	private int promptTextSize = 20;
	private int buttonTextSize = 16;
	private int textFieldSize = 16;
	
	private GridBagConstraints gbc;
	
	public LoadPanel() {
		/*
		//This is a setup for potentially something later
		//Might reformat how the load screen looks and works so user
		//doesn't have to type in the file name every time
		//Plan: Create buttons dynamically that have the file name on them
		//When clicked retrieve the file name and open it, retrieve fen, etc
		try {
			//Retrieve all files or directories in the resource folder
			File resourceFolder = new File(this.getClass().getResource(saveFilePath).toURI());
			File[] listOfFiles = resourceFolder.listFiles();
			
			listOfAllSaveFiles = new ArrayList<String>();
			
			//Check all files 
			for(File f : listOfFiles) {
				//Check if file is a folder
				if(f.isFile()) {
					String saveFileName = f.getName();
					
					//Display all files 
					System.out.println(saveFileName);
					
					//Check the extension to make sure it's a text file
					//If file is a text file, then most likely it's a save file
					String checkExtension = saveFileName.substring(saveFileName.lastIndexOf(".") + 1, saveFileName.length());
					if(checkExtension.equals(acceptedFileExtension)) {
						listOfAllSaveFiles.add(saveFileName);
					}
				}
			}
			
			//Print out what files are saved in the list
			System.out.println(listOfAllSaveFiles);
			
		} catch (Exception ex) {
			System.out.println("Something terrible has happened...");
			System.out.print("Exception: ");
			System.out.println(ex.getMessage());
		}
		*/
		GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);
		this.setBackground(Color.WHITE);
		
		gbc = new GridBagConstraints();
		
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
				//If so then the user is trying to load a game from the chessboard layout
				if(checkChessboardVisible == true) {
					System.out.println("Board is visible!");
					
					//Get current fen of the board
					String currFen = BoardPanel.my_storage.getFen();
					
					//Get fen that was read in from file before save
					String prevSaveFen = BoardPanel.lastSaveFen;
					
					//Assume that currFen is the last loaded in fen for testing
					currFen = BoardPanel.lastSaveFen;
					
					//Check file extension, see if it's a text file
					if(checkValidFileExtension() == true)
					{
						try {
							//Check if file exists
							File file = new File(this.getClass().getResource(fileNamePath).toURI());
							//Fen strings are different, prompt user if they want to save or not
							//Click save to save the game, or return or confirm
							if(currFen.equals(prevSaveFen) == false) {
								System.out.println("Fen strings are not equal! Prompt for save!");
								setUpPrompt(); 
							}
							else {
								loadFile();
							}
						} catch(Exception E) {
							System.out.print("Exception: ");
							System.out.println(E.getMessage());
							prompt.setText("<html>ERROR: File doesn't exists!<br>Enter save file name with extention:</html>");
						}
					}
				}
				
				//If not then it must be from the startup menu where the user clicked on load game
				else {
					System.out.println("Board is not visible!");
					loadFile();
				}
			}
		};
		return action;
	}
	
	private ActionListener returnBackToStartup() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			// Placeholder for when we add functionality
				//Clear out the text field
				fileName.setText("");
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
					//Check if panel was modified to prompt layout
					if(cancel.getText().equals("Return")) {
						//Make load frame not visible after user clicks return
						JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(cancel.getParent());
						frame.dispose();
					}
					//Panel wasn't modified to prompt layout
					else {
						//Revert all changes to make prompt back to how it was before
						//Revert done just by getting rid of the old one and loading in a new one
						LoadGame loadGamePanel = new LoadGame();
						
						JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(cancel.getParent());
						frame.dispose();
					}
				}
			}
		};
		return action;
	}
	
	//Load the entered file, retrieve it's contents and display it on the board
	private void loadFile() {
		//Test prompt back to default settings
		prompt.setText("Enter save file name with extention:");
		//Text field is not empty, attempt to load a file
		if(fileName.getText() != "") {
			fileContents = new ArrayList<String>();
			try {
				//Check if file exists
				File file = new File(this.getClass().getResource(fileNamePath).toURI());
				//Try to open contents of the file
				try {
					//Read in all contents of the file and store it into an ArrayList
					BufferedReader readFile = new BufferedReader(new FileReader(file));
					while((tempLine = readFile.readLine()) != null)
					{
						fileContents.add(tempLine);
					}
					readFile.close();
					
					//Get fen from the file
					//Assume that fen is the first line in the file
					fen = fileContents.get(0);
					
					//TEST CODE
					//Read out the board in the file to make sure its the same
					//As what is displayed
					System.out.println("Read in fen from file: \n" + fen);
					for(int i = 1; i < 9; i++) {
						System.out.println("Row " + i + " : " + fileContents.get(i));
					}
					
					//Remove previous chessboard before creating the new one
					//If there is some lag when loading the images
					//Might just call drawpieces on the current board with new fen
					ConsoleGraphics.frame.dispose();
					
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
				System.out.print("Exception: ");
				System.out.println(eeeee.getMessage());
				prompt.setText("<html>ERROR: File doesn't exists!<br>Enter save file name with extention:</html>");
			}
			//Update last saved fen
			//Last saved fen as the new fen that was just loaded
			BoardPanel.lastSaveFen = fen;
		}
	}
	
	public void setUpPrompt() {
		//Change the label text
		prompt.setText("<html>Game hasn't been saved!<br>Do you want to save your progress?</html>");
		
		//Remove the load button
		this.remove(enter);
		
		this.cancel.setText("Cancel");
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;           
		gbc.ipady = 15;		
		this.add(prompt, gbc);
		
		//Replace the load button spot with the save button
		//Brings up save panel to let user save the game
		save = new JButton("Yes");
		save.setAlignmentX(Component.CENTER_ALIGNMENT);
		save.setFont(new Font("Arial", Font.BOLD, buttonTextSize));
		save.addActionListener(confirmSave());
		
		gbc.ipadx = 150;	
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;  		
		this.add(save, gbc);
		
		//Add an additional button, if user doesn't want to save the current playing game then 
		//just load the game they want
		continueLoad = new JButton("No");
		continueLoad.setAlignmentX(Component.CENTER_ALIGNMENT);
		continueLoad.setFont(new Font("Arial", Font.BOLD, buttonTextSize));
		continueLoad.addActionListener(confirmLoad());
		
		gbc.ipadx = 150;	
		
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 1;  		
		this.add(continueLoad, gbc);
		
		//Size of the screen changes with the addition of another button so change it accordingly
		Toolkit t = getToolkit();
		Dimension screen = t.getScreenSize();
		
		LoadGame.frame.setSize(750, 250);
		LoadGame.frame.setLocation(screen.width/2-LoadGame.frame.getWidth()/2,screen.height/2-LoadGame.frame.getHeight()/2);
		
		//Hide textfield
		fileName.setVisible(false);
		
		//Update the panel so changes appear
		this.validate();
		this.repaint();
	}
	
	private ActionListener confirmSave() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Set textfield back to default value, null
				fileName = null;
				
				//Open save file panel, let user save the game
				SaveGame saveGame = new SaveGame();
				
				//Make save frame not visible after user clicks return/cancel
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(save.getParent());
				frame.dispose();
			}
		};
		return action;
	}
	
	private ActionListener confirmLoad() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Load game to the board
				loadFile();
				
				//Make save frame not visible after user clicks return/cancel
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(continueLoad.getParent());
				frame.dispose();
			}
		};
		return action;
	}
	
	//Check if the user entered a valid, accepted file extension
	public boolean checkValidFileExtension() {
		boolean validFileExtension = false;
		
		fileNamePath = "/" + fileName.getText();

		//Check file extensions before attempting to open the file
		//Have to do this because user could try to load a png file
		//Might have to move images into a subfolder in resources
		String checkExtension = fileNamePath.substring(fileNamePath.lastIndexOf(".") + 1, fileNamePath.length());
		
		//Only check if it's a text file for now
		//Change later if we plan to encrypt
		
		//File extension is accept, proceed to open the file
		if(checkExtension.equals(acceptedFileExtension)) {
			validFileExtension = true;
		}
		else {
			prompt.setText("<html>ERROR: Invalid file extension<br>Enter save file name with extention:</html>");
		}
		
		return validFileExtension;
	}
}