package chess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class Stockfish {

  private Process engine;
  private BufferedReader processReader;
  private OutputStreamWriter processWriter;
  private final String STARTING_POS = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";


  private String path = "engine/stockfish-7-win/Windows/stockfish 7 x64.exe";

  // Start Stockfish engine
  public boolean startEngine() {
  try {
    engine = Runtime.getRuntime().exec(path);
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
  public boolean sendCommand(String command) {
    try {
      processWriter.write(command + "\n");
      processWriter.flush();
    }
    catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }


  // Get raw output from engine
  public String getOutput() {
    StringBuffer output = new StringBuffer();
    try {
      sendCommand("isready");
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
    sendCommand("position fen " + fen);
    sendCommand("go movetime " + waitTime);
    return getOutput().split("bestmove ")[1].split(" ")[0];
  }

  /**
  * Stops Stockfish and cleans up before closing it
  */
  public void stopEngine() {
    try {
      sendCommand("quit");
      processReader.close();
      processWriter.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}
