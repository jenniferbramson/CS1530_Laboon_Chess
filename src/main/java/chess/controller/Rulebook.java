package chess;
import static java.lang.Math.abs;

public class Rulebook {

  private Storage my_storage;

  public Rulebook(Storage my_storage) {
    this.my_storage = my_storage;
  }

  // Checks if moves are legal. It does not check the case of no movement since
  // the board checks that
  public boolean checkMove(int y_1, int x_1, int y_2, int x_2) {
    char piece = my_storage.getSpaceChar(x_1, y_1);
    char space = my_storage.getSpaceChar(x_2, y_2);

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
        // On first move, pawn can move two spaces
        // Otherwise can only move one place - north if space above is empty,
        // diagonally if taking a piece
        if (x_1 == x_2 && y_1 == 1 && y_2 == 3 && space == '\u0000') {
          if (my_storage.getSpaceChar(x_1, 2) == '\u0000') {
            // First move down 2 ok, nothing in the way
            return true;
          }
        } else if (x_1 == x_2 && (y_1 + 1) == y_2 && space == '\u0000') {
          // Move north ok
          return true;
        } else if (((x_1 + 1 == x_2) || (x_1 - 1 == x_2)) && (y_1 + 1) == y_2 &&
                  Character.isUpperCase(space)) {
          // Take piece diagonally ok
          return true;
        }
        break;
      case 'P':
        // On first move, pawn can move two spaces
        // Otherwise can only move one place - north if space above is empty,
        // diagonally if taking a piece
        if (x_1 == x_2 && y_1 == 6 && y_2 == 4 && space == '\u0000') {
          if (my_storage.getSpaceChar(x_1, 5) == '\u0000') {
            // First move down 2 ok, nothing in the way
            return true;
          }
        } else if (x_1 == x_2 && (y_1 - 1) == y_2 && space == '\u0000') {
          // Move north ok
          return true;
        } else if (((x_1 + 1 == x_2) || (x_1 - 1 == x_2)) && (y_1 - 1) == y_2 &&
                  Character.isLowerCase(space)) {
          // Take piece diagonally ok
          return true;
        }
        break;
      case 'n':
      case 'N':
        // Knights move in an 'L' shape whether or not opponent piece is there
        // Ignore moves where own side's piece is there though
        if (((x_1 + 2 == x_2) || (x_1 - 2 == x_2)) && ((y_1 + 1 == y_2) || (y_1 - 1 == y_2))) {
          return true;
        } else if (((y_1 + 2 == y_2) || (y_1 - 2 == y_2)) && ((x_1 + 1 == x_2) || (x_1 - 1 == x_2))) {
          return true;
        }
        break;
      case 'b':
      case 'B':
        // Bishop can move along the diagonals
        if (abs(x_1 - x_2) == abs(y_1 - y_2)) {
          // space or upper case, diagonal
          return bishopCheck(x_1, y_1, x_2, y_2);
        }
        break;
      case 'k':
      case 'K':
        // Can only move one square in any direction
        int xDiff = abs((x_1 - x_2));
        int yDiff = abs((y_1 - y_2));
        if ((xDiff == 1 && yDiff == 1) || (xDiff == 1 && yDiff == 0) || (xDiff == 0 && yDiff == 1)) {
          return true;
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
          return bishopCheck(x_1, y_1, x_2, y_2);
        }
      case 'r':
      case 'R':
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
    } // end switch statement

    return false; // If reached this point, false
  } // end checkMove

  // Helper function for checkMove(), looks at the legal moves for a bishop
  private boolean bishopCheck(int x_1, int y_1, int x_2, int y_2) {
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
