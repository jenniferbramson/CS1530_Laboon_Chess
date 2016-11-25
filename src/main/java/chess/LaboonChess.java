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

  public static void main(String[] args) {
    stockfish.startEngine();
    stockfish.enableDebugLog();
    stockfish.setDifficultyLevel(DifficultyLevel.EASY);
    StartUpMenu chessBoard = new StartUpMenu();


    //-------
    //
    // Stockfish sf = new Stockfish();
    // sf.startEngine();
    // sf.enableDebugLog();
    // // System.out.println(sf.getOutput());
    // sf.setDifficultyLevel(DifficultyLevel.EASY);
    // sf.stopEngine();

  }

}
