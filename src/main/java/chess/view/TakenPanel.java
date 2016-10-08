package chess;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class TakenPanel extends JPanel {

  // Placeholder for taken black and white piece panels
  public TakenPanel(String s) {
    JLabel label = new JLabel(s);
    this.add(label);
  }

}
