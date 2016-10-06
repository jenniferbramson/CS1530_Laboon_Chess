package chess;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;  // awt.* does not import Action or Event Listeners

public class BoardPanel extends JPanel {

  // These are buttons we will need to use listeners on
  protected JButton[][] checkers;

  // Makes the checkerboard with a JPanel array and adds JLabels around it to
  // label the rows 1 to 8 and the columns a to h
  public BoardPanel() {
    this.setLayout(new GridLayout(10, 10));

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
		String firstrow = "brkbqnrn";
		for(int i=0; i<firstrow.length();i++){
			checkers[0][i].setText(String.valueOf(firstrow.charAt(i)));
		}
		for(int i=0; i<firstrow.length(); i++){
			checkers[1][i].setText("p");
		}
		
		for(int i=0; i<firstrow.length();i++){
			checkers[6][i].setText("P");
		}
		String lastrow = "BRKBQNRN";
		for(int i=0; i<firstrow.length();i++){
			checkers[7][i].setText(String.valueOf(lastrow.charAt(i)));
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

      }
    };
    return action;
  }

}
