package chess;

public class HelloWorld {

  public static void speak() {
      System.out.println("Hello World!");
  }

  public static int five() {
      return 5;
  }

  public static char chess() {
    Board b = new Board();
    b.init();

    return b.getSquare(0, 1);
  }

  public static void demoStockfish(){
    Stockfish engine = new Stockfish();
    engine.startEngine("");
    engine.send("uci");
    engine.send("ucinewgame");
    engine.send("position startpos");
    engine.send("go");
    String bestMove = engine.getBestMove(engine.STARTING_POS, 1000);
    System.out.println(bestMove);
    engine.send("position startpos moves " + bestMove);
    // System.out.println(engine.getOutput());
    engine.send("d");
    System.out.println(engine.getOutput());
    engine.stopEngine();
    System.exit(0);

  }

  public static void main(String[] args) {
    speak();
    BoardGraphics chessBoard = new BoardGraphics();
    demoStockfish();
  }

}
