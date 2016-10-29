import org.junit.Test;
import static org.junit.Assert.*;

import chess.Rulebook;
import chess.Storage;

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
  public void testWhiteLegalCastleRight() {
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
    // Moves through queen check
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
    // Moves through bishop check
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
    // Moves through bishop check
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
    // Moves through bishop check
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
    // King already moved
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
