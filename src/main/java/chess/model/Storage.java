package chess;
import java.util.*;
public class Storage {
	
	//board
	private char[][] board = new char[8][8];
	//arraylist to keep track of history
	ArrayList<String> history;
	
	
	//constructor
	public Storage(){
		history = new ArrayList<String>();
		//initialize the board
		String firstrow = "brkbqnrn";
		for(int i=0; i<firstrow.length();i++){
			board[0][i] = firstrow.charAt(i);
		}
		for(int i=0; i<firstrow.length(); i++){
			board[1][i] = 'p';
		}
		
		for(int i=0; i<firstrow.length();i++){
			board[6][i] = 'P';
		}
		String lastrow = "BRKBQNRN";
		for(int i=0; i<firstrow.length();i++){
			board[7][i] = lastrow.charAt(i);
		}
	}
		
		public String getSpace(int x, int y){
			if(board[x][y] == '\u0000') return "";
			else return String.valueOf(board[x][y]);
		}

		public void movePiece(int x_1, int y_1, int x_2, int y_2){
			board[x_2][y_2] = board[x_1][y_1];
			board[x_1][y_1] = '\u0000';
		}
		
		//should pass in "moves" and update history
		public void storeHistory(){
		history.add(" ");
	}
}
	