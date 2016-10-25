package chess;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  // awt.* does not import Action or Event Listeners

public class ConsoleGraphics extends JFrame {

  // These are buttons we will need to use listeners on
  private Toolkit t;
  private Dimension screen;
  protected JButton whiteTurn;
  protected JButton blackTurn;
  protected static JFrame frame;
  protected static final Stockfish stockfish = new Stockfish();

  // Puts all the components together to create the screen
  public ConsoleGraphics() {

    stockfish.startEngine();

    frame = new JFrame("Laboon Chess");
    Container content = frame.getContentPane();   // Get reference to content pane

    // Left side of the board has the timer, chess board, and buttons (load and
    // save) stacked vertically
    TimerPanel timer = new TimerPanel();           // Get the timer panel
    BoardPanel board = new BoardPanel();           // Get the square board
    ButtonsPanel buttons = new ButtonsPanel();     // Get the buttons panel

    JPanel left = new JPanel();                    // Stack the three panels above
    left.setLayout(new BorderLayout());
    left.add(timer, BorderLayout.NORTH);
    left.add(board, BorderLayout.CENTER);
    left.add(buttons, BorderLayout.SOUTH);

    // Middle part of board has the turn signals
    whiteTurn = multiLineBtn("White's", "Turn");
    blackTurn = multiLineBtn("Black's", "Turn");

    // Right side of the board has the the taken black and white pieces stacked
    // vertically
    TakenPanel takenBlack = new TakenPanel("Taken Black Pieces");
    TakenPanel takenWhite = new TakenPanel("Taken White Pieces");

    // Entire screen has both the leftand right sides of the board put together
    // in a horizontal fashion
    content.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(10, 10, 10, 10);
    c.gridx = 0;              // specifies which column
    c.gridy = 0;              // specifies which row
    c.gridheight = 12;        // gridheight = number of rows it takes up
    c.ipadx = 250;            // ipad = size in pixels
    c.ipady = 250;
    content.add(left, c);
    c.gridheight = 2;
    c.gridx = 1;
    c.gridy = 5;
    c.ipadx = 10;
    c.ipady = 10;
    content.add(blackTurn, c);
    c.gridx = 1;
    c.gridy = 10;
    c.ipadx = 10;
    c.ipady = 11;
    content.add(whiteTurn, c);
    c.gridx = 2;
    c.gridy = 0;
    c.gridheight = 6;
    c.ipadx = 200;
    c.ipady = 200;
    content.add(takenBlack, c);
    c.gridx = 2;
    c.gridy = 6;
    c.gridheight = 6;
    c.ipadx = 200;
    c.ipady = 200;
    content.add(takenWhite, c);

    //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.pack();

    //Get size of computer screen
    //Set the screen so it appears in the middle
    t = getToolkit();
    screen = t.getScreenSize();
    frame.setLocation(screen.width/2-frame.getWidth()/2,screen.height/2-frame.getHeight()/2);

		//frame.setSize(screenSize.width,screenSize.height);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        stockfish.stopEngine();
      }
    });


    frame.setVisible(true);                     // Do this last
  }


  // Creates a multi-line button. The multi line part of this code is from
  // http://www.javaworld.com/article/2077368/learn-java/a-multiline-button-is-possible.html
  private JButton multiLineBtn(String s1, String s2) {
    JButton b = new JButton();
    b.setLayout(new BorderLayout());
    JLabel label1 = new JLabel(s1);
    label1.setHorizontalAlignment(SwingConstants.CENTER);
    JLabel label2 = new JLabel(s2);
    label2.setHorizontalAlignment(SwingConstants.CENTER);
    b.add(BorderLayout.NORTH,label1);
    b.add(BorderLayout.SOUTH,label2);
    b.setOpaque(true);            // Necessary to see background color
    b.setBackground(Color.WHITE);
    b.setBorder(BorderFactory.createLineBorder(Color.black));
    //b.addActionListener(getTurnAction());
    return b;
  }
/*
  private ActionListener getTurnAction() {
    ActionListener action = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // Placeholder for when we add functionality

      }
    };
    return action;
  }
*/

  public void setWhite() {
    blackTurn.setBackground(Color.WHITE);
    whiteTurn.setBackground(Color.YELLOW);
  }

  public void setBlack() {
    whiteTurn.setBackground(Color.WHITE);
    blackTurn.setBackground(Color.YELLOW);
  }

  public static void main(String[] args) {
    ConsoleGraphics chessBoard = new ConsoleGraphics();
  }

}
