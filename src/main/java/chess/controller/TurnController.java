package chess;

// Tells whose turn it is. Must call changeTurn() after each turn is made.
// Tells if it is white or black's turn and if it is the player's or the
// computer's turn.
public class TurnController {

  private boolean player = true;  // Default: start off with player's turn
  private char turn;
  private ConsoleGraphics graphics;
  private boolean graphicsExist = false;

  public TurnController() {
    turn = 'w';
  }

  public TurnController(char c) {
    if (c == 'b' || c == 'w') {
      turn = c;
    } else {
      System.out.println("Error. Invalid TurnController input.");
      System.exit(2);
    }
  }

  // Graphics must contain setBlack() and setWhite() methods.
  public void addGraphicalTurn(ConsoleGraphics graphics) {
    this.graphics = graphics;
    graphicsExist = true;
    if (turn == 'b') {
      graphics.setBlack();
    } else if (turn == 'w') {
      graphics.setWhite();
    }
  }

  public boolean getPlayersTurn() {
    return player;
  }

  // Set to false initially if the player chooses black, true if white.
  public void setPlayersTurn(boolean playersChoice) {
    this.player = playersChoice;
  }

  public void changeTurn() {
    if (turn == 'w') {
      turn = 'b';
      if (graphicsExist) {
        graphics.setBlack();
      }
    } else {
      turn = 'w';
      if (graphicsExist) {
        graphics.setWhite();
      }
    }

    if (player) {
      player = false; // Comment this out and start as white to test black/white
                      // turn switching
    } else {
      player = true;
    }
  }

  public char getTurn() {
    return turn;
  }

}
