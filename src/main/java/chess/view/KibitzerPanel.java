package chess;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;  // awt.* does not import Action or Event Listeners
import javax.imageio.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class KibitzerPanel extends JPanel {

	//Might need to change it to use JFXPanel if we plan to add music or videos

	private static int secondsWait;
	private static Random rng = new Random();
	private static ArrayList<String> kibFiles = new ArrayList<String>();
	private static JLabel commentDisplay;

	private int widthPanel = 500;
	private int heightPanel = 200;

	private static boolean checkChessboardVisible = true;

	protected static Thread t;

	public KibitzerPanel() {

		//Add items to ArrayList
		kibFiles.add("/Kib/laboon.jpg");
		kibFiles.add("/Kib/lynx.jpeg");
		kibFiles.add("/Kib/cats_play_chess.jpeg");
		kibFiles.add("/Kib/queen_cat.jpeg");
		kibFiles.add("/Kib/facepalm.jpeg");
		kibFiles.add("/Kib/bday_cat.jpeg");
		kibFiles.add("/Kib/jeans_cat.jpeg");
		kibFiles.add("/Kib/cat_chess.jpeg");
		kibFiles.add("/Kib/taco_cat.jpeg");
		kibFiles.add("/Kib/yawn.jpeg");

		this.setPreferredSize(new Dimension(widthPanel, heightPanel));

		//Set background white
		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());

		//Generate random number based on the size of the list
		int randomInt = rng.nextInt(kibFiles.size());
		String fileName = kibFiles.get(randomInt);

		//Add image or effects to panel
		commentDisplay = new JLabel("");
		try{
			Image img = ImageIO.read(getClass().getResource(fileName));
			commentDisplay.setIcon(new ImageIcon(img));
			commentDisplay.setBorder(null);
		} catch(Exception e){
			String tempName = kibFiles.get(randomInt);
			commentDisplay.setText("Name in ArrayList: " + tempName);
			commentDisplay.setHorizontalAlignment(JLabel.CENTER);
			commentDisplay.setFont(new Font("Arial", Font.BOLD, 16));
		}

		commentDisplay.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(commentDisplay);

		t = new Thread(KibitzerPanel::changeDisplay);
		t.start();
	}

	private static void changeDisplay() {
		int testCounter = 0;

		checkChessboardVisible = true;

		while(checkChessboardVisible == true) {
			//Generate random number based on the size of the list
			int randomInt = ThreadLocalRandom.current().nextInt(0, (kibFiles.size() - 1));
			String fileName = kibFiles.get(randomInt);

			try{
				Image img = ImageIO.read(KibitzerPanel.class.getResource(fileName));
				commentDisplay.setIcon(new ImageIcon(img));
				commentDisplay.setBorder(null);
			} catch(Exception e){
				String tempName = kibFiles.get(randomInt);
				commentDisplay.setText("Name in ArrayList: " + tempName);
				commentDisplay.setHorizontalAlignment(JLabel.CENTER);
				commentDisplay.setFont(new Font("Arial", Font.BOLD, 16));
			}

			//Generate random number between 1 and 5
			//Convert to seconds
			int randomIntTime = ThreadLocalRandom.current().nextInt(1, 5);
			secondsWait = randomIntTime * 1000;

			System.out.println("Test Counter: " + testCounter + " - Thread waiting for: " + randomIntTime + " seconds");

			try {
				Thread.sleep(secondsWait);
			} catch (InterruptedException iex) {
				System.out.println("Something terrible has happened to the thread...");
				t.interrupt();
				t.start();
			}

			//Try to see if the board is open/visible
			try {
				checkChessboardVisible = ConsoleGraphics.frame.isShowing();
			}
			catch(NullPointerException ex) {
				checkChessboardVisible = false;
			}

			//Test to ensure that thread is working and changing images
			testCounter++;
		}

		//Board is not displayed on the screen, stop thread
		t.interrupt();
	}
}
