package chess;
import static java.lang.Math.abs;

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
        // Queen is rook plus diagonal ability, so don't break at end and incorporate
        // rook ability that way
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

    if (x -1 > -1 && y - 2 > -1)
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
      pieceThere = test.getSpaceChar(x - 1, y - 1);
      if (pieceThere == 'p') {
        return true;
      }
      pieceThere = test.getSpaceChar(x + 1, y - 1);
      if (pieceThere == 'p') {
        return true;
      }
    } else {
      // King is black, look for white pawn below
      pieceThere = test.getSpaceChar(x - 1, y + 1);
      if (pieceThere == 'P') {
        return true;
      }
      pieceThere = test.getSpaceChar(x + 1, y + 1);
      if (pieceThere == 'P') {
        return true;
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

}
