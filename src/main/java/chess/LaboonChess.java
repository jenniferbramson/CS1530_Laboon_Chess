package chess;

public class LaboonChess {

  protected static TurnController controller;
  protected static Stockfish stockfish = new Stockfish();


  public static char getTurn() {
    return controller.getTurn();
  }

  public static void changeTurn() {
    controller.changeTurn();
  }

  public static boolean getPlayersTurn() {
    return controller.getPlayersTurn();
  }

  public static void setPlayersTurn(boolean val) {
    controller.setPlayersTurn(val);
  }

  public static void firstStockfishTurn(){
    controller.firstStockfishTurn();
  }

  public static void setDifficultyLevel(DifficultyLevel level) {
    stockfish.setDifficultyLevel(level);
  }

  public static void main(String[] args) {
    stockfish.startEngine();
    stockfish.enableDebugLog();
    stockfish.send("uci");
    StartUpMenu chessBoard = new StartUpMenu();
  }

}
