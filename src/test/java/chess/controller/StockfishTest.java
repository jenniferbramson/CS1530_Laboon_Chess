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
    boolean started = sf.startEngine();
    if (started) sf.stopEngine();
    assertTrue(started);
  }

   @Test
   public void testEngineStop() {
     Stockfish sf = new Stockfish();
     sf.startEngine();
     boolean stopped = sf.stopEngine();
     assertTrue(stopped);
   }

  @Test
  public void testUCIMode(){
    Stockfish sf = new Stockfish();
    sf.startEngine();
    // Tell the engine to switch to UCI mode
    sf.send("uci");
    String output = sf.getOutput();
    // The engine responds with "uciok" when it is ready to communicate in uci mode
    assertTrue(output.contains("uciok"));
    sf.stopEngine();
  }

  /* This test initializes a stockfish board with the starting positions of a new game,
    then tries to pull the fen string from the engine output. The fen should match
    the fen of a new game, with no moves made yet. */
  @Test
  public void testGetFen() {
    Stockfish sf = new Stockfish();
    sf.startEngine();
    // Tell the engine to switch to UCI mode
    sf.send("uci");
    // Tell the engine to draw the board
    sf.send("d");
    String output = sf.getOutput();
    String fen = sf.getFen();
    /* The board was just initialized with the starting postions, so the
       fen string should match the STARTING_POS fen string */
    assertTrue(fen.equals(Stockfish.STARTING_POS));
    sf.stopEngine();
  }

  /* This test tries to make a move on the internal stockfish board, starting from
     the starting positions of a new chess game. It checks the fen that Stockfish
     produces to ensure that it matches the move made
     */
  @Test
  public void testMovePiece(){
    Stockfish sf = new Stockfish();
    sf.startEngine();
    // Tell the engine to switch to UCI mode
    sf.send("uci");

    // Move white pawn from d2 to d4 while the rest of the pieces are in the starting positions
    sf.movePiece("d2d4", sf.STARTING_POS);
    String fen = sf.getFen();
    // Fen should contain string with the 4th white pawn in the middle
    assertTrue(fen.contains("3P4"));
    sf.stopEngine();

  }

  /** This test starts two instances of stockfishk, with "player 1" set to easy and "player 2" to
	expert (the default level). The number of wins for each player is counted. Player 2 should always win.
	*/
	@Test
  public void testDifficultyLevelEasy() {
		int wins1 = 0, wins2 = 0;

		for (int i = 0; i < 3; i++){
			boolean player1Wins = Stockfish.playGame(100, "EASY", "EXPERT");
			if (player1Wins)
				wins1++;
			else
				wins2++;
			}

		System.out.println("Player 1 wins: " + wins1 + "\nPlayer 2 wins: " + wins2);
		assert(wins1<wins2);
	}

  /** This test starts two instances of stockfish, with "player 1" set to expert and "player 2" to
	easy. The number of wins for each player is counted. Player 1 should always win.
	*/
	@Test
  public void testDifficultyLevelExpert() {
		int wins1 = 0, wins2 = 0;
		for (int i = 0; i < 3; i++){
			boolean player1Wins = Stockfish.playGame(100, "EXPERT", "EASY");
			if (player1Wins)
				wins1++;
			else
				wins2++;
			}
		System.out.println("Player 1 wins: " + wins1 + "\nPlayer 2 wins: " + wins2);
		assert(wins1>wins2);

	}


  /** This test starts two instances of stockfish, with "player 1" set to medium and "player 2" to
	expert. The number of wins for each player is counted. Player 1 should always win.
	*/
	@Test
  public void testDifficultyLevelMedium() {
		int wins1 = 0, wins2 = 0;
	  for (int i = 0; i < 3; i++){
			boolean player1Wins = Stockfish.playGame(100, "MEDIUM", "EXPERT");
			if (player1Wins)
				wins1++;
			else
				wins2++;
			}
		System.out.println("Player 1 wins: " + wins1 + "\nPlayer 2 wins: " + wins2);
		assert(wins1<wins2);
	}
}
