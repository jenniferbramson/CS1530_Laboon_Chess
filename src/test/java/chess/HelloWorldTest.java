import org.junit.Test;
import static org.junit.Assert.*;

import chess.HelloWorld;

public class HelloWorldTest {

  @Test
  public void testHello1() {
    HelloWorld.speak();
  }

  @Test
  public void testHello2() {
    assertEquals(HelloWorld.five(), 5);
  }

  @Test
  public void testHello3() {
    assertEquals(HelloWorld.chess(), 'p');
  }

}
