package chess;
import javax.swing.*;
import java.awt.*;

public class BoardGraphics {

  // These are all buttons we will need to use listeners on
  private JButton[][] checkers;
  private JButton save;
  private JButton load;


  // Puts all the components together to create the screen
  public BoardGraphics() {
    JFrame frame = new JFrame("Laboon Chess");
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Container content = frame.getContentPane();   // Get reference to content pane

    JPanel timer = this.addTimer();
    JPanel board = this.addCheckers();            // Add the square board
    JPanel buttons = this.addButtons();

    JPanel left = new JPanel();
    left.setLayout(new BorderLayout());
    left.add(timer, BorderLayout.NORTH);
    left.add(board, BorderLayout.CENTER);
    left.add(buttons, BorderLayout.SOUTH);


    JPanel takenBlack = this.addTakenBlack();
    JPanel takenWhite = this.addTakenWhite();

    JPanel right = new JPanel();
    right.setLayout(new GridLayout(2, 1));
    right.add(takenBlack);
    right.add(takenWhite);


    content.setLayout(new BorderLayout());
    content.add(left, BorderLayout.CENTER);
    content.add(right, BorderLayout.EAST);

    frame.setSize(700, 600);
  }


  // Puts JButton array in a panel, then adds the panel to the frame's content
  // The JButton array are the squares
  private JPanel addCheckers() {
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(10, 10));

    checkers = new JButton[8][8];
    Insets margins = new Insets(0, 0, 0, 0);  // For setting button margins

    // Initialize squares
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        JButton b = new JButton("");
        b.setMargin(margins);     // Make the button have no margins
        b.setBorder(null);        // Necessary to not have button covering colors
        b.setOpaque(true);        // Necessary to see the colors (otherwise white)

        if ((i + j) % 2 == 0) {   // White square
          b.setBackground(Color.WHITE);
        } else {                  // Black Square
          b.setBackground(Color.BLACK);
        }

        checkers[i][j] = b;
      }
    }

    // Create Labels for a through h for the first and last rows
    panel.add(new JLabel(""));  // Corners are empty
    for (int i = 0; i < 8; i++) {
      JLabel label = new JLabel("" + (char)(97 + i));
      label.setHorizontalAlignment(SwingConstants.CENTER);
      panel.add(label);
    }
    panel.add(new JLabel(""));  // Corners are empty

    // Fill out the center of the panel
    for (int i = 0; i < 8; i++) {       // columns
      for (int j = 0; j < 10; j++) {    // rows
        if (j == 0 || j == 9) {
          // Beginning or end of row, add column number
          JLabel label = new JLabel("" + (8 - i));
          label.setHorizontalAlignment(SwingConstants.CENTER);
          panel.add(label);
        } else {
          // Add chess squares
          panel.add(checkers[i][j-1]);
        }
      }
    }

    // Fill out the last row of letters a through h
    panel.add(new JLabel(""));  // Corners are empty
    for (int i = 0; i < 8; i++) {
      JLabel label = new JLabel("" + (char)(97 + i));
      label.setHorizontalAlignment(SwingConstants.CENTER);
      panel.add(label);
    }
    panel.add(new JLabel(""));  // Corners are empty

    // Add the panel to the content
    return panel;
  } // end addCheckers


  private JPanel addButtons() {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

    // Add save button
    save = new JButton("Save");
    panel.add(save);

    // Add load button
    load = new JButton("Load");
    panel.add(load);

    return panel;
  }


  private JPanel addTimer() {
    JPanel panel = new JPanel();
    JLabel label = new JLabel("Timer");   // Place holder for real timer
    label.setHorizontalAlignment(SwingConstants.CENTER);
    panel.add(label);

    return panel;
  }


  private JPanel addTakenWhite() {
    JPanel panel = new JPanel();
    JLabel label = new JLabel("Taken White Pieces");
    label.setHorizontalAlignment(SwingConstants.CENTER);

    panel.add(label);
    return panel;
  }


  private JPanel addTakenBlack() {
    JPanel panel = new JPanel();
    JLabel label = new JLabel("Taken Black Pieces");
    label.setHorizontalAlignment(SwingConstants.CENTER);

    panel.add(label);
    return panel;
  }


  public static void main(String[] args) {
    BoardGraphics chessBoard = new BoardGraphics();
  }

}
