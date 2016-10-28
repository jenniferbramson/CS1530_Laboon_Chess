package chess;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;  // awt.* does not import Action or Event Listeners
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import static java.lang.Math.abs;

import chess.Stockfish;

public class BoardPanel extends JPanel {
	GridBagLayout gbl;
	GridBagConstraints gbc;
  // These are buttons we will need to use listeners on
  protected JButton[][] checkers;
	protected static Storage my_storage;
  private Rulebook my_rulebook;
	boolean second_click = false;
	private String old_spot = "";
	private int old_x = 0;
	private int old_y = 0;
	//size of chess piece images
	private int imageWidth = 64;
	private int imageHeight = 64;
	//Stuff for colorizing
	private boolean colorizeBlack = false;
	private boolean colorizeWhite = false;
	private int blackR = 0;
	private int blackG = 0;
	private int blackB = 0;
	private int whiteR = 0;
	private int whiteG = 0;
	private int whiteB = 0;
	//COLORS
	Color SEAGREEN = new Color(180,238,180);
	Color DARKSEAGREEN = new Color(155,205,155);
	//for flipping the board
	private boolean flipped = false;


	//Keep track of the last saved fen
	//Used for testing loss of progress
	protected static String lastSaveFen = "";		//Used to check if player made moves after load
	protected static String lastSaveFilePath;		//Test code

	private String defaultFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

  // Makes the checkerboard with a JPanel array and adds JLabels around it to
  // label the rows 1 to 8 and the columns a to h
  public BoardPanel() {

		//Check if fen has been found from file
		//No fen was loaded from a file
		if(LoadPanel.fen == "" || LoadPanel.fen == null) {
			System.out.println("No fen to load");
			//default storage constructor
			my_storage = new Storage();
			//Set last saved fen to the default fen
			lastSaveFen = defaultFen;
		}
		else {
			System.out.println("Fen loaded!: " + LoadPanel.fen);

			//Save the fen from the file, will be used when the user try to load a different game
			//or close the current game they are on, get a new fen and compare it with the one
			//from the file, if different then prompt if user wants to save, else let use continue action
			lastSaveFen = LoadPanel.fen;
			my_storage = new Storage(lastSaveFen);
			//add fen to storage constructor
		}

    my_rulebook = new Rulebook(my_storage);


		//Setting up Display --------------------------------------------------------------------------------------
		this.setBackground(Color.WHITE); 	//make it white
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		this.setLayout(gbl);
    checkers = new JButton[8][8];
    Insets margins = new Insets(0, 0, 0, 0);  // For setting button margins

    // Initialize squares
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        JButton b = new JButton("");
				b.setPreferredSize(new Dimension(64, 64));					//set preferred size to 64px by 64px
        b.setMargin(margins);       // Make the button have no margins
        b.setOpaque(true);          // Necessary to see the colors (otherwise white)
        b.addActionListener(getSquareAction());
        b.setBorder(null);          // Necessary to not have button covering colors
        b.setBorderPainted(false);  // Necessary for mac icon transparency

        if ((i + j) % 2 == 0) {   // White square
          b.setBackground(Color.WHITE);
        } else {                  // Black Square
          b.setBackground(Color.GRAY);
        }

        checkers[i][j] = b;
      }
    }
		setPieces();				//call method to draw the pieces

		drawBoard();
  }

	/*-----------------------------------------------------------------------------------*/
	private void setPieces(){
		//Added to try and draw letters
		for(int i=0; i<8;i++){
			for(int j=0; j<8;j++){
				checkers[i][j].setIcon(null);			//clear it before you draw (if something was there previously)
				char c = my_storage.getSpace(i,j, true);
				try{
					Image src;
					Image img;
					ImageFilter blackCF = new HueFilter(blackR,blackG,blackB);
					ImageFilter whiteCF = new HueFilter(whiteR,whiteG,whiteB);
					switch(c){
						case 'p':
							img = ImageIO.read(getClass().getResource("/BlackPawn.png"));
							if(colorizeBlack) 		img = createImage(new FilteredImageSource(img.getSource(), blackCF));																
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;
						case 'b':
							img = ImageIO.read(getClass().getResource("/BlackBishop.png"));
							if(colorizeBlack) 		img = createImage(new FilteredImageSource(img.getSource(), blackCF));		
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;
						case 'r':
							img = ImageIO.read(getClass().getResource("/BlackRook.png"));
							if(colorizeBlack) 		img = createImage(new FilteredImageSource(img.getSource(), blackCF));		
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;
						case 'k':
							img = ImageIO.read(getClass().getResource("/BlackKing.png"));
							if(colorizeBlack) 		img = createImage(new FilteredImageSource(img.getSource(), blackCF));		
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;
						case 'q':
							img = ImageIO.read(getClass().getResource("/BlackQueen.png"));
							if(colorizeBlack) 		img = createImage(new FilteredImageSource(img.getSource(), blackCF));		
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;
						case 'n':
							img = ImageIO.read(getClass().getResource("/BlackKnight.png"));
							if(colorizeBlack) 		img = createImage(new FilteredImageSource(img.getSource(), blackCF));		
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;

						case 'P':
							img = ImageIO.read(getClass().getResource("/WhitePawn.png"));
							if(colorizeWhite) 		img = createImage(new FilteredImageSource(img.getSource(), whiteCF));		
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;
						case 'B':
							img = ImageIO.read(getClass().getResource("/WhiteBishop.png"));
							if(colorizeWhite) 		img = createImage(new FilteredImageSource(img.getSource(), whiteCF));	
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;
						case 'R':
							img = ImageIO.read(getClass().getResource("/WhiteRook.png"));
							if(colorizeWhite) 		img = createImage(new FilteredImageSource(img.getSource(), whiteCF));	
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;
						case 'K':
							img = ImageIO.read(getClass().getResource("/WhiteKing.png"));
							if(colorizeWhite) 		img = createImage(new FilteredImageSource(img.getSource(), whiteCF));	
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;
						case 'Q':
							img = ImageIO.read(getClass().getResource("/WhiteQueen.png"));
							if(colorizeWhite) 		img = createImage(new FilteredImageSource(img.getSource(), whiteCF));	
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;
						case 'N':
							img = ImageIO.read(getClass().getResource("/WhiteKnight.png"));
							if(colorizeWhite) 		img = createImage(new FilteredImageSource(img.getSource(), whiteCF));	
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;
						default:
							break;
					}
				} catch(IOException e){
					//error :(
				}

			}
		}

	}
	/*--------------------------------------------------------------------------------------------------------*/
	//draws the labels and pieces on the board
	public void drawBoard(){
		// Create Labels for a through h for the first rows
		addComponent(0,0,1,1,new JLabel(""));  // Corners are empty
		for (int i = 0; i < 8; i++) {
			JLabel label = new JLabel("" + (char)(97 + i));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			addComponent(i+1,0,1,1,label);
		}
		addComponent(0,9,1,1,new JLabel(""));  // Corners are empty

		// Fill out the center of the panel
		for (int i = 0; i < 8; i++) {       // rows
			for (int j = 0; j < 10; j++) {    // columns
				if (j == 0 || j == 9) {
					// Beginning or end of row, add column number
					JLabel label = new JLabel("" + (8 - i));
					label.setHorizontalAlignment(SwingConstants.CENTER);
					addComponent(j,i+1,1,1,label);
				} else {
					// Add chess squares
					addComponent(j,i+1,1,1,checkers[i][j-1]);
				}
			}
		}
			
		// Fill out the last row of letters a through h
		addComponent(0,9,1,1,new JLabel(""));  // Corners are empty
		for (int i = 0; i < 8; i++) {
			JLabel label = new JLabel("" + (char)(97 + i));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			addComponent(i+1,9,1,1, label);
		}
		addComponent(9,9,1,1,new JLabel(""));
	}
	
	
	/*----------------------------------------------------------------------------------------------------------*/
  private ActionListener getSquareAction() {
    ActionListener action = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // Placeholder for when we add functionality
				int x=0, y=0;
				for(int i=0; i<8; i++){
					for(int j=0; j<8; j++){
						if(e.getSource() == checkers[i][j]){
								x=j;
								y=i;
						}
					}
				}
				int a = (int)'a';
				char x_board = (char)(x+a);
				int y_board = 8-y;
				String current_spot = x_board + Integer.toString(y_board);
				System.out.println("You clicked on " + current_spot);
				if (second_click) {
          if (old_x == x && old_y == y) {
            // No movement, just undo the click
            if ( (old_x+old_y) % 2== 0) {
              checkers[old_y][old_x].setBackground(Color.WHITE);
            } else {
              checkers[old_y][old_x].setBackground(Color.GRAY);
            }
            second_click = false;
          } else {
            // Movement, so see if the movement is legal
            boolean legal = my_rulebook.checkMove(old_y, old_x, y, x);
            System.out.println(legal);
            if (legal) {
							// Check to see if moving King or Rook
							char oldPiece = my_storage.getSpaceChar(old_x, old_y);
							boolean castle = false;
							System.out.println(oldPiece);

							System.out.println("Moving " + old_spot + " to " + current_spot);
							if ( (old_x+old_y) % 2== 0) {
								checkers[old_y][old_x].setBackground(Color.WHITE);
							} else {
								checkers[old_y][old_x].setBackground(Color.GRAY);
							}
							second_click = false;
							String fen = my_storage.getFen();
							// Play move on stockfish's internal board
							System.out.println("Fen before move " + fen);

							// Move castle if castle, otherwise normally
							if (abs(old_x - x) == 2) {
								// castling
            		my_storage.movePiece(old_y, old_x, y, x);
								System.out.println("Old spot: " + old_x + " " + old_y);
								System.out.println("New spot: " + x + " " + y);

								if (old_x - x == 2) {
									// left, move appropriate rook
									my_storage.movePiece(old_y, 0, old_y, 3);
								} else {
									// right, move appropriate rook
									my_storage.movePiece(old_y, 7, old_y, 5);
								}
							} else {
	              my_storage.movePiece(old_y, old_x, y, x);
							}

							String move = old_spot + current_spot;
							System.out.println("move is " + move);
							ConsoleGraphics.stockfish.movePiece(move, my_storage.getFen());
              fen = ConsoleGraphics.stockfish.getFen();
              System.out.println("New fen " + fen);
              ConsoleGraphics.stockfish.drawBoard();

	            //update storage fen with new fen pulled from stockfish output
	            my_storage.setFen(fen);

    					//redraw
    					setPieces();
              // Switch whose turn it is
              LaboonChess.changeTurn();
            } // end legality move check
						else{
							System.out.println("Not a legal move.");
						}
          } // end checking move
				} // end second click
				else{

          if (LaboonChess.getPlayersTurn()) { // Ignore input unless it is the player's turn
            boolean validColor = false;     // Tells if the right color piece is trying to move
            if (Character.isUpperCase(my_storage.getSpaceChar(x, y))) {
              if (LaboonChess.getTurn() == 'w') {
                validColor = true;
              }
            } else if (Character.isLowerCase(my_storage.getSpaceChar(x, y))) {
              if (LaboonChess.getTurn() == 'b') {
                validColor = true;
              }
            }

            if (validColor) {               // Continue if right color piece clicked
							if((x+y)%2 == 0){
								checkers[y][x].setBackground(SEAGREEN);
							}
							else{
								checkers[y][x].setBackground(DARKSEAGREEN);
							}
    					System.out.println("First click");
    					old_spot = current_spot;
    					old_x = x;
    					old_y = y;
    					second_click = true;
            } else {
              // Invalid color piece clicked or empty, so ignore
            } // end if (validColor)
          } // end if (playersTurn())

				} // end ActionListener

				//for testing ONLY
				if(x==0 && y==0){
					System.out.println(my_storage.getFen());
				}
      }
    };
    return action;
  }

	//helper method to add Components to a Container using GridBagLayout
	public void addComponent(int x, int y, int w, int h, Component aComponent) {
		gbc.gridx=x;
		gbc.gridy=y;
		gbc.gridwidth=w;
		gbc.gridheight=h;
		gbl.setConstraints(aComponent,gbc);
		this.add(aComponent);
	}
	
	//method that hues the black pieces to a certain hue
		//Note: no hue if boolean is false
	public void colorizeBlack(boolean colorize, int red, int green, int blue){
		colorizeBlack = colorize;
		blackR = red;
		blackG = green;
		blackB = blue;
		setPieces();
	}
	//method that hues the white pieces to a certain hue
		//Note: no hue if boolean is false
	public void colorizeWhite(boolean colorize, int red, int green, int blue){
		colorizeWhite = colorize;
		whiteR = red;
		whiteG = green;
		whiteB = blue;
		setPieces();
	}
	
	public void setFlip(boolean flip){
		flipped = flip;
		//drawBoard();
	}
}
