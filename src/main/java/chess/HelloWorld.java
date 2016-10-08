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
    Stockfish player1 = new Stockfish();
    player1.startEngine(os_name);
    // Tell the engine to switch to UCI mode
    player1.send("uci");
    String output = player1.getOutput();
    // System.out.println("PLAYER 1 INIT: demoStockfish()" + output);

    String bestMove1 = player1.getBestMove(player1.STARTING_POS, 10000);
    System.out.println("best move calculated by stockfish: " + bestMove1);

    // Send first move
    player1.send("position startpos moves " + bestMove1);
    String fen = player1.getFen();
    System.out.println("Fen string after first move: " + fen);
    player1.drawBoard();

    // Start a second stockfish engine to represent player 2
    Stockfish player2 = new Stockfish();
    player2.startEngine(os_name);
    player2.send("uci");

    output = player2.getOutput();
    // System.out.println("PLAYER 2 INIT: demoStockfish()" + output);
    String bestMove2 = player2.getBestMove(fen, 10000);
    System.out.println("best move calculated by stockfish for player 2: " + bestMove2);

    // It seems that the stockfish board does not preserve any state.
    // It may be necessary to store all moves in some structure
    player2.send("position startpos moves " + bestMove1 + " " + bestMove2);
    player2.drawBoard();
    fen = player2.getFen();
    System.out.println("Fen string after second move: " + fen);


    String bestMove3 = player1.getBestMove(fen, 10000);
    System.out.println("best move calculated by stockfish: " + bestMove3);

    // Send first move
    player1.send("position startpos moves " + bestMove1 + " " + bestMove2 + " "  + bestMove3);
    fen = player1.getFen();
    System.out.println("Fen string after third move: " + fen);
    player1.drawBoard();

    player1.stopEngine();
    player2.stopEngine();

  }

  public static void main(String[] args) {
    speak();
    demoStockfish();
    ConsoleGraphics chessBoard = new ConsoleGraphics();
  }

}
