import org.junit.Test;
import static org.junit.Assert.*;

import chess.Stockfish;

public class StockfishTest {


  // public static void main (String [] args) {
  //   Stockfish sf = new Stockfish();
  //   if (sf.startEngine())
  //     System.out.println("started engine");
  //   else
  //     System.out.println("Engine didn't start");
  //
  //   String STARTING_POS = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
  //   sf.sendCommand("uci");
  //   sf.sendCommand("ucinewgame");
  //   sf.sendCommand("position startpos");
  //   sf.sendCommand("go");
  //   String bestMove = sf.getBestMove(STARTING_POS, 1000);
  //   System.out.println(bestMove);
  //   sf.sendCommand("position startpos moves " + bestMove);
  // //  System.out.println(sf.getOutput());
  //   sf.sendCommand("d");
  //   System.out.println(sf.getOutput());
  //   sf.stopEngine();
  //
  //
  //   System.exit(0);
  // }


  @Test
  public void testEngineStart() {
    Stockfish sf = new Stockfish();
    boolean started = sf.startEngine();
    assertTrue(started);
    sf.stopEngine();
  }


}
