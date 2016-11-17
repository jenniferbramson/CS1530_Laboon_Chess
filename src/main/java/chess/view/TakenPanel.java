package chess;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;

public class TakenPanel extends JPanel {

  JLabel taken;  // array of taken pieces (total pieces with empty for untaken)

  // Placeholder for taken black and white piece panels
  public TakenPanel(String s) {
		this.setBackground(Color.WHITE); 	//make it white
    JLabel label = new JLabel(s);
    this.add(label);
    // gbl = new GridBagLayout();
		// gbc = new GridBagConstraints();
		// this.setLayout(gbl);
    // taken = new JLabels[8][8];
    // Insets margins = new Insets(0, 0, 0, 0);  // For setting button margins
  }

}
