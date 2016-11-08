package chess;

import java.util.concurrent.TimeUnit;
import javax.swing.SwingWorker;
import java.awt.Color;

// Tells whose turn it is. Must call changeTurn() after each turn is made.
// Tells if it is white or black's turn and if it is the player's or the
// computer's turn.
public class TurnController {

  private boolean playersTurn = true;  // Default: start off with player's turn
  private char playersColor;
  private char turn;
  private ConsoleGraphics graphics;
  private boolean graphicsExist = false;
  boolean promotion = false;

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

  public void setPlayersTurn(boolean value){
    playersTurn = value;
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

                            // turn switching
    } else {
      playersTurn = true;
    }
  }

  public void firstStockfishTurn(){
    int [] move = getMoveFromStockfish(false);
    playMoveFromStockfish(move);
  }

  public char getTurn() {
    return turn;
  }

   public void playMoveFromStockfish(int[] move){

    int old_y = move[0];
    int old_x = move[1];
    int y = move[2];
    int x = move[3];
    
  	if ( (old_x+old_y) % 2== 0) {
			BoardPanel.checkers[old_y][old_x].setBackground(Color.WHITE);
		} else {
			BoardPanel.checkers[old_y][old_x].setBackground(Color.GRAY);
		}
		
		if ( (x+y) % 2== 0) {
			BoardPanel.checkers[y][x].setBackground(Color.WHITE);
		} else {
			BoardPanel.checkers[y][x].setBackground(Color.GRAY);
		}
    
    BoardPanel.my_storage.movePiece(old_y, old_x, y, x);
    LaboonChess.stockfish.drawBoard();
    LaboonChess.changeTurn();

  }
  
  public int[]  getMoveFromStockfish(boolean wait) {
    
    
    String bestMove = LaboonChess.stockfish.getBestMove(BoardPanel.my_storage.getFen(), 1000);
    if (bestMove.charAt(4) != ' ') promotion = true;
      
    System.out.println("best move from stockfish " + bestMove);
    LaboonChess.stockfish.movePiece(bestMove, BoardPanel.my_storage.getFen());
    String fen = LaboonChess.stockfish.getFen();
    System.out.println("New fen " + fen);
    BoardPanel.my_storage.setFen(fen);
    char old_x_board = bestMove.charAt(0);
    int old_x = (int)old_x_board - 97;
    int old_y = Integer.parseInt(bestMove.substring(1,2));
    old_y = 8-old_y;
    char x_board = bestMove.charAt(2);
    int x = (int) x_board - 97;
    int y = Integer.parseInt(bestMove.substring(3,4));
    y = 8 - y;
    
    Sleeper sleeper = new Sleeper();
    if (wait) sleeper.doInBackground();
    
    Color o1 =  BoardPanel.checkers[old_y][old_x].getBackground();
    Color o2 = BoardPanel.checkers[y][x].getBackground();
    
    BoardPanel.checkers[old_y][old_x].setBackground(BoardPanel.SEAGREEN);
    BoardPanel.checkers[y][x].setBackground(BoardPanel.SEAGREEN);
    // if(wait) sleeper.doInBackground();
    int [] move = {old_y, old_x, y, x};
    return move;
    
  }

  
  class Sleeper extends SwingWorker<String, Object> {
       @Override
       public String doInBackground() {
         try{
           TimeUnit.SECONDS.sleep(1);
           System.out.println("waited");
         }
          catch (Exception e) {
            System.out.println("failed to wait");
          }
          return "complete";
       }
  }

}


