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
	 - Write test code that generates random text files 
		- Check number of files, if that number of files hasn't been reached, create them
	 - Dynamic/set maximum size for buttons, dynamically generate width from button sizes
*/

public class LoadPanel extends JPanel {
	
	private JLabel prompt;
	
	private JButton loadGame;
	private int loadGameWidth = 200;
	private int loadGameHeight = 25;
	
	private String fileName;

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
	
	private int promptWidth = 600;
	private int promptHeight = 200;
	
	private int calculatedHeight;
	private int defaultWidth = 800;
	
	private int numberColumns = 3;
	private int centerPrompt = 3;
	
	public LoadPanel() {
		
		//Retrieve all valid file names
		getListOfSaveFiles();
		
		GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);
		this.setBackground(Color.WHITE);
		
		gbc = new GridBagConstraints();
		
		//Adding prompt
		prompt = new JLabel("Select save file to load");
		prompt.setHorizontalAlignment(JLabel.CENTER);
		prompt.setFont(new Font("Arial", Font.BOLD, promptTextSize));
		
		gbc.insets = new Insets(7, 7, 7, 7);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = centerPrompt;           
		gbc.ipady = 15;		
		this.add(prompt, gbc);
		
		//Code related to new load file layout
		int rowNumber = 1;
		int colNumber = 0;
		
		for(int i = 0; i < listOfAllSaveFiles.size(); i++)
		{
			//Add buttons to the panel
			loadGame = new JButton(listOfAllSaveFiles.get(i));
			loadGame.setToolTipText(listOfAllSaveFiles.get(i));
			loadGame.setAlignmentX(Component.CENTER_ALIGNMENT);
			loadGame.setFont(new Font("Arial", Font.BOLD, buttonTextSize));
			loadGame.setPreferredSize(new Dimension(loadGameWidth,loadGameHeight));
			loadGame.addActionListener(loadChessGame());
			
			gbc.ipadx = 25;	
			
			gbc.gridx = colNumber;
			gbc.gridy = rowNumber;
			gbc.gridwidth = 1;  		
			this.add(loadGame, gbc);
			
			//If button hasn't filled up the row add another one
			if(colNumber < (numberColumns-1)) {
				colNumber++;
			}
			//Else move onto the next row
			else {
				rowNumber++;
				colNumber = 0;
			}
		}
		
		//Calculate dynamic height based on the number of row
		calculatedHeight = (rowNumber * (loadGameHeight + 31)) + 100;
		
		//If height is too large, limit it
		if(calculatedHeight > 700) {
			calculatedHeight = 700;
		}

		//Set frame to new height and the default width, determined by number of columns
		setNewFrameSize(defaultWidth, calculatedHeight);
	}
	
	private ActionListener loadChessGame() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//Code for load button panel layout
				//Get the file name based on what button was clicked
				fileName = e.getActionCommand();
				
				System.out.println("FileName of button clicked: " + fileName);
				
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
					//currFen = BoardPanel.lastSaveFen;
					
					//Assume that currFen is different from last loaded in fen
					currFen = "test";
					
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
				
				//If not then it must be from the startup menu where the user clicked on load game
				else {
					System.out.println("Board is not visible!");
					loadFile();
				}
			}
		};
		return action;
	}
	
	//Load the entered file, retrieve it's contents and display it on the board
	private void loadFile() {
		//Test prompt back to default settings
		prompt.setText("Enter save file name with extention:");
		
		fileNamePath = "/" + fileName;
		
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
				
				//Load chessboard
				ConsoleGraphics chessboard = new ConsoleGraphics();			
				
				//Update last saved fen
				//Last saved fen as the new fen that was just loaded
				BoardPanel.lastSaveFen = fen;
				
				//Make load frame not visible after user clicks load game
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this.getParent());
				frame.dispose();
				
			} catch (Exception eee) {
				System.out.print("Exception: ");
				System.out.println(eee.getMessage());
			}
		} catch(Exception eeeee) {
			System.out.print("Exception: ");
			System.out.println(eeeee.getMessage());
			prompt.setText("<html>ERROR: File doesn't exists!<br>Enter save file name with extention:</html>");
		}
	}
	
	private void setUpPrompt() {
		//Remove all components in the board
		this.removeAll();
		
		//Add the prompt back to the panel
		//Change the label text
		prompt.setText("<html>Game hasn't been saved!<br>Do you want to save your progress?</html>");

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;           
		gbc.ipady = 15;		
		this.add(prompt, gbc);
		
		//Add a yes button that causes the save window to pop up
		save = new JButton("Yes");
		save.setAlignmentX(Component.CENTER_ALIGNMENT);
		save.setFont(new Font("Arial", Font.BOLD, buttonTextSize));
		save.addActionListener(confirmSave());
		
		gbc.ipadx = 150;	
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;  		
		this.add(save, gbc);
		
		//Add a no button that will automatically load the file the file that the user wanted
		continueLoad = new JButton("No");
		continueLoad.setAlignmentX(Component.CENTER_ALIGNMENT);
		continueLoad.setFont(new Font("Arial", Font.BOLD, buttonTextSize));
		continueLoad.addActionListener(confirmLoad());
		
		gbc.ipadx = 150;	
		
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 1;  		
		this.add(continueLoad, gbc);
		
		//Set up the prompt frame to the default size listed in variable declaration
		setNewFrameSize(promptWidth, promptHeight);
	}
	
	//Method used for yes button in the prompt
	//If user clicks the no button, then it confirms that the user wants to save the current game
	private ActionListener confirmSave() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				//Open save file panel, let user save the game
				SaveGame saveGame = new SaveGame();
				
				//Make save frame not visible after user clicks return/cancel
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(prompt.getParent());
				frame.dispose();
			}
		};
		return action;
	}
	
	//Method used for the no button in the prompt
	//If user clicked the no button, then the load would continue
	private ActionListener confirmLoad() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Load game to the board
				loadFile();
				
				//Make save frame not visible after user clicks return/cancel
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(prompt.getParent());
				frame.dispose();
			}
		};
		return action;
	}
	
	//Retrieves all the save files and stores it in a global variable
	//	Gets all files, removes any files that aren't txt files
	private void getListOfSaveFiles() {
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
					//System.out.println(saveFileName);
					
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
			System.out.print("Exception: ");
			System.out.println(ex.getMessage());
		}
	}
	
	//Sets the frame size for the load panel dynamically
	private void setNewFrameSize(int width, int height) {
		//Set the new frame width and height
		LoadGame.screenWidth = width;
		LoadGame.screenHeight = height;
		
		//Modify the size of the frame
		LoadGame.frame.setSize(LoadGame.screenWidth, LoadGame.screenHeight);
		
		//Recenter the frame in the middle of the screen
		Toolkit t = getToolkit();
		Dimension screen = t.getScreenSize();
		LoadGame.frame.setLocation(screen.width/2-LoadGame.frame.getWidth()/2,screen.height/2-LoadGame.frame.getHeight()/2);
		
		this.validate();
		this.repaint();
	}
}