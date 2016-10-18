import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.util.concurrent.TimeUnit;

import chess.Stockfish;

public class StockfishTest {

  @Test
  public void testEngineStart() {
    Stockfish sf = new Stockfish();
    // Get OS type to pass in to startEngine() so that the correct file can be executed
    String os_name = System.getProperty("os.name");
    boolean started = sf.startEngine(os_name);
    if (started) sf.stopEngine();
    assertTrue(started);
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

  @Test
  public void testMovePiece(){
    String os_name = System.getProperty("os.name");
    Stockfish sf = new Stockfish();
    sf.startEngine(os_name);
    // Tell the engine to switch to UCI mode
    sf.send("uci");
    sf.send("ucinewgame");

    // Move white pawn from d2 to d4 while the rest of the pieces are in the starting positions
    sf.movePiece("d2d4", sf.STARTING_POS);
    String fen = sf.getFen();
    // Fen should contain string with the 4th white pawn in the middle
    assertTrue(fen.contains("3P4"));
    sf.stopEngine();
  }

  @Test
  public void testGetLegalMoves(){
    String os_name = System.getProperty("os.name");
    Stockfish sf = new Stockfish();
    sf.startEngine(os_name);
    // Tell the engine to switch to UCI mode
    sf.send("uci");
    sf.send("ucinewgame");
    sf.getLegalMoves("d2d4", sf.STARTING_POS);
    sf.stopEngine();
  }


  //   Not working  on all OS's yet
  /* Enable debug mode, then check to see if log has been modified since the time when
  //   this test started. */
  // @Test
  // public void testDebugMode(){
  //   long startTime = System.currentTimeMillis();
  //   String os_name = System.getProperty("os.name");
  //   Stockfish sf = new Stockfish();
  //   sf.startEngine(os_name);
  //   sf.enableDebugLog();
  //   sf.stopEngine();
  //   File log = new File("io_log.txt");
  //   assertTrue(log.exists());
  //   long modifiedTime = log.lastModified();
  //   assertTrue(modifiedTime > startTime);
  // }
}
