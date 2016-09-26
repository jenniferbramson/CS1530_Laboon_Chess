package chess;

public class Board {

  private char[][] board;

  public Board() {
    board = new char[8][8];
  }

  public void init() {
    // Add pawns
    for(int i = 0; i < 8; i++) {
      board[0][i] = 'p';
      board[7][i] = 'p';
    }
  }

  // Only pawns are filled out, so any other space than those two rows should
  // return a 'z', which wouldn't be on a chess board
  public char getSquare(int i, int j) {
    if (i < 8 && i > -1 && j < 8 && j > -1) {
      return board[i][j];
    } else {
      return 'z';
    }
  }

}
