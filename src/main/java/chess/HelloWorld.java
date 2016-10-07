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
    String os_name = System.getProperty("os.name");
    System.out.println(os_name);
    Stockfish engine = new Stockfish();
    engine.startEngine(os_name);
    // Tell the engine to switch to UCI mode
    engine.send("uci");
    engine.send("ucinewgame");
    engine.send("position startpos");
    engine.send("d");
    String output = engine.getOutput();
    System.out.println("First print in demo" + output);
    engine.send("go");
    String bestMove = engine.getBestMove(engine.STARTING_POS, 1000);
    System.out.println("best move calculated by stockfish: " + bestMove);
    engine.send("position startpos moves " + bestMove);
    String fen = engine.getFen();
    System.out.println("Fen string after first move: " + fen);
    engine.stopEngine();
    System.exit(0);

  }

  public static void main(String[] args) {
    speak();
    BoardGraphics chessBoard = new BoardGraphics();
    demoStockfish();
  }

}
