package chess;
import static java.lang.Math.abs;
import java.util.*;

public class Rulebook {

  private Storage my_storage;

  public Rulebook(Storage my_storage) {
    this.my_storage = my_storage;
  }

  // Checks if moves are legal. It does not check the case of no movement since
  // the board checks that
  // Returns true if the move is legal, false if not
  public boolean checkMove(int y_1, int x_1, int y_2, int x_2) {
    char piece = my_storage.getSpaceChar(x_1, y_1);
    char space = my_storage.getSpaceChar(x_2, y_2);
    String fen = my_storage.getFen();
    String[] fenParts;
    String enPass;
    int a = (int)'a';
    String move;
    boolean valid = false; // For checking valid moves for king check
    TestBoard testBoard;

    // Can only move to a space that is empty or has the other player's piece on it
    if (Character.isLowerCase(piece)) {
      if (!Character.isUpperCase(space) && space != '\u0000') {
        return false;
      }
    } else if (Character.isUpperCase(piece)) {
      if (!Character.isLowerCase(space) && space != '\u0000') {
        return false;
      }
    }

    // Black is lowercase
    switch (piece) {
      case 'p':
        fenParts = fen.split(" ");
        enPass = fenParts[3];
        move = ("" + (char)(x_2+a)) + (8-y_2);
        // On first move, pawn can move two spaces
        // Otherwise can only move one place - north if space above is empty,
        // diagonally if taking a piece
        if (x_1 == x_2 && y_1 == 1 && y_2 == 3 && space == '\u0000') {
          if (my_storage.getSpaceChar(x_1, 2) == '\u0000') {
            // First move down 2 ok, nothing in the way
            valid = true;
          }
        } else if (x_1 == x_2 && (y_1 + 1) == y_2 && space == '\u0000') {
          // Move north ok
          valid = true;
        } else if (((x_1 + 1 == x_2) || (x_1 - 1 == x_2)) && (y_1 + 1) == y_2 &&
                  Character.isUpperCase(space)) {
          // Take piece diagonally ok
          valid = true;
        } else if (move.equals(enPass)) {
          // En passant ok
          valid = true;
        }
        break;
      case 'P':
        fenParts = fen.split(" ");
        enPass = fenParts[3];
        move = ("" + (char)(x_2+a)) + (8-y_2);
        // On first move, pawn can move two spaces
        // Otherwise can only move one place - north if space above is empty,
        // diagonally if taking a piece
        if (x_1 == x_2 && y_1 == 6 && y_2 == 4 && space == '\u0000') {
          if (my_storage.getSpaceChar(x_1, 5) == '\u0000') {
            // First move down 2 ok, nothing in the way
            valid = true;
          }
        } else if (x_1 == x_2 && (y_1 - 1) == y_2 && space == '\u0000') {
          // Move north ok
          valid = true;
        } else if (((x_1 + 1 == x_2) || (x_1 - 1 == x_2)) && (y_1 - 1) == y_2 &&
                  Character.isLowerCase(space)) {
          // Take piece diagonally ok
          valid = true;
        } else if (move.equals(enPass)) {
          // En passant ok
          valid = true;
        }
        break;
      case 'n':
      case 'N':
        // Knights move in an 'L' shape whether or not opponent piece is there
        // Ignore moves where own side's piece is there though
        if (((x_1 + 2 == x_2) || (x_1 - 2 == x_2)) && ((y_1 + 1 == y_2) || (y_1 - 1 == y_2))) {
          valid = true;
        } else if (((y_1 + 2 == y_2) || (y_1 - 2 == y_2)) && ((x_1 + 1 == x_2) || (x_1 - 1 == x_2))) {
          valid = true;
        }
        break;
      case 'b':
      case 'B':
        // Bishop can move along the diagonals
        if (abs(x_1 - x_2) == abs(y_1 - y_2)) {
          // space or upper case, diagonal
          valid = bishopMove(x_1, y_1, x_2, y_2);
        }
        break;
      case 'k':
      case 'K':
        // Check to see if attempting castling, otherwise do regular check
        if (x_1 - x_2 == 2 && y_1 - y_2 == 0) {
          // Queenside
          return queensideCastle(x_1, y_1, piece);
        } else if (x_1 - x_2 == -2 && y_1 - y_2 == 0) {
          // Kingside
          return kingsideCastle(x_1, y_1, piece);
        } else {
          // Check for regular move because doesn't look like castling
          // Can only move one square in any direction
          int xDiff = abs((x_1 - x_2));
          int yDiff = abs((y_1 - y_2));
          if ((xDiff == 1 && yDiff == 1) || (xDiff == 1 && yDiff == 0) || (xDiff == 0 && yDiff == 1)) {
            valid = true;
          }
        }
        break;
      case 'q':
      case 'Q':
        // Queen is rook plus diagonal ability
        if (abs(x_1 - x_2) != abs(y_1 - y_2) && y_1 != y_2 && x_1 != x_2) {
          return false;	// Not moving in a legal diagonal, horizontal, or vertical direction
        }

        if (abs(x_1 - x_2) == abs(y_1 - y_2)) {
          // Diagonal
          valid = bishopMove(x_1, y_1, x_2, y_2);
        } else {
          valid = rookMove(x_1, y_1, x_2, y_2);
        }

        break;
      case 'r':
      case 'R':
        valid = rookMove(x_1, y_1, x_2, y_2);
        break;
    } // end switch statement

    // Move is valid, make sure it doesn't cause the player's own king to be in check
    if (valid) {
      testBoard = new TestBoard(my_storage, x_1, y_1, x_2, y_2);
      if (Character.isUpperCase(piece)) {
        // Return true if the king is not in danger with the new move
        return !kingDanger(testBoard.getWhiteKingX(), testBoard.getWhiteKingY(), 'K', testBoard);
      } else {
        return !kingDanger(testBoard.getBlackKingX(), testBoard.getBlackKingY(), 'k', testBoard);
      }
    } else {
      return false; // If reached this point, false
    }

  } // end checkMove ----------------------------------------------------------

  // Helper function for checkMove, checks for Queenside castling
  // Assumes user is trying to queenside castle, returns true if they can
  private boolean queensideCastle(int x, int y, char piece) {
    boolean danger;
    char space;
    String fen = my_storage.getFen();
    String[] fenParts = fen.split(" ");
    String castleList = fenParts[2];
    boolean whiteCanCastle = false;
    boolean blackCanCastle = false;
    TestBoard testBoard;

    for (int i = 0; i < castleList.length(); i++) {
      char c = castleList.charAt(i);
      if (c == 'Q') {
        whiteCanCastle = true;
      } else if (c == 'q') {
        blackCanCastle = true;
      }
      // Otherwise either kingside or '-'
      // If '-', means no castling allowed and this method only checks for queenside
    }

    // Queenside
    if ((piece == 'K' && whiteCanCastle) || (piece == 'k' && blackCanCastle)) {
      for (int i = 1; i < 3; i++) { // Checks to the left two spaces
        space = my_storage.getSpaceChar(x - i, y);
        if (space == '\u0000') {
          testBoard = new TestBoard(my_storage, x, y, (x-i), y);
          danger = kingDanger(x - i, y, piece, testBoard);
          if (danger) {
            return false;
          }
        } else {
          return false;
        }
      }

      space = my_storage.getSpaceChar(x - 3, y); // Make sure no piece in the way
                                                 // (where knight normally is)
      if (space != '\u0000') {
        return false;
      }

      return true;
    } else {
      // Already moved king or rook, so can't castle
      return false;
    }
  } // end queensideCastle

  // Helper function for checkMove, checks for Kingside castling
  // Assumes user is trying to kingside castle, returns true if they can
  private boolean kingsideCastle(int x, int y, char piece) {
    boolean danger;
    char space;
    String fen = my_storage.getFen();
    String[] fenParts = fen.split(" ");
    String castleList = fenParts[2];
    boolean whiteCanCastle = false;
    boolean blackCanCastle = false;
    TestBoard testBoard;

    for (int i = 0; i < castleList.length(); i++) {
      char c = castleList.charAt(i);
      if (c == 'K') {
        whiteCanCastle = true;
      } else if (c == 'k') {
        blackCanCastle = true;
      }
      // Otherwise either queenside or '-'
      // If '-', means no castling allowed and this method only checks for kingside
    }

    // Kingside
    if ((piece == 'K' && whiteCanCastle) || (piece == 'k' && blackCanCastle)) {
      for (int i = 1; i < 3; i++) { // Checks to the right 2 spaces
        space = my_storage.getSpaceChar(x + i, y);
        if (space == '\u0000') {
          testBoard = new TestBoard(my_storage, x, y, (x + i), y);
          danger = kingDanger(x + i, y, piece, testBoard);
          if (danger) {
            return false;
          }
        } else {
          return false;
        }
      }

      return true;
    } else {
      // Already moved king or rook, so can't castle
      return false;
    }
  }

  // Method that checks if an input King at x, y of c color is or would
  // be checked. Color is indicated by the original king piece's letter case.
  // Returns false if not checked, true if checked
  // There does not actually need to be a king at x, y
  // Assumes valid x, y, c input
  protected boolean kingDanger(int x, int y, char c, TestBoard testBoard) {
    boolean kingWhite = Character.isUpperCase(c);
    boolean danger;
    char dangerC;
    int numRows = 8;
    int numCols = 8;

    // Rook and Queen checks ---------------------------------
    // East
    for(int i = x + 1; i < numCols; i++) {
      dangerC = rookQueenCheck(i, y, kingWhite, testBoard);
      if (dangerC == 'a') {
        return true;
      } else if (dangerC == 's') {
        break; // Break out of loop if the king is safe east
      }
    } // end east check

    // West
    for(int i = x - 1; i > -1; i--) {
      dangerC = rookQueenCheck(i, y, kingWhite, testBoard);
      if (dangerC == 'a') {
        return true;
      } else if (dangerC == 's') {
        break; // Break out of loop if the king is safe east
      }
    } // end east check

    // South
    for(int i = y + 1; i < numRows; i++) {
      dangerC = rookQueenCheck(x, i, kingWhite, testBoard);
      if (dangerC == 'a') {
        return true;
      } else if (dangerC == 's') {
        break; // Break out of loop if the king is safe east
      }
    } // end south check

    // North
    for(int i = y - 1; i > -1; i--) {
      dangerC = rookQueenCheck(x, i, kingWhite, testBoard);
      if (dangerC == 'a') {
        return true;
      } else if (dangerC == 's') {
        break; // Break out of loop if the king is safe east
      }
    } // end north check
    //------------------------------------------------------

    // Check for kings
    danger = kingChecks(x, y, kingWhite, testBoard);
    if (danger) {
      return true;
    }

    // check diagonals
    // Check for pawns
    danger = pawnCheck(x, y, kingWhite, testBoard);
    if (danger) {
      return true;
    }

    // Bishop and Queen checks -------------------------------
    // Northeast
    for(int i = 1; i < numRows; i++) {
      if ( (x + i) < numCols && (y + i) < numRows) {
        dangerC = bishopQueenCheck(x + i, y + i, kingWhite, testBoard);
        if (dangerC == 'a') {
          return true;
        } else if (dangerC == 's') {
          break; // Break out of loop if the king is safe east
        }
      } else {
        break; // Out of bounds
      }
    }

    // Northwest
    for(int i = 1; i < numRows; i++) {
      if ( (x - i) > -1 && (y + i) < numCols) {
        dangerC = bishopQueenCheck(x - i, y + i, kingWhite, testBoard);
        if (dangerC == 'a') {
          return true;
        } else if (dangerC == 's') {
          break; // Break out of loop if the king is safe east
        }
      } else {
        break; // Out of bounds
      }
    }

    // Southeast
    for(int i = 1; i < numCols; i++) {
      if ( (x + i) < numCols && (y - i) > -1) {
        dangerC = bishopQueenCheck(x + i, y - i, kingWhite, testBoard);
        if (dangerC == 'a') {
          return true;
        } else if (dangerC == 's') {
          break; // Break out of loop if the king is safe east
        }
      } else {
        break; // Out of bounds
      }
    }

    // Southwest
    for(int i = 1; i < numRows; i++) {
      if ( (x - i) > -1 && (y - i) > -1) {
        dangerC = bishopQueenCheck(x - i, y - i, kingWhite, testBoard);
        if (dangerC == 'a') {
          return true;
        } else if (dangerC == 's') {
          break; // Break out of loop if the king is safe east
        }
      } else {
        break; // Out of bounds
      }
    }
    //----------------------------------------------------------

    // check for knights
    danger = knightChecks(x, y, kingWhite, testBoard);
    if (danger) {
      return true;
    }

    return false; // No danger found, spot is safe
  } // end KingDanger() ------------------------------------------------------

  // Helper method for kingDanger()
  // True if there is a valid attacking knight for the space, false if not
  private boolean knightChecks(int x, int y, boolean kingWhite, TestBoard test) {
    char[] piecesThere = new char[8];
    char pieceThere;
    int numCols = 8;
    int numRows = 8;

    // 8 possible attacking king spots
    if (x - 2 > -1 && y - 1 > -1)
      piecesThere[0] = test.getSpaceChar(x - 2, y - 1);

    if (x + 2 < numCols && y - 1 > -1)
      piecesThere[1] = test.getSpaceChar(x + 2, y - 1);

    if (x - 2 > - 1 && y + 1 < numRows)
      piecesThere[2] = test.getSpaceChar(x - 2, y + 1);

    if (x + 2 < numCols && y + 1 < numRows)
      piecesThere[3] = test.getSpaceChar(x + 2, y + 1);

    if (x -1 > -1 && y - 2 > -1)
      piecesThere[4] = test.getSpaceChar(x - 1, y - 2);

    if (x +1 < numCols && y - 2 > -1)
      piecesThere[5] = test.getSpaceChar(x + 1, y - 2);

    if (x -1 > -1 && y + 2 < numRows)
      piecesThere[6] = test.getSpaceChar(x - 1, y + 2);

    if (x +1 < numCols && y + 2 < numRows)
      piecesThere[7] = test.getSpaceChar(x + 1, y + 2);

    for(int i = 0; i < 8; i++) {
      pieceThere = piecesThere[i];
      if (pieceThere == 'n' || pieceThere == 'N') {
        boolean pieceThereWhite = Character.isUpperCase(pieceThere);
        if ((pieceThereWhite && !kingWhite) || (!pieceThereWhite && kingWhite)) {
          // Knight of opposite suit in spots
          return true;
        }
      }
    }

    return false;
  }

  // Helper method for kingDanger()
  // True if there is a valid attacking pawn for the space, false if not
  private boolean pawnCheck(int x, int y, boolean kingWhite, TestBoard test) {
    char pieceThere;

    // Pawns can only move in one direction, depending on their color
    if (kingWhite) {
      // King is white, look for black pawn aka one above
      if (y-1 > -1) {
        if (x-1 > -1) {
          pieceThere = test.getSpaceChar(x - 1, y - 1);
          if (pieceThere == 'p') {
            return true;
          }
        }
        if (x+1 < 8) {
          pieceThere = test.getSpaceChar(x + 1, y - 1);
          if (pieceThere == 'p') {
            return true;
          }
        }
      }
    } else {
      // King is black, look for white pawn below
      if (y+1 < 8) {
        if (x-1 > -1) {
          pieceThere = test.getSpaceChar(x - 1, y + 1);
          if (pieceThere == 'P') {
            return true;
          }
        }
        if (x+1 < 8) {
          pieceThere = test.getSpaceChar(x + 1, y + 1);
          if (pieceThere == 'P') {
            return true;
          }
        }
      }
    }

    // No dangerous pawns found
    return false;
  }

  // Helper method that looks for attacking kings
  private boolean kingChecks(int x, int y, boolean kingWhite, TestBoard test) {
    boolean danger;

    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
        if (i == 0 && j == 0) {
          // do nothing, original space
        } else {
          danger = kingCheck(x + i, y + j, kingWhite, test);
          if (danger) {
            return true;
          }
        }
      }
    }

    return false; // no danger founds
  }

  // Helper method that looks for attacking kings
  // True if there is a valid attacking king for the space, false if not
  private boolean kingCheck(int x, int y, boolean kingWhite, TestBoard test) {
    if (x > -1 && y > -1 && x < 8 && y < 8) {
      char pieceThere = test.getSpaceChar(x, y);

      if (pieceThere != '\u0000') {
        // Return false if char there is one that can take King
        boolean pieceThereWhite = Character.isUpperCase(pieceThere);

        if ((pieceThereWhite && !kingWhite) || (!pieceThereWhite && kingWhite)) {
          if (pieceThere == 'k' || pieceThere == 'K') {
            return true;
          }
        }
      }

      return false;
    } else {
      return false; // out of bounds
    }
  }

  // Helper method for kingDanger()
  // a = attacked, s = safe, one of own pieces present in the space, u = unsure yet
  private char bishopQueenCheck(int x, int y, boolean kingWhite, TestBoard test) {
    char pieceThere = test.getSpaceChar(x, y);

    if (pieceThere != '\u0000') {
      // Return false if char there is one that can take King
      boolean pieceThereWhite = Character.isUpperCase(pieceThere);
      if ((pieceThereWhite && !kingWhite) || (!pieceThereWhite && kingWhite)) {
        // If of opposite colors
        char pieceThereLower = Character.toLowerCase(pieceThere);
        // Bishop or queen there, king is being attacked
        if ( (pieceThereLower == 'b') || (pieceThereLower == 'q') ) {
          return 'a';
        } else {
          // The piece isn't the right type to attack
          return 's';
        }
      } else {
        // Same color, so king must be safe
        return 's';
      }
    }

    return 'u'; // No danger found
  } // end eastWestCheck()

  // Helper method for kingDanger(), looks for queen and rook north/south
  // a = attacked, s = safe, one of own pieces present in the space, u = unsure yet
  private char rookQueenCheck(int x, int y, boolean kingWhite, TestBoard test) {
    char pieceThere = test.getSpaceChar(x, y);

    if (pieceThere != '\u0000') {
      // Return false if char there is one that can take King
      boolean pieceThereWhite = Character.isUpperCase(pieceThere);
      if ((pieceThereWhite && !kingWhite) || (!pieceThereWhite && kingWhite)) {
        // If of opposite colors
        char pieceThereLower = Character.toLowerCase(pieceThere);
        // Rook or queen in space, king is being attacked
        if ( (pieceThereLower == 'r') || (pieceThereLower == 'q') ) {
          return 'a';
        } else {
          // The piece isn't the right type to attack
          return 's';
        }
      } else {
        // The same color, so the king must be safe
        return 's';
      }
    }

    return 'u'; // No danger found
  } // end northSouthCheck()

 // ----------------------------------------------------------------------------

 // Helper function for checkMove(), looks at the legal moves for a rook
 private boolean rookMove(int x_1, int y_1, int x_2, int y_2) {
   // Rooks can move north/south xor east/west
   // Shoot have to check every single space in between start and end to
   // make sure no pieces blocking the path
   if (y_1 != y_2 && x_1 != x_2) {
     return false;	// Not moving in a legal horizontal, or vertical direction
   }

   if (x_1 == x_2 && y_1 < y_2) {
     for (int i = y_1 + 1; i < y_2; i++) {
       if (my_storage.getSpaceChar(x_1, i) != '\u0000') {
         return false; // path blocked if not empty
       }
     }
   } else if (x_1 == x_2 && y_1 > y_2) {
     for (int i = y_1 - 1; i > y_2; i--) {
       if (my_storage.getSpaceChar(x_1, i) != '\u0000') {
         return false; // path blocked if not empty
       }
     }
   } else if (y_1 == y_2 && x_1 < x_2) {
     for (int i = x_1 + 1; i < x_2; i++) {
       if (my_storage.getSpaceChar(i, y_1) != '\u0000') {
         return false; // path blocked if not empty
       }
     }
   } else if (y_1 == y_2 && x_1 > x_2) {
     for (int i = x_1 - 1; i > x_2; i--) {
       if (my_storage.getSpaceChar(i, y_1) != '\u0000') {
         return false; // path blocked if not empty
       }
     }
   }
   return true;
 }

  // Helper function for checkMove(), looks at the legal moves for a bishop
  private boolean bishopMove(int x_1, int y_1, int x_2, int y_2) {
    if (x_2 > x_1 && y_2 > y_1) {
      // Moving to upper right
      for (int i = 1; i < (x_2 - x_1); i++) {
        if (my_storage.getSpaceChar(x_1 + i, y_1 + i) != '\u0000') {
          return false; // path blocked if not empty
        }
      }
    } else if (x_2 > x_1 && y_2 < y_1) {
      // Moving to lower right
      for (int i = 1; i < (x_2 - x_1); i++) {
        if (my_storage.getSpaceChar(x_1 + i, y_1 - i) != '\u0000') {
          return false; // path blocked if not empty
        }
      }
    } else if (x_2 < x_1 && y_2 < y_1) {
      // Moving to lower left
      for (int i = 1; i < (x_1 - x_2); i++) {
        if (my_storage.getSpaceChar(x_1 - i, y_1 - i) != '\u0000') {
          return false; // path blocked if not empty
        }
      }
    } else if (x_2 < x_1 && y_2 > y_1) {
      // Moving to upper left
      for (int i = 1; i < (x_1 - x_2); i++) {
        if (my_storage.getSpaceChar(x_1 - i, y_1 + i) != '\u0000') {
          return false; // path blocked if not empty
        }
      }
    }
    return true;
  }

  public String testGameEnded(String fen) {
	String result = "noResult";

	Stockfish player = new Stockfish();
    player.startEngine();
    // Tell the engine to switch to UCI mode
    player.send("uci");
	
	String bestMove = player.getBestMove(fen, 100);
	System.out.println("Best Move: " + bestMove);
	
	//Stockfish couldn't determine a best move given the current fen
	if(bestMove.equals("(non")) {
		
		//Indicates which color couldn't make any moves
		char turn = LaboonChess.getTurn();
		
		//Indicates the color that the user selected to play as
		char playercolor = LaboonChess.controller.playersColor;
		
		TestBoard testBoard = new TestBoard(my_storage, 0, 0, 0, 0);
		
		//Test if draw condition - stalemate, unable to move any pieces and king is not in check
		if(turn == 'w') {
			//If it's white's turn, see if their king is in danger
			boolean whiteKingDanger = kingDanger(testBoard.getWhiteKingX(), testBoard.getWhiteKingY(), 'K', testBoard);
			if(whiteKingDanger == false) {
				result = "draw";
				System.out.println("Draw by stalemate on white's turn");
			}
		}
		else {
			//If it's black's turn, see if their king is in danger
			boolean blackKingDanger = kingDanger(testBoard.getBlackKingX(), testBoard.getBlackKingY(), 'k', testBoard);
			if(blackKingDanger == false) {
				result = "draw";
				System.out.println("Draw by stalemate on black's turn");
			}
		}

		//If not a draw then it must be that someone won/loss
		if(result.equals("noResult")) {
			//If it was the player's turn and they have no best possible move then they lost
			if(turn == playercolor) {
				result = "loss";
			}
			//Else they won the game
			else {
				result = "win";
			}
		}
	}
	//There are still possible best moves
	//This else mainly tests only for draw conditions
	else {
		System.out.println("Testing insufficient pieces");
		
		//Test insufficient number of pieces to win game draw conditions
		result = checkInsufficientPieces(fen);
		
		//Test repetition of moves
		if(result.equals("noResult")) {
			
			System.out.println("Testing move repetition");
			
			result = checkRepetition();
		}
		//Test 50 move rule if all other draw conditions weren't met
		if(result.equals("noResult")) {
			
			System.out.println("Testing 50 move rule");
			
			result = check50MoveRule(fen);
		}
	}
	
	player.stopEngine();
	
	System.out.println("Results: " + result);
	
	return result;
  }
	
	//Test insufficient number of pieces to win game - draw conditions
	//This implementation will change when there is a running counter on the taken panel
	//Using the taken panel will be more efficient, only have to pull a value instead of doing checks
	//	would be able to test when there is a 2 pieces on one side and 1 pieces on another side
	//	Test those pieces and see if its the one's that lead to a draw
	public String checkInsufficientPieces(String fen) {
		String result = "noResult";
		
		//Stores what pieces remain
		String blackPieces = "";
		String whitePieces = "";
		
		//Counts the number of pieces each side has remaining
		int upperCase = 0;
		int lowerCase = 0;
		
		String[] fenSections = fen.split(" ");
		String boardState = fenSections[0];
		
		//Look at the number of pieces that both sides have
		//Keep track of the pieces that remain
		for(int i = 0; i < boardState.length(); i++) {
			if(Character.isUpperCase(boardState.charAt(i))) {
				
				upperCase++;
				
				if(boardState.charAt(i) != 'K') {
					whitePieces += boardState.charAt(i);
				}
			}
			else if(Character.isLowerCase(boardState.charAt(i))) {
				
				lowerCase++;
				
				if(boardState.charAt(i) != 'k') {
					blackPieces += boardState.charAt(i);
				}
			}
		}
		
		//Check if there are only three pieces total for both sides
		//If so then test the draw condition
		if((upperCase == 2 && lowerCase == 1) || (upperCase == 1 && lowerCase == 2)) {
			//Test knight draw and bishop draw for white side
			if(upperCase == 2) {
				//Test if the only piece the white side has left besides the king is a knight or bishop
				//If so then it's a draw
				if(whitePieces.equals("N") || whitePieces.equals("B")) {
					result = "draw";
					System.out.println("Draw by Insufficient Pieces to Win or Lose");
				}
			}

			//Test knight draw and bishop draw for white side
			else {
				//Test if the only piece the black side has left besides the king is a knight or bishop
				//If so then it's a draw
				if(blackPieces.equals("n") || blackPieces.equals("b")) {
					result = "draw";
					System.out.println("Draw by Insufficient Pieces to Win or Lose");
				}
			}
		}
		
		return result;
	}
	
	//TODO
	//Test draw condition - Repetition of Position
	//When a chess position is repeated three times, perpetual checking
	//This code could possibly be more efficient when we implement a history of the moves
	//Because then we can just check the last three moves that each player made 
	//and see if it satisfies the condition for drawing
	
	//Current implementation looks at the last 6 moves
	//If the moves for the last three white turns match and same goes for the
	//black turns then it's a draw by repetition
	
	//Currently previous moves aren't saved to the save file
	//So if they are in the middle of the draw condition (i.e. last 2 moves from each player 
	//were the same) then when the save file is reloaded the user will have to go through the 
	//three repeated moves again (instead of just one from previously)
	public String checkRepetition() {
		String result = "noResult";
		
		LinkedList moves = BoardPanel.previousMoves;
		
		if(moves.size() == 6) {
			System.out.println("Last 6 moves: " + moves);
			
			//Check if all of the moves made by both players have been the same for the last three turns
			if((moves.get(0).equals(moves.get(4))) && (moves.get(1).equals(moves.get(5)))) {
				result = "draw";
				System.out.println("Draw by Repetition of Position");
			}
		}
		return result;
	}
	
	
	//Tests the draw condition - 50 move rule
	//If no pieces have been taken/traded for 50 moves
	public String check50MoveRule(String fen) {
		String result = "noResult";
		
		//Test for 50 move rule
		//Fen contains a counter for the 50 move rule so we can just test that
		String[] fenSections = fen.split(" ");
		int check50MoveRule = Integer.parseInt(fenSections[4]);
		if(check50MoveRule >= 50) {
			result = "draw";
			System.out.println("Draw by 50 Move Rule");
		}
	
		//Test code to display number of moves with nothing being taken
		System.out.println("Check 50 move rule: " + check50MoveRule); 

		return result;
	}
}
