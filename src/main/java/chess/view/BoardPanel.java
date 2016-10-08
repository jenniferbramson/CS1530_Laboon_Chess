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
	private Storage my_storage;
	boolean second_click = false;
	private String old_spot = "";

  // Makes the checkerboard with a JPanel array and adds JLabels around it to
  // label the rows 1 to 8 and the columns a to h
  public BoardPanel() {
    this.setLayout(new GridLayout(10, 10));
		my_storage = new Storage();
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

		//Added to try and draw letters
		for(int i=0; i<8;i++){
			for(int j=0; j<8;j++){
				//checkers[i][j].setText(String.valueOf(my_storage.getSpace(i,j)));
				if (i == 6) {
					try {
					Image img = ImageIO.read(getClass().getResource("/WhitePawn.png"));
					//Modify the values to get desired pixel width/height
					img = img.getScaledInstance(32, 32, Image.SCALE_DEFAULT);
					checkers[i][j].setIcon(new ImageIcon(img));
					} catch (IOException ex) {
					// Error
					}
				}
			}
		}
		//------------------------------------

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
				if(second_click){
					System.out.println("Moving " + old_spot + " to " + current_spot);
					second_click = false;
				}
				else{
					System.out.println("First click");
					old_spot = current_spot;
					second_click = true;
				}
      }
    };
    return action;
  }

}
