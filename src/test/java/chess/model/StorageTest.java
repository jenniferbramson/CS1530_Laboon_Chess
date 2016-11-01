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
		//testing to see if movePiece works
		Storage temp = new Storage();
		assertEquals(temp.getSpace(1,2), "p");
		temp.movePiece(1, 2, 4, 4);
		assertEquals(temp.getSpace(1,2), "");
		assertEquals(temp.getSpace(4,4), "p");
	}

  @Test
  public void storageEnPassant() {
    //testing to see if en passant works
    Storage temp = new Storage("rnbqkbnr/pp1ppp1p/2B5/6p1/2pPP3/8/PPP2PPP/RNBQK1NR b KQkq d3 7 4");
    assertEquals(temp.getSpace(4,2), "p");
    assertEquals(temp.getSpace(5,3), "");
    assertEquals(temp.getSpace(4,3), "P");
    temp.movePiece(4, 2, 5, 3);
    assertEquals(temp.getSpace(5,3), "p");
    assertEquals(temp.getSpace(4,3), "");
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

}
