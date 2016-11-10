package chess;
import java.util.*;
import chess.Stockfish;

public class Storage {

	//board
	private char[][] board = new char[8][8];
	private char[] white_taken = new char[16];
	private char[] black_taken = new char[16];
	private boolean white_turn = true;
	int full_moves = 0;
	//arraylist to keep track of history
	ArrayList<String> history;
  private String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

	//constructor
	public Storage(){
		history = new ArrayList<String>();
		//initialize the board
		String firstrow = "rnbqkbnr";
		for(int i=0; i<firstrow.length();i++){
			board[0][i] = firstrow.charAt(i);
		}
		for(int i=0; i<firstrow.length(); i++){
			board[1][i] = 'p';
		}

		for(int i=0; i<firstrow.length();i++){
			board[6][i] = 'P';
		}
		String lastrow = "RNBQKBNR";
		for(int i=0; i<firstrow.length();i++){
			board[7][i] = lastrow.charAt(i);
		}
		white_turn = true;		//white goes first
	}

	//loads the board from a fen string
	public Storage(String fen){
    this.fen=fen;
		String[] tokens = fen.split("/|\\ ");	//using regex OR operator to pass multiple delims "/" or " "
		//load the entire board
		for(int i=0; i<8; i++){	//load one row at a time
			String row = tokens[i];
			int index = 0;
			int skip = 0;
			for(int j=0; j<row.length();j++){
				if (index > 7) break;
				if(Character.isDigit(row.charAt(j))){		//if its a number, we need to fill in some spaces
					index += Integer.parseInt(Character.toString(row.charAt(j))) -1;
				}
				else{
					//set the spot to be that character
					board[i][index] = row.charAt(j);
				}
				index++;
			}//end of loop for row
		}//end of loop to load board

		//set the turn
		if(tokens[8].equals("w")){
			white_turn = true;
		}
		else if(tokens[8].equals("b")){
			white_turn = false;
		}
		else{
			System.out.println("error: not white or black's turn");
		}
	}

  public void loadBoard(String fen){
    this.fen=fen;
    String[] tokens = fen.split("/|\\ ");	//using regex OR operator to pass multiple delims "/" or " "
    //load the entire board
    for(int i=0; i<8; i++){	//load one row at a time
      String row = tokens[i];
      int index = 0;
      int skip = 0;
      for(int j=0; j<row.length();j++){
        if (index > 7) break;
        if(Character.isDigit(row.charAt(j))){		//if its a number, we need to fill in some spaces
          //index += (int)Character.getNumericValue(j) ;				//<- this won't work for some reason
          index += Integer.parseInt(Character.toString(row.charAt(j))) -1;
        }
        else{
          //set the spot to be that character
          board[i][index] = row.charAt(j);
        }
        index++;
      }//end of loop for row
    }//end of loop to load board

    //set the turn
    if(tokens[8].equals("w")){
      white_turn = true;
    }
    else if(tokens[8].equals("b")){
      white_turn = false;
    }
    else{
      System.out.println("error: not white or black's turn");
    }

  }

	// Returns the number of rows (aka the length of the x-coordinate in the
	// board array)
	public int getNumRows() {
		return board.length;
	}

	// Returns the number of columns (aka the length of the y-coordinate in the
	// board array)
	public int getNumCols() {
		return board[0].length;
	}

  public void setFen(String fen) {
    this.fen = fen;
  }

  public String getFen() {
    return fen;
  }

	//returns whether it is white's turn or not
	public boolean isWhiteTurn(){
		return white_turn;
	}

	public void deletePiece(int x, int y){
		board[x][y] = '\u0000';
	}

	public String getSpace(int x, int y){
		if(board[x][y] == '\u0000') return "";
		else return String.valueOf(board[x][y]);
	}

	public char getSpaceChar(int y, int x){
		return board[x][y];
	}

	public char getSpace(int x, int y, boolean filler){
		return board[x][y];
	}

	//Set the intended spot on the board with a different piece
	public void setSpace(int x, int y, char piece) {
		board[x][y] = piece;
	}


	// Assumes you are attempting a valid move, i.e. by using Rulebook to check
	// that the move was valid
	public void movePiece(int y_1, int x_1, int y_2, int x_2){
		// Move castle if castling, en passant if en passant, otherwise normally
		char piece = this.getSpaceChar(x_1, y_1);
		char space = this.getSpaceChar(x_2, y_2);
		if ((piece == 'k' || piece == 'K') && Math.abs(x_1 - x_2) == 2) {
		  // Castling
		  board[y_2][x_2] = board[y_1][x_1];	// set new space to old piece
			board[y_1][x_1] = '\u0000';					// set old space to null

		  if (x_1 - x_2 == 2) {
		    // left, move appropriate rook
		    board[y_2][3] = board[y_1][0];	// set new space to old piece
		    board[y_1][0] = '\u0000';				// set old space to null
		    System.out.println("Old spot: " + 0 + " " + y_1);
		    System.out.println("New spot: " + 3 + " " + y_1);
		  } else {
		    // right, move appropriate rook
		    board[y_2][5] = board[y_1][7];	// set new space to old piece
		    board[y_1][7] = '\u0000';				// set old space to null
		    System.out.println("Old spot: " + 7 + " " + y_1);
		    System.out.println("New spot: " + 5 + " " + y_1);
		  }
		} else if ((piece == 'p' || piece == 'P') && space == '\u0000' && Math.abs(x_1 - x_2) == 1) {
			// En passant
			board[y_2][x_2] = board[y_1][x_1];	// set new space to old piece
			board[y_1][x_1] = '\u0000';					// set old space to null
			board[y_1][x_2] = '\u0000';					// set taken pawn space to null
		} else {
			// Not castling or en passant, move normally
			board[y_2][x_2] = board[y_1][x_1];	// set new space to old piece
			board[y_1][x_1] = '\u0000';	// set old space to null
		}
	}

	//should pass in "moves" and update history
	public void storeHistory(){
		//TO-DO Implement later
		history.add(" ");
	}
}
