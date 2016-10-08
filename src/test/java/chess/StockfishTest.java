import org.junit.Test;
import static org.junit.Assert.*;

import chess.Stockfish;

public class StockfishTest {

  @Test
  public void testEngineStart() {
    Stockfish sf = new Stockfish();
    // Get OS type to pass in to startEngine() so that the correct file can be executed
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
    // The engine responds with "uciok" when it is ready to communicate in uci mode
    assertTrue(output.contains("uciok"));
    sf.stopEngine();
  }

  @Test
  public void testGetFen() {
    String os_name = System.getProperty("os.name");
    Stockfish sf = new Stockfish();
    sf.startEngine(os_name);
    // Tell the engine to switch to UCI mode
    sf.send("uci");
    sf.send("ucinewgame");
    // Tell the engine to draw the board
    sf.send("d");
    String output = sf.getOutput();
    String fen = sf.getFen();
    /* The board was just initialized with the starting postions, so the
       fen string should match the STARTING_POS fen string */
    assertTrue(fen.equals(Stockfish.STARTING_POS));
    sf.stopEngine();
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
