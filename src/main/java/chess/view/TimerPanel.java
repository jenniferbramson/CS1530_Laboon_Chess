package chess;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class TimerPanel extends JPanel {

  // Placeholder for timer
  public TimerPanel() {
    JLabel label = new JLabel("Timer");   // Place holder for real timer
    label.setHorizontalAlignment(SwingConstants.CENTER);
    this.add(label);
  }

}
