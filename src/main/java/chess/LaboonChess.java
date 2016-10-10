package chess;

public class LaboonChess {

  public static void main(String[] args) {
    StartUpMenu chessBoard = new StartUpMenu();
    Stockfish.playGame(10);
  }

}
