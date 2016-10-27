package chess;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;

public class TakenPanel extends JPanel {

  // Placeholder for taken black and white piece panels
  public TakenPanel(String s) {
		this.setBackground(Color.WHITE); 	//make it white
    JLabel label = new JLabel(s);
    this.add(label);
  }

}
