package chess;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  // awt.* does not import Action or Event Listeners
import java.io.*;
import javax.imageio.*;
import java.util.*;
import java.lang.*;

public class SavePanel extends JPanel {

	private JButton save;
	private JButton cancel;
	private JButton overwrite;
	private JLabel prompt;
	private JTextField fileName;

	boolean checkChessboardVisible;

	private String acceptedFileExtension = "txt";
	private String saveFilePath = "/resources/";

	protected static String fileNamePath;

	//Set text sizes
	private int promptTextSize = 20;
	private int buttonTextSize = 16;
	private int textFieldSize = 16;

	private GridBagConstraints gbc;

	public SavePanel() {
		GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);
		this.setBackground(Color.WHITE);

		gbc = new GridBagConstraints();

		//Adding prompt
		prompt = new JLabel("Enter save file name:");
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

		//Add save button to confirm text field input
		save = new JButton("Save");
		save.setAlignmentX(Component.CENTER_ALIGNMENT);
		save.setFont(new Font("Arial", Font.BOLD, buttonTextSize));
		save.addActionListener(saveChessGame());

		//Add cancel button to return back to the start up menu
		cancel = new JButton("Return");
		cancel.setAlignmentX(Component.CENTER_ALIGNMENT);
		cancel.setFont(new Font("Arial", Font.BOLD, buttonTextSize));
		cancel.addActionListener(returnBackToStartup());

		gbc.ipadx = 150;

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		this.add(save, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		this.add(cancel, gbc);
	}

	private ActionListener saveChessGame() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Set prompt back to default setting
				prompt.setText("Enter save file name:");
				//Check if the user entered a name for the new file
				if(fileName.getText() != "") {
					//Check user entered a valid file extension
					if(checkValidFileExtension() == true)
					{
						try {
							fileNamePath = "/" + fileName.getText();

							//Check if file exists
							File file = new File(this.getClass().getResource(fileNamePath).toURI());

							//TODO -Check if the last loaded fen string and current one are the same
							//If they are the same no need to open up a prompt to save if it's the same
							System.out.println("File name exists! Prompt overwrite confirmation");
							setUpPrompt();

						} catch(Exception ee) {
							//File name doesn't exist, save board information to the file
							System.out.println("File Name doesn't exist! Save fen to file!");
							System.out.print("Exception: ");
							System.out.println(ee.getMessage());

							//File name doesn't exist already, therefore save the game to the file
							saveFile();
						}
					}
				}
			}
		};
		return action;
	}

	private ActionListener returnBackToStartup() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Set textfield back to default value, null
				fileName.setText("");

				//Check if panel was modified to prompt layout
				if(cancel.getText().equals("No")) {
					//Revert all changes to make prompt back to how it was before
					SaveGame saveGamePanel = new SaveGame();

					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(cancel.getParent());
					frame.dispose();
				}
				//Save panel wasn't converted into a prompt/confirmation panel
				else {
					//Make save frame not visible after user clicks return/cancel
					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(cancel.getParent());
					frame.dispose();
				}
			}
		};
		return action;
	}

	private void saveFile() {
		//Save file to resource folder
		//Get current fen of the board
		String currFen = BoardPanel.my_storage.getFen();

		System.out.println("Current Fen String: " + currFen);

		//TEST CODE
		//Assume that fen strings equals the one in the save file
		//Or if there wasn't a save file loaded assume that
		if(BoardPanel.lastSaveFen == null) {
			currFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
		}
		else if(BoardPanel.lastSaveFen.equals("")) {
			currFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
		}
		else {
			currFen = BoardPanel.my_storage.getFen();
		}

		//Holds file information, can add more information later
		StringBuilder fileInfo = new StringBuilder("");

		//Add fen string to the file
		fileInfo.append(currFen + "\n");

		char c;
		String row;

		//Append current board state to the file
		for(int i=0; i<8;i++){
			row = "";
			for(int j=0; j<8;j++){
				c = BoardPanel.my_storage.getSpace(i,j, true);
				if(c == '\u0000') {
					c = '-';
				}
				row = row + c;
			}
			fileInfo.append(row + "\n");
		}



		//Test if save file information was written correctly
		System.out.print(fileInfo);

		try {
			//Create new file
			fileNamePath = "/" + fileName.getText();

			System.out.println("Entered save file name: " + fileNamePath);

			//Get the directory where the save files are at
			String directory = this.getClass().getResource("/").getFile();
			File file = new File(directory + fileNamePath);

			FileWriter fileIn = new FileWriter(file, false);

			//Write all file information to the file
			fileIn.write(fileInfo.toString());
			//Last line of file, record what color the player is
			fileIn.write(LaboonChess.controller.getPlayersColor() + "\n");

			//Append difficulty level to file
			fileIn.write(LaboonChess.stockfish.getDifficultyLevel().name());

			fileIn.close();

		} catch(Exception ex) {
			System.out.println("Problem creating file!");
			System.out.print("Exception: ");
			System.out.println(ex.getMessage());

			System.out.println("File already exists! Overwrite file!");

			//Overwrite the file with the new information
			try {
				//Retrieve the file to overwrite
				//Then overwrite the file with new information
				File f = new File(this.getClass().getResource(fileNamePath).toURI());
				FileWriter fileIn = new FileWriter(f, false);

				//Write all file information to the file
				fileIn.write(fileInfo.toString());
				fileIn.close();
			} catch(Exception exx) {
				System.out.println("Problem opening file!");
				System.out.print("Exception: ");
				System.out.println(exx.getMessage());
			}
		}

		//Make save frame not visible after user clicks return/cancel
		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(cancel.getParent());
		frame.dispose();
	}

	public void setUpPrompt() {
		//Change the label text
		prompt.setText("<html>There is already a save file with that name<br>Are you sure you want to overwrite it?</html>");

		//Remove the save button
		this.remove(save);

		//Replace the save button spot with the overwrite button
		//Add save button to confirm text field input
		overwrite = new JButton("Yes");
		overwrite.setAlignmentX(Component.CENTER_ALIGNMENT);
		overwrite.setFont(new Font("Arial", Font.BOLD, buttonTextSize));
		overwrite.addActionListener(confirmOverwrite());

		gbc.ipadx = 150;

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		this.add(overwrite, gbc);

		//Change text for return button
		cancel.setText("No");

		//Hide textfield
		fileName.setVisible(false);

		this.validate();
		this.repaint();
	}

	private ActionListener confirmOverwrite() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveFile();
				//Make save frame not visible after user clicks return/cancel
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(cancel.getParent());
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
