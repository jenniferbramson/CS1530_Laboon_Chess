package chess;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;  // awt.* does not import Action or Event Listeners
import java.io.*;
import javax.imageio.*;

public class BoardPanel extends JPanel {

  // These are buttons we will need to use listeners on
  protected JButton[][] checkers;
	protected static Storage my_storage;
  private Rulebook my_rulebook;
	boolean second_click = false;
	private String old_spot = "";
	private int old_x = 0;
	private int old_y = 0;

	private int imageWidth = 32;
	private int imageHeight = 32;

	protected static String lastSaveFen;

  // Makes the checkerboard with a JPanel array and adds JLabels around it to
  // label the rows 1 to 8 and the columns a to h
  public BoardPanel() {
    this.setLayout(new GridLayout(10, 10));

	//Check if fen has been found from file
	//No fen was loaded from a file
	if(LoadPanel.fen == "" || LoadPanel.fen == null) {
		System.out.println("No fen to load");
		//default storage constructor
		my_storage = new Storage();
		lastSaveFen = null;
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
    checkers = new JButton[8][8];
    Insets margins = new Insets(0, 0, 0, 0);  // For setting button margins

    // Initialize squares
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        JButton b = new JButton("");
				b.setPreferredSize(new Dimension(64, 64));					//set prefered size to 64px by 64px
				//ImageIcon icon = new    ImageIcon(this.getClass().getResource("/img/WhitePawn.png"));  //get image
        b.setMargin(margins);     // Make the button have no margins
        b.setOpaque(true);        // Necessary to see the colors (otherwise white)
        b.addActionListener(getSquareAction());
        b.setBorder(null);        // Necessary to not have button covering colors
        b.setBorderPainted(false);  // Necessary for mac icon transparency
				//b.setFont(new Font("Arial", Font.BOLD, 20));				//make the letters big
        if ((i + j) % 2 == 0) {   // White square
          b.setBackground(Color.WHITE);
        } else {                  // Black Square
          b.setBackground(Color.GRAY);
        }

				//b.setIcon(icon);

        checkers[i][j] = b;
      }
    }

		drawPieces();				//call method to draw the pieces

    // Create Labels for a through h for the first rows
    this.add(new JLabel(""));  // Corners are empty
    for (int i = 0; i < 8; i++) {
      JLabel label = new JLabel("" + (char)(97 + i));
      label.setHorizontalAlignment(SwingConstants.CENTER);
      this.add(label);
    }
    this.add(new JLabel(""));  // Corners are empty

    // Fill out the center of the panel
    for (int i = 0; i < 8; i++) {       // columns
      for (int j = 0; j < 10; j++) {    // rows
        if (j == 0 || j == 9) {
          // Beginning or end of row, add column number
          JLabel label = new JLabel("" + (8 - i));
          label.setHorizontalAlignment(SwingConstants.CENTER);
          this.add(label);
        } else {
          // Add chess squares
          this.add(checkers[i][j-1]);
        }
      }
    }

    // Fill out the last row of letters a through h
    this.add(new JLabel(""));  // Corners are empty
    for (int i = 0; i < 8; i++) {
      JLabel label = new JLabel("" + (char)(97 + i));
      label.setHorizontalAlignment(SwingConstants.CENTER);
      this.add(label);
    }
    this.add(new JLabel(""));  // Corners are empty

    this.setBorder(new LineBorder(Color.BLACK));
  }

	/*-----------------------------------------------------------------------------------*/
	private void drawPieces(){
		//Added to try and draw letters
		for(int i=0; i<8;i++){
			for(int j=0; j<8;j++){
				checkers[i][j].setIcon(null);			//clear it before you draw (if something was there previously)
				char c = my_storage.getSpace(i,j, true);
				try{
							Image img;
					switch(c){
						case 'p':
							img = ImageIO.read(getClass().getResource("/BlackPawn.png"));
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;
						case 'b':
							img = ImageIO.read(getClass().getResource("/BlackBishop.png"));
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;
						case 'r':
							img = ImageIO.read(getClass().getResource("/BlackRook.png"));
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;
						case 'k':
							img = ImageIO.read(getClass().getResource("/BlackKing.png"));
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;
						case 'q':
							img = ImageIO.read(getClass().getResource("/BlackQueen.png"));
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;
						case 'n':
							img = ImageIO.read(getClass().getResource("/BlackKnight.png"));
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;

						case 'P':
							img = ImageIO.read(getClass().getResource("/WhitePawn.png"));
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;
						case 'B':
							img = ImageIO.read(getClass().getResource("/WhiteBishop.png"));
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;
						case 'R':
							img = ImageIO.read(getClass().getResource("/WhiteRook.png"));
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;
						case 'K':
							img = ImageIO.read(getClass().getResource("/WhiteKing.png"));
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;
						case 'Q':
							img = ImageIO.read(getClass().getResource("/WhiteQueen.png"));
							img = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
							checkers[i][j].setIcon(new ImageIcon(img));
							break;
						case 'N':
							img = ImageIO.read(getClass().getResource("/WhiteKnight.png"));
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
    					System.out.println("Moving " + old_spot + " to " + current_spot);
    					if ( (old_x+old_y) % 2== 0) {
                checkers[old_y][old_x].setBackground(Color.WHITE);
              } else {
                checkers[old_y][old_x].setBackground(Color.GRAY);
              }
    					second_click = false;
    					my_storage.movePiece(old_y, old_x, y, x);
    					//redraw
    					drawPieces();
            } // end legality move check
          } // end checking move
				} // end second click
				else{
					checkers[y][x].setBackground(Color.GREEN);
					System.out.println("First click");
					old_spot = current_spot;
					old_x = x;
					old_y = y;
					second_click = true;
				}

				//for testing ONLY
				if(x==0 && y==0){
					System.out.println(my_storage.getFen());
				}
      }
    };
    return action;
  }
}
