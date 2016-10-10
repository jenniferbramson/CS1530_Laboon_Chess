import org.junit.Test;
import static org.junit.Assert.*;

import chess.Storage;

public class StorageTest {

  @Test
  public void storageTest1() {
		//testing to see if constructor works
		Storage temp = new Storage();
    assertEquals(temp.getSpace(4,4), "");
  }
	
	@Test
	public void storageTest2() {
		//testing to see if movePiece workds
		Storage temp = new Storage();
		assertEquals(temp.getSpace(1,2), "p");
		temp.movePiece(1, 2, 4, 4);
		assertEquals(temp.getSpace(1,2), "");
		assertEquals(temp.getSpace(4,4), "p");
	}
	
	@Test
	public void storageTest3() {
		//testing to see if constructor works passing in fen string
		Storage temp = new Storage("rnbqkb1r/ppp2ppp/4pn2/3p4/3P4/2N2N2/PPP1PPPP/R1BQKB1R b KQkq - 2 4");
		assertFalse(temp.isWhiteTurn());
		assertEquals(Character.getNumericValue('3'),3);
		assertEquals(temp.getSpace(3,3), "p");
		assertEquals(temp.getSpace(5,5), "N");
	}

}