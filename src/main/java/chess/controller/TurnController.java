package chess;

// Tells whose turn it is. Must call changeTurn() after each turn is made.
// Tells if it is white or black's turn and if it is the player's or the
// computer's turn.
public class TurnController {

  private boolean playersTurn = true;  // Default: start off with player's turn
  private char playersColor;
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
      playersTurn = false;  // Comment this out and start as white to test black/white
      playMoveFromStockfish();

                            // turn switching
    } else {
      playersTurn = true;
    }
  }

  public char getTurn() {
    return turn;
  }

   public void playMoveFromStockfish(){

    String bestMove = LaboonChess.stockfish.getBestMove(BoardPanel.my_storage.getFen(), 1000);
    System.out.println("best mvoe from stockfish " + bestMove);
    LaboonChess.stockfish.movePiece(bestMove, BoardPanel.my_storage.getFen());
    String fen = LaboonChess.stockfish.getFen();
    System.out.println("New fen " + fen);
    BoardPanel.my_storage.setFen(fen);
    char old_x_board = bestMove.charAt(0);
    int old_x = (int)old_x_board - 97;
    // old_x = 8-old_x;
    int old_y = Integer.parseInt(bestMove.substring(1,2));
    old_y = 8-old_y;
    char x_board = bestMove.charAt(2);
    int x = (int) x_board - 97;
    // x = 8/**/x;
    int y = Integer.parseInt(bestMove.substring(3,4));
    y = 8 - y;
    System.out.println("oldxboard: " + old_x_board + " oldx : " + old_x + " old_y " + old_y);
    System.out.println("newxboard " + x_board + " newx: " + x + " newy " + y);
    BoardPanel.my_storage.movePiece(old_y, old_x, y, x);
    LaboonChess.stockfish.drawBoard();
    //BoardPanel.setPiecesEx tern();
    // BoardPanel.setPieces();
  // Switch whose turn it is
  LaboonChess.changeTurn();

  }

}
