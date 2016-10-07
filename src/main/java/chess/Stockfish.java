package chess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/* This class will use the Universal Chess Interface (UCI) to communicate
 * with the Stockfish engine.
 *
 * Commands that may be useful:
 * debug [on | off]
 * isready - returns "readyok"
 * position [fen  | startpos ]  moves
 * d - draws the board and includes a fen string
 */

public class Stockfish {

  private Process engine;
  private BufferedReader processReader;
  private OutputStreamWriter processWriter;

  /* This is the FEN notation for the starting positions on a chessboard.
   * White pieces are upper-case letters. Black are lower case.
   * w = white's turn.
   * KQkq - = castling availability
   * - = "en passant target square doesn't exist"  TODO: look up what the heck en passant means
   * The second to last number is the "halfmove clock - the number of halfmoves since the last capture or pawn advance
   * Full move number: Starts at one and increments when black moves
   */
  public static final String STARTING_POS = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
  public String fen = STARTING_POS;


  // Start Stockfish engine
  public boolean startEngine(String os_name) {
    String path = "";
    if (os_name.toLowerCase().contains("windows"))
      path = "engine/stockfish-7-win/Windows/stockfish 7 x64.exe";
    else if (os_name.toLowerCase().contains("mac"))
      // path to binary. Will be compiled from Make file included in Stockfish-7-mac.zip
      path = "";

    try {
      engine = Runtime.getRuntime().exec(path);
      // Open streams to read from and write to engine
      processReader = new BufferedReader(new InputStreamReader(engine.getInputStream()));
      processWriter = new OutputStreamWriter(engine.getOutputStream());
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

  public String getFen(){
    // "d" is the command to draw board
    // fen string is on its own line of the output, starting with "Fen: "
    send("d");
    String output = getOutput();
    int fenStart = output.indexOf("Fen:");
    int fenEnd = output.indexOf("\n", fenStart);
    String fen = output.substring(fenStart + 5, fenEnd);
    return fen;
  }

  public boolean isReady(){
    send("isready");
    String output = getOutput();
    if (output.contains("readyok"))
      return true;
    return false;
  }

  // move string needs to be in algebraic notation for chess
  // NOT WORKING
  public boolean movePiece(String move, String fen){
    // System.out.println(" Will send command: position " + fen + " moves " + move);
    send("position " + fen + " moves " + move);
    // System.out.println(getOutput());
    return true; // if valid move?

  }


  // Get raw output from engine
  public String getOutput() {
    StringBuffer output = new StringBuffer();
    try {
      send("isready");
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
  * @param fen
  *            Position string
  * @param waitTime
  *            in milliseconds
  * @return Best Move in PGN format
  */
  public String getBestMove(String fen, int waitTime) {
    send("position fen " + fen);
    send("go movetime " + waitTime);
    return getOutput().split("bestmove ")[1].split(" ")[0];
  }


  // Not working yet
  public String getLegalMoves(String fen) {
     send("position fen " + fen);
     send("d");
     String output = getOutput();
     System.out.println("Output" + output);
     return getOutput().split("Legal moves: ")[1];
  }

  // Not working
  public boolean isLegalMove(String fen, String move){
    String legalMoves = getLegalMoves(fen);
    return true;
  }

  /**
  * Stops Stockfish and cleans up before closing it
  */
  public boolean stopEngine() {
    try {
      send("quit");
      processReader.close();
      processWriter.close();
      return true;
    }
    catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }
}
