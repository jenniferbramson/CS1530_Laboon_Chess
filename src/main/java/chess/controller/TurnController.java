package chess;

// Tells whose turn it is. Must call changeTurn() after each turn is made.
public class TurnController {

  private char turn;

  public TurnController() {
    turn = 'w';
  }

  public void changeTurn() {
    if (turn == 'w') {
      turn = 'b';
    } else {
      turn = 'w';
    }
  }

  public char getTurn() {
    return turn;
  }

}
