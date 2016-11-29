package chess;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;  // awt.* does not import Action or Event Listeners
import javax.imageio.*;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class ConsoleGraphics extends JFrame {

	protected static JFrame frame;
  private Toolkit t;
  private Dimension screen;
  protected JLabel whiteTurn;
  protected JLabel blackTurn;
	protected static BoardPanel board;
	protected ButtonsPanel buttons;
	protected static KibitzerPanel kib;
	protected Container content;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	String[] colors = {"Default", "Red", "Orange", "Yellow", "Green" ,"Blue" , "Purple", "PARTYTIME"};
	Color TURQUOISE = new Color(64,224,208);
	//needed for flipping
	protected boolean flipped = false;
	protected JComboBox blackColorList;
	protected JComboBox whiteColorList;
	protected JLabel blackColorLabel;
	protected JLabel whiteColorLabel;
	protected JLabel background;




  // Puts all the components together to create the screen
  public ConsoleGraphics() {

    frame = new JFrame("Laboon Chess");
    content = frame.getContentPane();   // Get reference to content pane

		// Add background image
		Image woodenTable = readImage();
		if (woodenTable != null) {					// If found wood image, set background
			woodenTable = getScaledImage(woodenTable, 1200, 700);
			background = new JLabel(new ImageIcon(woodenTable));
			content.add(background);
		}

    // Left side of the board has the chess board, flip board, and buttons
		// (load and save) stacked vertically
		JButton flipButton = new JButton("Flip Board");
		flipButton.addActionListener(flipBoard());
    board = new BoardPanel();           // Get the square board
    buttons = new ButtonsPanel();     // Get the buttons panel
		kib = new KibitzerPanel();

    JPanel left = new JPanel();                    // Stack the three panels above
    left.setLayout(new BorderLayout());
    left.add(flipButton, BorderLayout.NORTH);
    left.add(board, BorderLayout.CENTER);
    left.add(buttons, BorderLayout.SOUTH);
		left.setOpaque(false);
		left.add(kib, BorderLayout.WEST);

    // Middle part of board has the turn signals
    whiteTurn = playerTurnButton("White");
    blackTurn = playerTurnButton("Black");


		//drop list for black side pieces
		blackColorList = new JComboBox(colors);
		blackColorList.setSelectedIndex(0);
		blackColorList.addActionListener(updateBlackList());
		//drop list for white side pieces
		whiteColorList = new JComboBox(colors);
		whiteColorList.setSelectedIndex(0);
		whiteColorList.addActionListener(updateWhiteList());
		//labels for the lists
		whiteColorLabel = new JLabel("White Piece color: ");
		blackColorLabel = new JLabel("Black Piece color: ");
		Border empty = BorderFactory.createEmptyBorder(2,4,2,4);
		blackColorLabel.setOpaque(true);
		whiteColorLabel.setOpaque(true);
		blackColorLabel.setBorder(empty);
		whiteColorLabel.setBorder(empty);
		whiteColorLabel.setBackground(Color.WHITE);
		blackColorLabel.setBackground(Color.WHITE);
		JLabel spacing = new JLabel("");
		spacing.setPreferredSize(new Dimension(100,140));	//spacing for making sure the buttons are centered

    // Right side of the board has the the taken black and white pieces stacked
    // vertically
    //TakenPanel takenBlack = new TakenPanel("Taken Black Pieces");
    //TakenPanel takenWhite = new TakenPanel("Taken White Pieces");

    // Setting up layout stuff
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		background.setLayout(gbl);

		//NOTE: addComponent parameters are: x,y, width, height, ipadx, ipady, component
		addComponent(0,0, 12, 12, 0, 0, left);
		addComponent(13,1,1,1,0,0, spacing);

		//add the buttons and lists that appear on the right side
		addComponentSet();

		frame.pack();

    //Get size of computer screen
    //Set the screen so it appears in the middle
    t = getToolkit();
    screen = t.getScreenSize();
    frame.setLocation(screen.width/2-frame.getWidth()/2,screen.height/2-frame.getHeight()/2);

		//frame.setSize(screen.width,screen.height);  // For maybe fitting game to screen later
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Listen for the window closing and stop the stockfish process when it closes
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        LaboonChess.stockfish.stopEngine();
      }
    });

    frame.setVisible(true);                     // Do this last

  	if (!(LaboonChess.getPlayersTurn()) & !board.firstTurnTaken){
  		board.playFirstTurnWithStockfish();
  	}
  }

	// From http://stackoverflow.com/questions/6714045/how-to-resize-jlabel-imageicon
	private Image getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}

	private Image readImage() {
		Image woodenTable;
		try {
			woodenTable = ImageIO.read(getClass().getResource("/wood.jpeg"));
		} catch (IOException e) {
			System.out.println("Error reading background image");
			woodenTable = null;
		}
		return woodenTable;
	}

  // method that builds the "player turn" button
	// If there is an issue reading the image, creates a multi-line button.
  private JLabel playerTurnButton(String s) {
    JLabel b = new JLabel();
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
			//b.setBorderPainted(false);
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
	//helper method for playerTurnButton
  public void setWhite() {
    blackTurn.setBackground(Color.WHITE);
    whiteTurn.setBackground(TURQUOISE);
  }
	//helper method for playerTurnButton
  public void setBlack() {
    whiteTurn.setBackground(Color.WHITE);
    blackTurn.setBackground(TURQUOISE);
  }

	private ActionListener updateWhiteList() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e){
				JComboBox cb = (JComboBox)e.getSource();
        String colorName = (String)cb.getSelectedItem();
				setWhiteColor(colorName);
			}
		};
		return action;
	}

	private ActionListener updateBlackList() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e){
				JComboBox cb = (JComboBox)e.getSource();
        String colorName = (String)cb.getSelectedItem();
				setBlackColor(colorName);
			}
		};
		return action;
	}

	private void setWhiteColor(String color){
		if(color.equals("Red")){
			board.setParty(false, true);								//false for no party, true for white
			board.colorizeWhite(true, 150, 0, 0);
		}
		else if(color.equals("Orange")){
			board.setParty(false, true);
			board.colorizeWhite(true, 150, 75, 0);
		}
		else if(color.equals("Yellow")){
			board.setParty(false, true);
			board.colorizeWhite(true, 150, 150, 0);
		}
		else if(color.equals("Green")){
			board.setParty(false, true);
			board.colorizeWhite(true, 0, 150, 50);
		}
		else if(color.equals("Blue")){
			board.setParty(false, true);
			board.colorizeWhite(true, 0, 0, 150);
		}
		else if(color.equals("Purple")){
			board.setParty(false, true);
			board.colorizeWhite(true, 75, 0, 150);
		}
		else if(color.equals("PARTYTIME")){
			board.colorizeWhite(false,0,0,0);		//unhue any pieces
			board.setParty(true, true);		//true for party, true for white
		}
		else{
			board.setParty(false, true);
			board.colorizeWhite(false, 0, 0, 0);
		}
	}

	//function that colorizes the black side pieces
	private void setBlackColor(String color){
		if(color.equals("Red")){
			board.setParty(false, false);
			board.colorizeBlack(true, 150, 0, 0);
		}
		else if(color.equals("Orange")){
			board.setParty(false, false);
			board.colorizeBlack(true, 150, 75, 0);
		}
		else if(color.equals("Yellow")){
			board.setParty(false, false);
			board.colorizeBlack(true, 150, 150, 0);
		}
		else if(color.equals("Green")){
			board.setParty(false, false);
			board.colorizeBlack(true, 0, 150, 50);
		}
		else if(color.equals("Blue")){
			board.setParty(false, false);
			board.colorizeBlack(true, 0, 0, 150);
		}
		else if(color.equals("Purple")){
			board.setParty(false, false);
			board.colorizeBlack(true, 75, 0, 150);
		}
		else if(color.equals("PARTYTIME")){
			board.setParty(false, false);
			board.colorizeBlack(false,0,0,0); //unhue any pieces
			board.setParty(true, false);		//true for party, false for black
		}
		else{
			board.setParty(false, false);
			board.colorizeBlack(false, 0, 0, 0);
		}
	}

	//helper function for adding components into the  frame
	public void addComponent(int x, int y, int w, int h, int padx, int pady, Component aComponent) {
		Insets inset = new Insets(10, 10, 10, 10);
		gbc.insets = inset;
		gbc.gridx=x;
		gbc.gridy=y;
		gbc.gridwidth = w;
		gbc.gridheight = h;
		gbc.ipadx = padx;
		gbc.ipady = pady;
		gbl.setConstraints(aComponent,gbc);
		background.add(aComponent);
	}

	//method that flips the arrangement of buttons and flips board
	private ActionListener flipBoard() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e){
				//switch the flip
				flipped = !flipped;
				//clear so they'll appear later
				clearComponentSet();
				//re-set the images (addComponentSet already knows the order)
				addComponentSet();
				//call the boardpanel to flip itself
				board.setFlip(flipped);
				//redraw
				background.revalidate();
				background.repaint();
			}
		};
		return action;
	}

	private void addComponentSet(){
			if(!flipped){
				addComponent(13,5,1,1, 0, 0, blackTurn);
				addComponent(13,6,1,1, 0, 0, blackColorLabel);
				addComponent(13,7,1,1,0, 0, blackColorList);
				addComponent(13,8,1,1,0,0, whiteColorLabel);
				addComponent(13,9,1,1,0,0, whiteColorList);
				addComponent(13,10,1,1,0, 0, whiteTurn);
			}
			else{
				addComponent(13,5,1,1, 0, 0, whiteTurn);
				addComponent(13,6,1,1, 0, 0, whiteColorLabel);
				addComponent(13,7,1,1,0, 0, whiteColorList);
				addComponent(13,8,1,1,0,0, blackColorLabel);
				addComponent(13,9,1,1,0,0, blackColorList);
				addComponent(13,10,1,1,0, 0, blackTurn);
			}

	}

	private void clearComponentSet(){
		background.remove(blackTurn);
		background.remove(blackColorLabel);
		background.remove(blackColorList);
		background.remove(whiteColorLabel);
		background.remove(whiteColorList);
		background.remove(whiteTurn);
	}

  public static void main(String[] args) {
    ConsoleGraphics chessBoard = new ConsoleGraphics();
  }



}
