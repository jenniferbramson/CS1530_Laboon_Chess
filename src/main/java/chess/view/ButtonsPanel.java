package chess;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  // awt.* does not import Action or Event Listeners

public class ButtonsPanel extends JPanel {

  protected JButton save;
  protected JButton load;

  // Makes the load and save buttons
  public ButtonsPanel() {
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
        // Placeholder for when we add functionality
		//Run the save file window 
      }
    };
    return action;
  }

  private ActionListener getLoadAction() {
    ActionListener action = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // Placeholder for when we add functionality
		
		//Get fen that was read in from file and current fen
		//If fen are different then ask if user wants to save before loading new game
		//If user hits save then open up the save menu
		//else load up current menu
		//If user wants to return then close load and go back to game
		LoadGame loadGame = new LoadGame();
		//Dispose of startup screen so it's not visible
      }
    };
    return action;
  }

}
