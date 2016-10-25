package chess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.*;

import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.concurrent.TimeUnit;




/* This class will use the Universal Chess Interface (UCI) to communicate
 * with the Stockfish engine.
 *
 * Commands that may be useful:
 * debug [on | off]
 * isready - returns "readyok"
 * position [fen  | startpos ]  moves [moves]
 * d - draws the board and includes a fen string
 */

public class Stockfish {

  private Process engine;
  private BufferedReader processReader;
  private BufferedWriter processWriter;
  private InputStream inputStream;
  private InputStreamReader inputStreamReader;
  private ProcessBuilder builder;

  /* This is the FEN notation for the starting positions on a chessboard.
   * White pieces are upper-case letters. Black are lower case.
   * w = white's turn.
   * KQkq - = castling availability
   * - = "en passant target square doesn't exist"
   * The second to last number is the "halfmove clock - the number of halfmoves since the last capture or pawn advance
   * Full move number: Starts at one and increments when black moves
   */
  public static final String STARTING_POS = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
  public String fen = STARTING_POS;


  // Start Stockfish engine
  public boolean startEngine() {
    // First get name of operating system to file appropriate executable file
    String os_name = System.getProperty("os.name");
  	String path = "";
  	String pathBase = System.getProperty("user.dir");
		//System.out.println(os_name);
    if (os_name.toLowerCase().contains("windows"))
      path = "engine/stockfish-7-win/Windows/stockfish 7 x64.exe";
    else if (os_name.toLowerCase().contains("mac"))
      path = pathBase + "/engine/stockfish-7-mac/Mac/stockfish-7-64";
    else if (os_name.toLowerCase().contains("linux"))
			path = pathBase + "/engine/stockfish-7-linux/Linux/stockfish 7 x64";
    else {
			System.out.println(os_name + " is not currently supported by Laboon Chess. Please try again on a Windows, Mac, or Linux system");
			System.exit(1);
		}


    try {

      // path to executable stockfish file
      Path file = Paths.get(path);
      builder = new ProcessBuilder(path);

      // Send standard error to same stream as standard out
      builder.redirectErrorStream(true);
      engine = builder.start();
      // System.out.println( "Process is running" + engine.isAlive());

      // Open streams to read from and write to engine
      inputStream = engine.getInputStream();
      inputStreamReader = new InputStreamReader(inputStream);
      processReader = new BufferedReader(inputStreamReader);
      processWriter = new BufferedWriter(new OutputStreamWriter(engine.getOutputStream()));
      System.out.println("Stockfish engine started");

      // Tell stockfish process to start communicating in UCI mode
      this.send("ucinewgame");

    }

    catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  return true;
  }

  // Send UCI command to Stockfish engine
  public boolean send(String command) {
    try {
      // UCI commands must end with newline
      processWriter.write(command + "\n");
      processWriter.flush();

    }
    catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /* Tell Stockfish process to write debugging output to file. This will create
    a file named io_log.txt in the project home folder. */
  public boolean enableDebugLog(){
    return this.send("setoption name Write Debug Log value true");
  }

  public String getFen(){
    // "d" is the command to draw board
    this.send("d");
    String output = this.getOutput();
    // fen string is on its own line of the output, starting with "Fen: "
    int fenStart = output.indexOf("Fen:");
    int fenEnd = output.indexOf("\n", fenStart);
    String fen = output.substring(fenStart + 5, fenEnd);
    return fen;
  }

  public boolean isReady(){
    this.send("isready");
    String output = getOutput();
    if (output.contains("readyok"))
      return true;
    return false;
  }

  public void drawBoard() {
    // tell stockfish to draw the current board
    this.send("d");
    String output = this.getOutput();
    // Board  starts with +---+---+---+---+---+---+---+---+
    // Fen string always follows board
    int start = output.indexOf("+---+---+---+---+---+---+---+---+");
    int end = output.indexOf("Fen: ") - 1;
    String board = output.substring(start, end);
    System.out.println(board);
  }



  // Get all output from engine
  public String getOutput() {
    StringBuffer output = new StringBuffer();
    try {
      this.send("isready" + "\n");
      String text = "";
      while (!text.equals("readyok")){
        text = processReader.readLine();
        output.append(text + "\n");
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return output.toString();
  }

  /**
  * This function returns the best move for a given position after
  * calculating for 'waitTime' ms
  *
  * fen string
  * waitTime in milliseconds
  */
  public String getBestMove(String fen, int waitTime) {

    this.send("position fen " + fen);
    this.send("go");
    this.send("go movetime " + waitTime);
    String output = this.getOutput();
    // System.out.println("output from Stockfish.getBestMove():" + output);
    int index = output.lastIndexOf("bestmove");
    if (index == -1) return null; // best move not found
    // bestmove string starts after last occurrence of "bestmove " and has length 4
    String bestMove = output.substring(index + 9, index + 13);
    return bestMove;
  }


  // move string needs to be in algebraic notation for chess
  public boolean movePiece(String move, String fen){
    this.send("position fen " + fen + " moves " + move);
    // check to see if valid - not sure how yet
    return true; // if valid move?

  }

  /**
  * Stops Stockfish and cleans up before closing it
  */
  public boolean stopEngine() {
    try {
      this.send("quit");
      inputStream.close();
      inputStreamReader.close();
      processReader.close();
      processWriter.close();
      // Wait for process to completely exit
      this.engine.waitFor();
      System.out.println("Stockfish engine stopped");
      return true;
    }
    catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  // Have stockfish play against itself for specified number of rounds
  // Just for testing to see how engine acts over many turns
  public static void playGame(int rounds) {
    Stockfish player1 = new Stockfish();
    player1.startEngine();
    // Tell the engine to switch to UCI mode
    player1.send("uci");
    // player1.turnOnDebug();

    Stockfish player2 = player1;

    String fen = player1.STARTING_POS;
    StringBuilder allMoves = new StringBuilder();

    for (int i = 0; i < rounds; i++){

      System.out.println("ROUND " + (i/2+1));

      player1.send("position fen " + fen);
      player1.send("d");
      String output = player1.getOutput();
      System.out.println("Output" + output);

      String bestMove = player1.getBestMove(fen, 100);
      player1.movePiece(bestMove, fen);
      fen = player1.getFen();
      System.out.println("Fen string after move " + (i+1) + ": " + bestMove + " --- "  + fen);
      player1.drawBoard();

      bestMove = player2.getBestMove(fen, 100);
      player2.movePiece(bestMove, fen);
      fen = player2.getFen();
      i++;
      System.out.println("Fen string after move " + (i+1) + ": " + bestMove + " --- " + fen);
      player2.drawBoard();

    }
    player1.stopEngine();



  }


}
