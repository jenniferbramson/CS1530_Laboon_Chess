package chess;

// Tells whose turn it is. Must call changeTurn() after each turn is made.
public class TurnController {

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

  public void addGraphicalTurn(ConsoleGraphics graphics) {
    this.graphics = graphics;
    graphicsExist = true;
    if (turn == 'b') {
      graphics.setBlack();
    } else if (turn == 'w') {
      graphics.setWhite();
    }
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
  }

  public char getTurn() {
    return turn;
  }

}
