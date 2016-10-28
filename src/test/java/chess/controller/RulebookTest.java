import org.junit.Test;
import static org.junit.Assert.*;

import chess.Rulebook;
import chess.Storage;

public class RulebookTest {

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
  public void testWhiteIllegalCastle2() {
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
