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
    //Storage temp = new Storage("rnbqkb1r/ppp2ppp/4pn2/3p4/3P4/2N2N2/PPP1PPPP/R1BQKB1R b KQkq - 2 4");
    //System.out.println(temp.getSpace(0, 1));
  }

}
