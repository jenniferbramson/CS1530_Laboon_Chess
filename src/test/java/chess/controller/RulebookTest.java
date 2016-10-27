import org.junit.Test;
import static org.junit.Assert.*;

import chess.Rulebook;
import chess.Storage;

public class RulebookTest {

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
