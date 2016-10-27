package chess;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  // awt.* does not import Action or Event Listeners

public class ButtonsPanel extends JPanel {

  protected JButton save;
  protected JButton load;

  // Makes the load and save buttons
  public ButtonsPanel() {
		this.setBackground(Color.WHITE); 	//make it white

    this.setLayout(new FlowLayout(FlowLayout.CENTER));

    // Add save button
    save = new JButton("Save");
    save.addActionListener(getSaveAction());
    this.add(save);

    // Add load button
    load = new JButton("Load");
    load.addActionListener(getLoadAction());
    this.add(load);
  }

  private ActionListener getSaveAction() {
    ActionListener action = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        //Save functionality
		    SaveGame saveGame = new SaveGame();
      }
    };
    return action;
  }

  private ActionListener getLoadAction() {
    ActionListener action = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
    		//Load functionality
    		LoadGame loadGame = new LoadGame();
      }
    };
    return action;
  }

}
