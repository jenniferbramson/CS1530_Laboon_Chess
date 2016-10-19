import org.junit.Test;
import static org.junit.Assert.*;

import chess.Rulebook;
import chess.Storage;

public class RulebookTest {

  @Test
  public void illegalMoveBlack1() {
  	Storage temp = new Storage();
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpaceChar(1, 1), 'p');
    if (r.checkMove(1, 1, 5, 5)) {
		  temp.movePiece(1, 1, 5, 5);
    }
    assertNotEquals(temp.getSpaceChar(5, 5), 'p');
  }

  @Test
  public void illegalMoveBlack2() {
    Storage temp = new Storage();
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpaceChar(1, 0), 'n');
    if (r.checkMove(0, 1, 4, 5)) {
      temp.movePiece(0, 1, 4, 5);
    }
    assertNotEquals(temp.getSpaceChar(5, 4), 'n');
  }

  @Test
  public void illegalMoveWhite1() {
    Storage temp = new Storage();
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpaceChar(1, 6), 'P');
    if (r.checkMove(6, 1, 5, 5)) {
      temp.movePiece(6, 1, 5, 5);
    }
    assertNotEquals(temp.getSpaceChar(5, 5), 'P');
  }

  @Test
  public void illegalMoveWhite2() {
    Storage temp = new Storage();
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpaceChar(1, 7), 'N');
    if (r.checkMove(7, 1, 4, 5)) {
      temp.movePiece(7, 1, 4, 5);
    }
    assertNotEquals(temp.getSpaceChar(5, 4), 'N');
  }

  @Test
  public void legalMoveBlack1() {
    Storage temp = new Storage();
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpaceChar(1, 0), 'n');
    if (r.checkMove(0, 1, 2, 2)) {
      temp.movePiece(0, 1, 2, 2);
    }
    assertEquals(temp.getSpaceChar(2, 2), 'n');
  }

  @Test
  public void legalMoveBlack2() {
    Storage temp = new Storage();
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpaceChar(1, 0), 'n');
    if (r.checkMove(0, 1, 2, 0)) {
      temp.movePiece(0, 1, 2, 0);
    }
    assertEquals(temp.getSpaceChar(0, 2), 'n');
  }

  @Test
  public void legalMoveWhite1() {
    Storage temp = new Storage();
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpaceChar(1, 7), 'N');
    if (r.checkMove(7, 1, 5, 2)) {
      temp.movePiece(7, 1, 5, 2);
    }
    assertEquals(temp.getSpaceChar(2, 5), 'N');
  }

  @Test
  public void legalMoveWhite2() {
    Storage temp = new Storage();
    Rulebook r = new Rulebook(temp);
    assertEquals(temp.getSpaceChar(1, 7), 'N');
    if (r.checkMove(7, 1, 5, 0)) {
      temp.movePiece(7, 1, 5, 0);
    }
    assertEquals(temp.getSpaceChar(0, 5), 'N');
  }

}
