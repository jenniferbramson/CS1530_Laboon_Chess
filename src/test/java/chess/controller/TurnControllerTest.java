import org.junit.Test;
import static org.junit.Assert.*;

import chess.TurnController;

public class TurnControllerTest {

  @Test
  public void SwitchTurn1() {
    char playersColor = 'b';
    TurnController controller = new TurnController(playersColor);
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
