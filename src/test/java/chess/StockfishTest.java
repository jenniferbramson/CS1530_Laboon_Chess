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
}
