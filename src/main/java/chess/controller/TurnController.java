package chess;

// Tells whose turn it is. Must call changeTurn() after each turn is made.
// Tells if it is white or black's turn and if it is the player's or the
// computer's turn.
public class TurnController {

  private boolean playersTurn = true;  // Default: start off with player's turn
  protected static char playersColor;
  private char turn;
  private ConsoleGraphics graphics;
  private boolean graphicsExist = false;

  public TurnController(char playersColor) {
    turn = 'w';
    this.playersColor = playersColor;
    if (playersColor == 'w') {
      playersTurn = true;
    } else if (playersColor == 'b') {
      playersTurn = false;
    } else {
      System.out.println("Error. Invalid TurnController input.");
      System.exit(2);
    }
  }

  public TurnController(char currentColor, char playersColor) {
    if ((currentColor == 'b' || currentColor == 'w') && (playersColor == 'b' || playersColor == 'w')) {
      turn = currentColor;
      this.playersColor = playersColor;
      if (playersColor == currentColor) {
        playersTurn = true;
      } else {
        playersTurn = false;
      }
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
    return playersTurn;
  }

  public char getPlayersColor() {
    return playersColor;
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

    if (playersTurn) {
      //playersTurn = false;  // Comment this out and start as white to test black/white
                            // turn switching
    } else {
      playersTurn = true;
    }
  }

  public char getTurn() {
    return turn;
  }

}
