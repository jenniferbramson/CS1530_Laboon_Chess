package chess;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;  // awt.* does not import Action or Event Listeners
import javax.imageio.*;

public class ConsoleGraphics extends JFrame {

  // These are buttons we will need to use listeners on
  private Toolkit t;
  private Dimension screen;
  protected JButton whiteTurn;
  protected JButton blackTurn;
  protected static JFrame frame;
	protected JButton blackColorButton;
	protected JButton whiteColorButton;
	boolean whiteColorize = false;
	boolean blackColorize = false;
	BoardPanel board;
	ButtonsPanel buttons;
	TimerPanel timer;

  // The stockfish process will be used as the NPC and also to generate fen strings
  protected static final Stockfish stockfish = new Stockfish();

  // Puts all the components together to create the screen
  public ConsoleGraphics() {

    // Start stockfish process
    stockfish.startEngine();

    frame = new JFrame("Laboon Chess");
    Container content = frame.getContentPane();   // Get reference to content pane
		content.setBackground(Color.WHITE); 	//make it white

    // Left side of the board has the timer, chess board, and buttons (load and
    // save) stacked vertically
		timer = new TimerPanel();           // Get the timer panel
    board = new BoardPanel();           // Get the square board
    buttons = new ButtonsPanel();     // Get the buttons panel

    JPanel left = new JPanel();                    // Stack the three panels above
    left.setLayout(new BorderLayout());
    left.add(timer, BorderLayout.NORTH);
    left.add(board, BorderLayout.CENTER);
    left.add(buttons, BorderLayout.SOUTH);

    // Middle part of board has the turn signals
    whiteTurn = playerTurnButton("White");
    blackTurn = playerTurnButton("Black");
		
		blackColorButton = new JButton("Change Black to Green");
		blackColorButton.addActionListener(changeBlackColor());
		whiteColorButton = new JButton("Change White to Red");
		whiteColorButton.addActionListener(changeWhiteColor());

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
    c.ipadx = 50;            // ipad = size in pixels
    c.ipady = 50;
    content.add(left, c);
    c.gridheight = 2;
    c.gridx = 1;
    c.gridy = 5;
    c.ipadx = 0;
    c.ipady = 0;
    content.add(blackTurn, c);
    c.gridx = 1;
    c.gridy = 10;
    c.ipadx = 0;
    c.ipady = 0;
    content.add(whiteTurn, c);
		c.gridx = 1;
		c.gridy = 7;
		content.add(blackColorButton, c);
		c.gridx = 1;
		c.gridy = 9;
		content.add(whiteColorButton, c);
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

		frame.pack();

    //Get size of computer screen
    //Set the screen so it appears in the middle
    t = getToolkit();
    screen = t.getScreenSize();
    frame.setLocation(screen.width/2-frame.getWidth()/2,screen.height/2-frame.getHeight()/2);

		//frame.setSize(screen.width,screen.height);  // For maybe fitting game to screen later
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
  private JButton playerTurnButton(String s) {
    JButton b = new JButton();
		try{
			//just initializing to black or compiler complains about it not being initialized
			Image img = ImageIO.read(getClass().getResource("/BlackTurn.png"));
			//if it's white, load the proper image
			if(s.equals("White")){
				img = ImageIO.read(getClass().getResource("/WhiteTurn.png"));
			}
			b.setIcon(new ImageIcon(img));
      b.setOpaque(true);            // Necessary to see background color
			b.setBackground(Color.WHITE);
			b.setPreferredSize(new Dimension(62, 58));
			b.setBorderPainted(false);
		} catch( Exception e){
			//if something went wrong, use the old buttons
			b.setLayout(new BorderLayout());
			JLabel label1 = new JLabel(s);
			label1.setHorizontalAlignment(SwingConstants.CENTER);
			JLabel label2 = new JLabel("Turn");
			label2.setHorizontalAlignment(SwingConstants.CENTER);
			b.add(BorderLayout.NORTH,label1);
			b.add(BorderLayout.SOUTH,label2);
			b.setOpaque(true);            // Necessary to see background color
			b.setBackground(Color.WHITE);
			b.setBorder(BorderFactory.createLineBorder(Color.black));
		}
    return b;
  }

  public void setWhite() {
    blackTurn.setBackground(Color.WHITE);
    whiteTurn.setBackground(Color.YELLOW);
  }

  public void setBlack() {
    whiteTurn.setBackground(Color.WHITE);
    blackTurn.setBackground(Color.YELLOW);
  }
	
	private ActionListener changeWhiteColor() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.colorizeWhite(!(whiteColorize), 150, 0, 0);
				whiteColorize = !whiteColorize;
			}
		};
		return action;
	}
	
	private ActionListener changeBlackColor() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.colorizeBlack(!(blackColorize), 0, 150, 0);
				blackColorize = !blackColorize;
			}
		};
		return action;
	}

  public static void main(String[] args) {
    ConsoleGraphics chessBoard = new ConsoleGraphics();
  }

}
