import org.junit.Test;
import static org.junit.Assert.*;

import chess.Stockfish;

public class StockfishTest {


  @Test
  public void testEngineStart() {
    Stockfish sf = new Stockfish();
    String os_name = System.getProperty("os.name");
    boolean started = sf.startEngine(os_name);
    assertTrue(started);
    sf.stopEngine();
  }

  @Test
  public void testEngineStop() {
    Stockfish sf = new Stockfish();
    String os_name = System.getProperty("os.name");
    sf.startEngine(os_name);
    boolean stopped = sf.stopEngine();
    assertTrue(stopped);
  }

  @Test
  public void testUCIMode(){
    String os_name = System.getProperty("os.name");
    Stockfish sf = new Stockfish();
    sf.startEngine(os_name);
    // Tell the engine to switch to UCI mode
    sf.send("uci");
    String output = sf.getOutput();
    assertTrue(output.contains("uciok"));
    sf.stopEngine();
  }

  @Test
  public void testFenExtraction() {
    String os_name = System.getProperty("os.name");
    Stockfish sf = new Stockfish();
    sf.startEngine(os_name);
    // Tell the engine to switch to UCI mode
    sf.send("uci");
    sf.send("ucinewgame");
    sf.send("position startpos");
    sf.send("d");
    String output = sf.getOutput();
    String fen = sf.getFen();
    System.out.println("Starting fen from testFenExtraction: " + fen);
    assertTrue(fen.equals(Stockfish.STARTING_POS));
  }

  // @Test
  // public void testMovePiece(){
  //   String os_name = System.getProperty("os.name");
  //   Stockfish sf = new Stockfish();
  //   sf.startEngine(os_name);
  //   // Tell the engine to switch to UCI mode
  //   sf.send("uci");
  //   sf.send("ucinewgame");
  //   boolean ready = sf.isReady();
  //   sf.send("position startpos");
  //   System.out.println("ready: " + ready);
  //   sf.send("go");
  //   // Move pawn from d2 to d4 where the rest of the pieces are in the starting position
  //   sf.movePiece("d2d4", sf.STARTING_POS);
  //   String fen = sf.getFen();
  //   System.out.println("feb after move: " + fen);
  //   // Output should contain fen string with one pawn in the middle
  //   // assertTrue(output.contains("3p4"));
  //   sf.stopEngine();
  // }
}
