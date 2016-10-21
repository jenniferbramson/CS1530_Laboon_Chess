import org.junit.Test;
import static org.junit.Assert.*;

import chess.TurnController;

public class TurnControllerTest {

  @Test
  public void SwitchTurn1() {
    TurnController controller = new TurnController();
    char turn = controller.getTurn();

    Storage temp = new Storage();

    if (turn == 'b') {
      temp.movePiece(1, 1, 3, 1);
      assertEquals(controller.getTurn(), 'w');
    } else if (turn == 'w') {
      temp.movePiece(6, 1, 4, 1);
      assertEquals(controller.getTurn(), 'b');
    }

  }

}
