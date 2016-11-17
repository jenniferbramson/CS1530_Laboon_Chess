package chess;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Dimension;

public class ChessLabel extends JLabel {

  public ChessLabel(String input) {
    this.setText(input);
    this.setHorizontalAlignment(SwingConstants.CENTER);
    this.setBackground(new Color(52, 53, 54));
    this.setForeground(Color.WHITE);
    this.setOpaque(true);
  }

}
