import org.junit.Test;
import static org.junit.Assert.*;

import chess.TurnController;

public class TurnControllerTest {

  @Test
  public void SwitchTurn1() {
    TurnController controller = new TurnController();
    char turn = controller.getTurn();

    if (turn == 'b') {
      controller.changeTurn();
      assertEquals(controller.getTurn(), 'w');
    } else if (turn == 'w') {
      controller.changeTurn();
      assertEquals(controller.getTurn(), 'b');
    }

  }

}
