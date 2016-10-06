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

}