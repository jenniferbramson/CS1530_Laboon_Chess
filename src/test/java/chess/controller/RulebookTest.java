import org.junit.Test;
import static org.junit.Assert.*;

import chess.Rulebook;
import chess.Storage;

/***
  This class tests that pieces can only move in legal ways. This class by no
  means tests every possible illegal move or every possible legal move. For
  the most part, castling and en passant are the most heavily tested and the
  remaining illegal moves are pieces moving in the wrong pattern.
***/

public class RulebookTest {

  @Test
  public void testEnPassant() {
    //testing to see if en passant passes rulebook
    Storage temp = new Storage("rnbqkbnr/pp1ppp1p/2B5/6p1/2pPP3/8/PPP2PPP/RNBQK1NR b KQkq d3 7 4");
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpace(4,2), "p");
    assertEquals(temp.getSpace(5,3), "");
    assertEquals(temp.getSpace(4,3), "P");
    if (r.checkMove(4, 2, 5, 3)) {
      temp.movePiece(4, 2, 5, 3);
    }
    assertEquals(temp.getSpace(5,3), "p");
    assertEquals(temp.getSpace(4,3), "");
    assertEquals(temp.getSpace(4,2), "");
  }

  @Test
  public void testIllegalEnPassant1() {
    // En Passant causes king to be in check
    Storage temp = new Storage("rnbqkbnr/1pp2ppp/8/3Pp3/p7/8/PPP1PPPP/RNBK1BNR w - e6 12 7");
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpace(3,3), "P");
    assertEquals(temp.getSpace(3,4), "p");
    assertEquals(temp.getSpace(2,4), "");
    if (r.checkMove(3, 3, 2, 4)) {
      temp.movePiece(3, 3, 2, 4);
    }
    assertNotEquals(temp.getSpace(2,4), "P");
    assertEquals(temp.getSpace(3,3), "P");
    assertEquals(temp.getSpace(3,4), "p");
  }

  @Test
  public void testIllegalEnPassant2() {
    // En Passant causes king to be in check
    Storage temp = new Storage("rnb1kbnr/1p1p1ppp/8/1K1Ppq2/2p4P/p7/PPP1PPP1/RNBQ1BNR w kq e6 20 11");
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpace(3,3), "P");
    assertEquals(temp.getSpace(3,4), "p");
    assertEquals(temp.getSpace(2,4), "");
    if (r.checkMove(3, 3, 2, 4)) {
      temp.movePiece(3, 3, 2, 4);
    }
    assertNotEquals(temp.getSpace(2,4), "P");
    assertEquals(temp.getSpace(3,3), "P");
    assertEquals(temp.getSpace(3,4), "p");
  }

  @Test
  public void testWhiteLegalCastleRight() {
    // Legal Kingside castling for the white King
    Storage temp = new Storage("rnbqkbnr/p1p2ppp/8/1p1pp3/4P3/5N2/PPPPBPPP/RNBQK2R w KQkq - 6 4");
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpaceChar(4, 7), 'K');
    assertEquals(temp.getSpaceChar(7, 7), 'R');
    if (r.checkMove(7, 4, 7, 6)) {
      temp.movePiece(7, 4, 7, 6);
    }
    assertEquals(temp.getSpaceChar(6, 7), 'K');
    assertEquals(temp.getSpaceChar(5, 7), 'R');
  }

  @Test
  public void testWhiteLegalCastleLeft() {
    // Legal Queenside castling for the white King
    Storage temp = new Storage("Bn1qkb2/p4p2/2p1prp1/1p3bP1/3P3N/1PNQB3/P1P5/R3K2R w Q - 52 27");
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpaceChar(4, 7), 'K');
    assertEquals(temp.getSpaceChar(0, 7), 'R');
    if (r.checkMove(7, 4, 7, 2)) {
      temp.movePiece(7, 4, 7, 2);
    }
    assertEquals(temp.getSpaceChar(2, 7), 'K');
    assertEquals(temp.getSpaceChar(3, 7), 'R');
  }

  @Test
  public void testWhiteIllegalCastleQueen() {
    // King moves through queen check, which is illegal because kings cannot
    // move through checks
    Storage temp = new Storage("rnb1kbnr/pppp1ppp/4p3/8/8/4PNq1/PPPP1P1P/RNBQK2R w KQkq - 8 5");
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpaceChar(4, 7), 'K');
    assertEquals(temp.getSpaceChar(7, 7), 'R');
    if (r.checkMove(7, 4, 7, 6)) {
      temp.movePiece(7, 4, 7, 6);
    }
    assertNotEquals(temp.getSpaceChar(6, 7), 'K');
    assertNotEquals(temp.getSpaceChar(5, 7), 'R');
  }

  @Test
  public void testWhiteIllegalCastleBishop() {
    // King moves through bishop check
    Storage temp = new Storage("Bn1qkb2/p4p2/2p1prp1/1Q4P1/3P3N/1PN1B3/P1b5/R3K2R w Q - 54 28");
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpaceChar(4, 7), 'K');
    assertEquals(temp.getSpaceChar(0, 7), 'R');
    if (r.checkMove(7, 4, 7, 2)) {
      temp.movePiece(7, 4, 7, 2);
    }
    assertNotEquals(temp.getSpaceChar(2, 7), 'K');
    assertNotEquals(temp.getSpaceChar(3, 7), 'R');
  }

  @Test
  public void testBlackIllegalCastleKnight() {
    // King moves through knight check
    Storage temp = new Storage("r3kbnr/Nqppppp1/Qp6/6np/8/1PP1PPP1/P6P/R1B1KBNR b kq - 25 13");
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpaceChar(4, 0), 'k');
    assertEquals(temp.getSpaceChar(0, 0), 'r');
    if (r.checkMove(0, 4, 0, 2)) {
      temp.movePiece(0, 4, 0, 2);
    }
    assertNotEquals(temp.getSpaceChar(2, 0), 'k');
    assertNotEquals(temp.getSpaceChar(3, 0), 'r');
  }

  @Test
  public void testBlackIllegalCastleRook() {
    // Moves through rook check on the same x axis as the King
    Storage temp = new Storage("r3k2R/pppqp3/2np1pp1/5b2/2P1P3/3P4/PP2NPP1/RNBQKB2 b Qq - 23 12");
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpaceChar(4, 0), 'k');
    assertEquals(temp.getSpaceChar(0, 0), 'r');
    if (r.checkMove(0, 4, 0, 2)) {
      temp.movePiece(0, 4, 0, 2);
    }
    assertNotEquals(temp.getSpaceChar(2, 0), 'k');
    assertNotEquals(temp.getSpaceChar(3, 0), 'r');
  }

  @Test
  public void testBlacklegalCastle() {
    // King moves through bishop check
    Storage temp = new Storage("r3kbnr/1qppppp1/Qp2n3/1N5p/8/1PP1PPP1/P6P/R1B1KBNR b kq - 27 14");
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpaceChar(4, 0), 'k');
    assertEquals(temp.getSpaceChar(0, 0), 'r');
    if (r.checkMove(0, 4, 0, 2)) {
      temp.movePiece(0, 4, 0, 2);
    }
    assertEquals(temp.getSpaceChar(2, 0), 'k');
    assertEquals(temp.getSpaceChar(3, 0), 'r');
  }

  @Test
  public void testWhiteIllegalCastle1() {
    // King already moved - Kings cannot castle once they have moved
    Storage temp = new Storage("rnbqkbnr/p1p4p/5pp1/1p1pp3/4P3/5N2/PPPPBPPP/RNBQK2R w kq - 10 6");
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpaceChar(4, 7), 'K');
    assertEquals(temp.getSpaceChar(7, 7), 'R');
    if (r.checkMove(7, 4, 7, 6)) {
      temp.movePiece(7, 4, 7, 6);
    }
    assertNotEquals(temp.getSpaceChar(6, 7), 'K');
    assertNotEquals(temp.getSpaceChar(5, 7), 'R');
  }

  @Test
  public void testWhiteIllegalCastleRook() {
    // King moves through a check from rook
    Storage temp = new Storage("rnbqkbn1/p1p1ppp1/8/1p1p3p/4P3/1P3N2/P1PPBPrP/RNBQK2R w KQq - 12 7");
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpaceChar(4, 7), 'K');
    assertEquals(temp.getSpaceChar(7, 7), 'R');
    if (r.checkMove(7, 4, 7, 6)) {
      temp.movePiece(7, 4, 7, 6);
    }
    assertNotEquals(temp.getSpaceChar(6, 7), 'K');
    assertNotEquals(temp.getSpaceChar(5, 7), 'R');
  }

  @Test
  public void testWhiteIllegalCastle3() {
    // King moves through a check from knight
    Storage temp = new Storage("rnbqkb2/p1p1ppp1/7r/1p1pN2p/8/1P4n1/P1PPBP1P/RNBQK2R w KQq - 22 12");
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpaceChar(4, 7), 'K');
    assertEquals(temp.getSpaceChar(7, 7), 'R');
    if (r.checkMove(7, 4, 7, 6)) {
      temp.movePiece(7, 4, 7, 6);
    }
    assertNotEquals(temp.getSpaceChar(6, 7), 'K');
    assertNotEquals(temp.getSpaceChar(5, 7), 'R');
  }

  @Test
  public void testWhiteIllegalCastle4() {
    // King moves through a check from pawn
    Storage temp = new Storage("Bnbqkb2/p1p1pp2/5rp1/1p4P1/3P4/1P3N2/P1P4p/RNBQK2R w KQ - 40 21");
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpaceChar(4, 7), 'K');
    assertEquals(temp.getSpaceChar(7, 7), 'R');
    if (r.checkMove(7, 4, 7, 6)) {
      temp.movePiece(7, 4, 7, 6);
    }
    assertNotEquals(temp.getSpaceChar(6, 7), 'K');
    assertNotEquals(temp.getSpaceChar(5, 7), 'R');
  }

  @Test
  public void illegalMoveBlackPawn() {
    // White pawn tries to move 4 spaces in both directions, which is an illegal
    // because pawns can only move two spaces at the beginning (row 1 for white)
  	Storage temp = new Storage();
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpaceChar(1, 1), 'p');
    if (r.checkMove(1, 1, 5, 5)) {
		  temp.movePiece(1, 1, 5, 5);
    }
    assertNotEquals(temp.getSpaceChar(5, 5), 'p');
  }

  @Test
  public void illegalMoveBlackKnight() {
    // Black knight tried to move 5 columns and 4 rows - illegal pattern
    Storage temp = new Storage();
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpaceChar(1, 0), 'n');
    if (r.checkMove(0, 1, 4, 5)) {
      temp.movePiece(0, 1, 4, 5);
    }
    assertNotEquals(temp.getSpaceChar(5, 4), 'n');
  }

  @Test
  public void illegalMoveWhitePawn() {
    // White pawn tries to move 4 columns - pawns cannot move horizontally
    Storage temp = new Storage();
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpaceChar(1, 6), 'P');
    if (r.checkMove(6, 1, 5, 5)) {
      temp.movePiece(6, 1, 5, 5);
    }
    assertNotEquals(temp.getSpaceChar(5, 5), 'P');
  }

  @Test
  public void illegalMoveWhiteKnight() {
    Storage temp = new Storage();
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpaceChar(1, 7), 'N');
    if (r.checkMove(7, 1, 4, 5)) {
      temp.movePiece(7, 1, 4, 5);
    }
    assertNotEquals(temp.getSpaceChar(5, 4), 'N');
  }

  @Test
  public void legalMoveWhiteRook() {
    // White rook moving legally
    Storage temp = new Storage("1nbqkbnr/1p1ppppp/r7/p1p5/P3P3/R7/1PPP1PPP/1NBQKBNR w Kk - 0 4");
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpaceChar(0, 5), 'R');
    if (r.checkMove(5, 0, 5, 4)) {
      temp.movePiece(5, 0, 5, 4);
    }
    assertEquals(temp.getSpaceChar(4, 5), 'R');
  }

  @Test
  public void illegalMoveWhiteRook() {
    // A white rook trying to move vertically - rooks can only move in one
    // direction at a time
    Storage temp = new Storage("1nbqkbnr/1p1ppppp/r7/p1p5/P3P3/R7/1PPP1PPP/1NBQKBNR w Kk - 0 4");
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpaceChar(0, 5), 'R');
    if (r.checkMove(5, 0, 6, 4)) {
      temp.movePiece(5, 0, 6, 4);
    }
    assertNotEquals(temp.getSpaceChar(4, 6), 'R');
  }

  @Test
  public void legalMoveWhiteKing() {
    Storage temp = new Storage("1nbqkbnr/1p1ppppp/r7/p1p5/P3P3/R7/1PPP1PPP/1NBQKBNR w Kk - 0 4");
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpaceChar(4, 7), 'K');
    if (r.checkMove(7, 4, 6, 4)) {
      temp.movePiece(7, 4, 6, 4);
    }
    assertEquals(temp.getSpaceChar(4, 6), 'K');
  }

  @Test
  public void illegalMoveWhiteKing() {
    // King illegally tries to move more than one space vertically
    Storage temp = new Storage("1nbqkbnr/1p1ppppp/r7/p1p5/P3P3/R7/1PPP1PPP/1NBQKBNR w Kk - 0 4");
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpaceChar(4, 7), 'K');
    if (r.checkMove(7, 4, 5, 4)) {
      temp.movePiece(7, 5, 5, 4);
    }
    assertNotEquals(temp.getSpaceChar(4, 5), 'K');
  }

  @Test
  public void legalMoveBlackKnight1() {
    Storage temp = new Storage();
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpaceChar(1, 0), 'n');
    if (r.checkMove(0, 1, 2, 2)) {
      temp.movePiece(0, 1, 2, 2);
    }
    assertEquals(temp.getSpaceChar(2, 2), 'n');
  }

  @Test
  public void legalMoveBlackKnight2() {
    Storage temp = new Storage();
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpaceChar(1, 0), 'n');
    if (r.checkMove(0, 1, 2, 0)) {
      temp.movePiece(0, 1, 2, 0);
    }
    assertEquals(temp.getSpaceChar(0, 2), 'n');
  }

  @Test
  public void legalMoveWhiteKnight1() {
    Storage temp = new Storage();
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpaceChar(1, 7), 'N');
    if (r.checkMove(7, 1, 5, 2)) {
      temp.movePiece(7, 1, 5, 2);
    }
    assertEquals(temp.getSpaceChar(2, 5), 'N');
  }

  @Test
  public void legalMoveWhiteKnight2() {
    Storage temp = new Storage();
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpaceChar(1, 7), 'N');
    if (r.checkMove(7, 1, 5, 0)) {
      temp.movePiece(7, 1, 5, 0);
    }
    assertEquals(temp.getSpaceChar(0, 5), 'N');
  }

}
