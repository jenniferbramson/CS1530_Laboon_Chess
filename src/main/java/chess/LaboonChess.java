package chess;

public class LaboonChess {

  protected static TurnController controller;

  public static char getTurn() {
    return controller.getTurn();
  }

  public static void changeTurn() {
    controller.changeTurn();
  }

  public static boolean getPlayersTurn() {
    return controller.getPlayersTurn();
  }

  public static void main(String[] args) {
    StartUpMenu chessBoard = new StartUpMenu();
  }

}
