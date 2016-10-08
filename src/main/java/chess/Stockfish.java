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



  // Get raw output from engine
  public String getOutput() {
    StringBuffer output = new StringBuffer();
    try {
      this.send("isready");
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
  * fen Position string
  * waitTime in milliseconds
  * @return Best Move in PGN format
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

  /* In the Stockfish documentation, it looks like you should be able to move a piece
     starting from any point in the game as long as the fen string to represent the
     board state is correct.

     I can't get that to work--it doesn't move the piece on its internal board when I try.
     It you use "startpos" instead of the fen and pass in all moves that have been played,
     it works.
   */
  // move string needs to be in algebraic notation for chess
  public boolean movePiece(String allMoves, String fen){
    this.send("position startpos " + " moves " + allMoves);

    // check to see if valid - not sure how yet
    return true; // if valid move?

  }

  // Not working yet
  public String getLegalMoves(String fen) {
     this.send("position fen " + fen);
     this.send("d");
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
      this.send("quit");
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
