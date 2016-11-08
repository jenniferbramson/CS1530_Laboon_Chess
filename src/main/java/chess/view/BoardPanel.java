package chess;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;  // awt.* does not import Action or Event Listeners
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import static java.lang.Math.abs;
import java.util.*;

import chess.Stockfish;

public class BoardPanel extends JPanel {
	protected static Storage my_storage;
  private Rulebook my_rulebook;
	protected GridBagLayout gbl;
	protected GridBagConstraints gbc;
  // These are buttons we will need to use listeners on
  protected JButton[][] checkers;
	// values for keeping track of when to send moves to controller
	boolean second_click = false;
	private String old_spot = "";
	private int old_x = 0;
	private int old_y = 0;
	//size of chess piece images
	private int imageWidth = 64;
	private int imageHeight = 64;
	//Stuff for colorizing
	private boolean blackPartyTime = false;
	private boolean whitePartyTime = false;
	private boolean colorizeBlack = false;
	private boolean colorizeWhite = false;
	private int blackR = 0;
	private int blackG = 0;
	private int blackB = 0;
	private int whiteR = 0;
	private int whiteG = 0;
	private int whiteB = 0;
	//COLORS
	private final Color SEAGREEN = new Color(180,238,180);
	private final Color DARKSEAGREEN = new Color(155,205,155);
	//for flipping the board
	private boolean flipped = false;
	
	//When history of moves is implemented use that instead but 
	//store the values in storage temporarily
	protected static int lastMovePiecePositionX = 0;
	protected static int lastMovePiecePositionY = 0;

	private boolean pawnPromoted = false;
	
	protected static String resultsOfGame;
	protected static LinkedList previousMoves;
	
	//Keep track of the last saved fen
	//Used for testing loss of progress
	protected static String lastSaveFen = "";		//Used to check if player made moves after load
	protected static String lastSaveFilePath;		//Test code

	private String defaultFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

  // Makes the checkerboard with a JPanel array and adds JLabels around it to
  // label the rows 1 to 8 and the columns a to h
  public BoardPanel() {
		
		previousMoves = new LinkedList();
		
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
		setPieces();				//call method to SET the pieces as what they should be
		drawBoard();			//call the method to draw the newly set pieces to the board
  }

	/*-----------------------------------------------------------------------------------*/
	public void setPieces(){
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
							if(blackPartyTime){
								ImageIcon icon = new ImageIcon(getClass().getResource("/aussieparrot.gif"));
								checkers[i][j].setIcon(icon);
								icon.setImageObserver(checkers[i][j]);
								break;
							}
							img = ImageIO.read(getClass().getResource("/BlackPawn.png"));
							if(colorizeBlack) 		img = createImage(new FilteredImageSource(img.getSource(), blackCF));
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;
						case 'b':
							if(blackPartyTime){
								ImageIcon icon = new ImageIcon(getClass().getResource("/aussiecongaparrot.gif"));
								checkers[i][j].setIcon(icon);
								icon.setImageObserver(checkers[i][j]);
								break;
							}
							img = ImageIO.read(getClass().getResource("/BlackBishop.png"));
							if(colorizeBlack) 		img = createImage(new FilteredImageSource(img.getSource(), blackCF));
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;

						case 'r':
							if(blackPartyTime){
								ImageIcon icon = new ImageIcon(getClass().getResource("/aussiereversecongaparrot.gif"));
								checkers[i][j].setIcon(icon);
								icon.setImageObserver(checkers[i][j]);
								break;
							}
							img = ImageIO.read(getClass().getResource("/BlackRook.png"));
							if(colorizeBlack) 		img = createImage(new FilteredImageSource(img.getSource(), blackCF));
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;

						case 'k':
							if(blackPartyTime){
								ImageIcon icon = new ImageIcon(getClass().getResource("/parrotcop.gif"));
								checkers[i][j].setIcon(icon);
								icon.setImageObserver(checkers[i][j]);
								break;
							}
							img = ImageIO.read(getClass().getResource("/BlackKing.png"));
							if(colorizeBlack) 		img = createImage(new FilteredImageSource(img.getSource(), blackCF));
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;
						case 'q':
							if(blackPartyTime){
								ImageIcon icon = new ImageIcon(getClass().getResource("/fastparrot.gif"));
								checkers[i][j].setIcon(icon);
								icon.setImageObserver(checkers[i][j]);
								break;
							}
							img = ImageIO.read(getClass().getResource("/BlackQueen.png"));
							if(colorizeBlack) 		img = createImage(new FilteredImageSource(img.getSource(), blackCF));
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;

						case 'n':
							if(blackPartyTime){
								ImageIcon icon = new ImageIcon(getClass().getResource("/partyparrot.gif"));
								checkers[i][j].setIcon(icon);
								icon.setImageObserver(checkers[i][j]);
								break;
							}
							img = ImageIO.read(getClass().getResource("/BlackKnight.png"));
							if(colorizeBlack) 		img = createImage(new FilteredImageSource(img.getSource(), blackCF));
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;


						case 'P':
							if(whitePartyTime){
								ImageIcon icon = new ImageIcon(getClass().getResource("/parrot.gif"));
								checkers[i][j].setIcon(icon);
								icon.setImageObserver(checkers[i][j]);
								break;
							}
							img = ImageIO.read(getClass().getResource("/WhitePawn.png"));
							if(colorizeWhite) 		img = createImage(new FilteredImageSource(img.getSource(), whiteCF));
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));

							break;
						case 'B':
							if(whitePartyTime){
									ImageIcon icon = new ImageIcon(getClass().getResource("/parrotwave3.gif"));
									checkers[i][j].setIcon(icon);
									icon.setImageObserver(checkers[i][j]);
									break;
							}
							img = ImageIO.read(getClass().getResource("/WhiteBishop.png"));
							if(colorizeWhite) 		img = createImage(new FilteredImageSource(img.getSource(), whiteCF));
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;
						case 'R':
							if(whitePartyTime){
									ImageIcon icon = new ImageIcon(getClass().getResource("/parrotwave1.gif"));
									checkers[i][j].setIcon(icon);
									icon.setImageObserver(checkers[i][j]);
									break;
							}
							img = ImageIO.read(getClass().getResource("/WhiteRook.png"));
							if(colorizeWhite) 		img = createImage(new FilteredImageSource(img.getSource(), whiteCF));
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;

						case 'K':
							if(whitePartyTime){
									ImageIcon icon = new ImageIcon(getClass().getResource("/fiestaparrot.gif"));
									checkers[i][j].setIcon(icon);
									icon.setImageObserver(checkers[i][j]);
									break;
							}

							img = ImageIO.read(getClass().getResource("/WhiteKing.png"));
							if(colorizeWhite) 		img = createImage(new FilteredImageSource(img.getSource(), whiteCF));
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;

						case 'Q':
							if(whitePartyTime){
									ImageIcon icon = new ImageIcon(getClass().getResource("/sassyparrot.gif"));
									checkers[i][j].setIcon(icon);
									icon.setImageObserver(checkers[i][j]);
									break;
							}
							img = ImageIO.read(getClass().getResource("/WhiteQueen.png"));
							if(colorizeWhite) 		img = createImage(new FilteredImageSource(img.getSource(), whiteCF));
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;

						case 'N':
							if(whitePartyTime){
									ImageIcon icon = new ImageIcon(getClass().getResource("/parrotwave2.gif"));
									checkers[i][j].setIcon(icon);
									icon.setImageObserver(checkers[i][j]);
									break;
							}
							img = ImageIO.read(getClass().getResource("/WhiteKnight.png"));
							if(colorizeWhite) 		img = createImage(new FilteredImageSource(img.getSource(), whiteCF));
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;
						default:
							break;
					}
				} catch(IOException e){
						System.out.println("Error loading pieces!");
						System.exit(0);
				}

			}
		}

	}
	/*--------------------------------------------------------------------------------------------------------*/
	//draws the labels and pieces on the board
	public void drawBoard(){
		// Create Labels for a through h for the first rows
		addComponent(0,0,1,1,new JLabel(""));  // Corners are empty
		if(!flipped){
			for (int i = 0; i < 8; i++) {
			JLabel label = new JLabel("" + (char)(97 + i));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			addComponent(i+1,0,1,1,label);
			}
		}
		else{
			for (int i = 7; i >= 0; i--) {
			JLabel label = new JLabel("" + (char)(97 + i));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			addComponent(8-i,0,1,1,label);
			}
		}

		addComponent(0,9,1,1,new JLabel(""));  // Corners are empty


		// Fill out the center of the panel
		//if not flipped, print them out in normal order
		if(!flipped){
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
		}
		//print out the rows in REVERSE order
		else {
			for (int i = 8; i >= 1; i--) {       // rows
				for (int j = 9; j >= 0; j--) {    // columns
					if (j == 0 || j == 9) {
						// Beginning or end of row, add column number
						JLabel label = new JLabel("" + (9 - i));
						label.setHorizontalAlignment(SwingConstants.CENTER);
						addComponent(9-j,9-i,1,1,label);
					} else {
						// Add chess squares
						addComponent(9-j,9-i,1,1,checkers[i-1][j-1]);
					}
				}
			}
		}	//end of drawBoard


		// Fill out the last row of letters a through h
		addComponent(0,9,1,1,new JLabel(""));  // Corners are empty
		if(!flipped){
			for (int i = 0; i < 8; i++) {
			JLabel label = new JLabel("" + (char)(97 + i));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			addComponent(i+1,9,1,1,label);
			}
		}
		else{
			for (int i = 7; i >= 0; i--) {
			JLabel label = new JLabel("" + (char)(97 + i));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			addComponent(8-i,9,1,1,label);
			}
		}
		addComponent(9,9,1,1,new JLabel(""));
	}

	//completely wipes the board
	public void clearBoard(){
		this.removeAll();
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
	public void setParty(boolean party, boolean white){
		if(white){
			whitePartyTime = party;
		}
		else{
			blackPartyTime = party;
		}
		setPieces();
	}

	//method that flips the board
	public void setFlip(boolean flip){
		flipped = flip;		//set the boolean value so drawBoard knows what order to draw in
		clearBoard();		//clear the board so we can draw a new one
		drawBoard();		//re-draw the board
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

				// Play move on our board
				System.out.println("Old spot: " + old_x + " " + old_y);
				System.out.println("New spot: " + x + " " + y);
				my_storage.movePiece(old_y, old_x, y, x);
				
				lastMovePiecePositionY = x;
				lastMovePiecePositionX = y;
				
				//Get the piece that just moved
				char piece = my_storage.getSpaceChar(x, y);
				
				//Get whose turn it is right now
				char turn = LaboonChess.getTurn();
				
				//Indicates the color that the user selected to play as
				char playercolor = LaboonChess.controller.playersColor;
				
				//Open up pawn promotion window if it's the user who initiates pawn promotion
				//Don't need to open the panel for stockfish, more overhead and stockfish handles
				//pawn promotion automatically
				if(turn == playercolor) {
					//Check if pawn can be promoted
					pawnPromoted = checkPromotion(y, piece);
				}
				else {
					pawnPromoted = false;
				}
				
				// Play move on stockfish's internal board
				System.out.println("Fen before move " + fen);
				
				String move;
				if(pawnPromoted == true) {
					System.out.println("Pawn was promoted!");
					String resultOfPawnPromotion = PawnPromotionPanel.pawnPromotionSelection;
					System.out.println("Upgrade Pawn to: " + resultOfPawnPromotion);
					move = old_spot + current_spot + resultOfPawnPromotion;
				}
				else {
					move = old_spot + current_spot;
				}
				
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
			  
			  //Keep track of the last 6 moves
			  if(previousMoves.size() < 6) {
				previousMoves.add(move);
			  }
			  else {
				previousMoves.add(move);
				previousMoves.removeFirst();
			  }
			  
			  //Display when testing win/loss/draw condition starts
			  System.out.println("Starting tests for game results");
			  resultsOfGame = my_rulebook.testGameEnded(fen);
			  if(!resultsOfGame.equals("noResult")) {
				GameResults result = new GameResults();
			  }
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
  
	//Method checks if the last moved pawn has reached to the other side of the board
	//Test if the piece that just moved is a pawn
	//	if so then test if it has reached the end of the board
	//		if it has then let the user select what to promote the pawn to
	//	else do nothing
	public boolean checkPromotion(int y, char piece) {
		boolean promoted = false;
		//Check if the piece is a pawn
		if(piece == 'p' || piece == 'P') {
			//Check if piece has gotten to the end of the board
			//Tests for both sides of the board, doesn't matter how board is flipped,
			//Since pawns can't move backwards, should be okay to test if the pawn has
			//reached either side
			if(y == 0 || y == 7) {
				System.out.println("BoardPanel: Pawn was promoted!");
				PawnPromotion promote = new PawnPromotion();
				promoted = true;
			}
		}
		
		return promoted;
	}

}//end of BoardPanel class
