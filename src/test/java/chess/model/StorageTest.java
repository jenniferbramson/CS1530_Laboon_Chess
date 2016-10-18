import org.junit.Test;
import static org.junit.Assert.*;

import chess.Storage;

public class StorageTest {

  @Test
  public void storageTestDefaultConstructor() {
		//testing to see if constructor works
		Storage temp = new Storage();
    assertEquals(temp.getSpace(4,4), "");
  }

	@Test
	public void storageTestMovePiece() {
		//testing to see if movePiece workds
		Storage temp = new Storage();
		assertEquals(temp.getSpace(1,2), "p");
		temp.movePiece(1, 2, 4, 4);
		assertEquals(temp.getSpace(1,2), "");
		assertEquals(temp.getSpace(4,4), "p");
	}

	@Test
	public void storageTestLoadFen() {
		//testing to see if constructor works passing in fen string
		Storage temp = new Storage("rnbqkb1r/ppp2ppp/4pn2/3p4/3P4/2N2N2/PPP1PPPP/R1BQKB1R b KQkq - 2 4");
		assertFalse(temp.isWhiteTurn());
		assertEquals(Character.getNumericValue('3'),3);
		assertEquals(temp.getSpace(3,3), "p");
		assertEquals(temp.getSpace(5,5), "N");
	}

	@Test
	public void storageTestPrintFen() {
		String s = "rnbqkb1r/ppp2ppp/4pn2/3p4/3P4/2N2N2/PPP1PPPP/R1BQKB1R b KQkq - 0 0";
		Storage temp = new Storage(s);
		assertEquals(temp.getFen(),s);
	}

  @Test
  public void illegalMoveBlack1() {
  	Storage temp = new Storage("rnbqkb1r/ppp2ppp/4pn2/3p4/3P4/2N2N2/PPP1PPPP/R1BQKB1R b KQkq - 2 4");
    assertEquals(temp.getSpaceChar(1, 1), 'p');
    if (temp.checkMove(1, 1, 5, 5)) {
		  temp.movePiece(1, 1, 5, 5);
    }
    assertNotEquals(temp.getSpaceChar(5, 5), 'p');
  }

  @Test
  public void illegalMoveBlack2() {
    Storage temp = new Storage("rnbqkb1r/ppp2ppp/4pn2/3p4/3P4/2N2N2/PPP1PPPP/R1BQKB1R b KQkq - 2 4");
    assertEquals(temp.getSpaceChar(1, 0), 'n');
    if (temp.checkMove(0, 1, 4, 5)) {
      temp.movePiece(0, 1, 4, 5);
    }
    assertNotEquals(temp.getSpaceChar(5, 4), 'n');
  }

  @Test
  public void illegalMoveWhite1() {
    Storage temp = new Storage("rnbqkb1r/ppp2ppp/4pn2/3p4/3P4/2N2N2/PPP1PPPP/R1BQKB1R b KQkq - 2 4");
    assertEquals(temp.getSpaceChar(1, 6), 'p');
    if (temp.checkMove(6, 1, 5, 5)) {
      temp.movePiece(6, 1, 5, 5);
    }
    assertNotEquals(temp.getSpaceChar(5, 5), 'p');
  }

  @Test
  public void illegalMoveWhite2() {
    Storage temp = new Storage("rnbqkb1r/ppp2ppp/4pn2/3p4/3P4/2N2N2/PPP1PPPP/R1BQKB1R b KQkq - 2 4");
    assertEquals(temp.getSpaceChar(1, 7), 'n');
    if (temp.checkMove(7, 1, 4, 5)) {
      temp.movePiece(7, 1, 4, 5);
    }
    assertNotEquals(temp.getSpaceChar(5, 4), 'n');
  }

  @Test
  public void legalMoveBlack1() {
    Storage temp = new Storage("rnbqkb1r/ppp2ppp/4pn2/3p4/3P4/2N2N2/PPP1PPPP/R1BQKB1R b KQkq - 2 4");
    assertEquals(temp.getSpaceChar(1, 0), 'n');
    if (temp.checkMove(0, 1, 2, 2)) {
      temp.movePiece(0, 1, 2, 2);
    }
    assertEquals(temp.getSpaceChar(2, 2), 'n');
  }

  @Test
  public void legalMoveBlack2() {
    Storage temp = new Storage("rnbqkb1r/ppp2ppp/4pn2/3p4/3P4/2N2N2/PPP1PPPP/R1BQKB1R b KQkq - 2 4");
    assertEquals(temp.getSpaceChar(1, 0), 'n');
    if (temp.checkMove(0, 1, 2, 0)) {
      temp.movePiece(0, 1, 2, 0);
    }
    assertEquals(temp.getSpaceChar(0, 2), 'n');
  }

  @Test
  public void legalMoveWhite1() {
    Storage temp = new Storage("rnbqkb1r/ppp2ppp/4pn2/3p4/3P4/2N2N2/PPP1PPPP/R1BQKB1R b KQkq - 2 4");
    assertEquals(temp.getSpaceChar(1, 7), 'n');
    if (temp.checkMove(7, 1, 5, 2)) {
      temp.movePiece(7, 1, 5, 2);
    }
    assertEquals(temp.getSpaceChar(2, 5), 'n');
  }

  @Test
  public void legalMoveWhite2() {
    Storage temp = new Storage("rnbqkb1r/ppp2ppp/4pn2/3p4/3P4/2N2N2/PPP1PPPP/R1BQKB1R b KQkq - 2 4");
    assertEquals(temp.getSpaceChar(1, 7), 'n');
    if (temp.checkMove(7, 1, 5, 0)) {
      temp.movePiece(7, 1, 5, 0);
    }
    assertEquals(temp.getSpaceChar(0, 5), 'n');
  }

}
