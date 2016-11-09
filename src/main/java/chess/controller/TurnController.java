package chess;

// Tells whose turn it is. Must call changeTurn() after each turn is made.
// Tells if it is white or black's turn and if it is the player's or the
// computer's turn.
public class TurnController {

  private boolean playersTurn = true;  // Default: start off with player's turn
  protected static char playersColor;
  private char turn;
  private ConsoleGraphics graphics;
  private boolean graphicsExist = false;
  protected static String resultsOfGame = "noResult";
  
  int moveRule50Counter = 0;

  public TurnController(char playersColor) {
    turn = 'w';
    this.playersColor = playersColor;
    if (playersColor == 'w') {
      playersTurn = true;
    } else if (playersColor == 'b') {
      playersTurn = false;
    } else {
      System.out.println("Error. Invalid TurnController input.");
      System.exit(2);
    }
  }

  public TurnController(char currentColor, char playersColor) {
    if ((currentColor == 'b' || currentColor == 'w') && (playersColor == 'b' || playersColor == 'w')) {
      turn = currentColor;
      this.playersColor = playersColor;
      if (playersColor == currentColor) {
        playersTurn = true;
      } else {
        playersTurn = false;
      }
    } else {
      System.out.println("Error. Invalid TurnController input.");
      System.exit(2);
    }
  }


  // Graphics must contain setBlack() and setWhite() methods.
  public void addGraphicalTurn(ConsoleGraphics graphics) {
    this.graphics = graphics;
    graphicsExist = true;
    if (turn == 'b') {
      graphics.setBlack();
    } else if (turn == 'w') {
      graphics.setWhite();
    }
  }

  public boolean getPlayersTurn() {
    return playersTurn;
  }

  public void setPlayersTurn(boolean value){
    playersTurn = value;
  }

  public char getPlayersColor() {
    return playersColor;
  }

  public void changeTurn() {
    if (turn == 'w') {
      turn = 'b';
      if (graphicsExist) {
        graphics.setBlack();
      }
    } else {
      turn = 'w';
      if (graphicsExist) {
        graphics.setWhite();
      }
    }

    if (playersTurn) {
      playersTurn = false;  // Comment this out and start as white to test black/white
	  
	  //Obtain the user's move
	  String move = BoardPanel.playersMostRecentMove;
	  String fen = BoardPanel.playersFenAfterMove;
	  
	  //Update the 50 move rule counter by the one in the previous fen
	  String[] fenSections = fen.split(" ");
	  moveRule50Counter = Integer.parseInt(fenSections[4]);
	  
	  //Keep track of the last 6 moves
	  if(BoardPanel.previousMoves.size() < 6) {
		BoardPanel.previousMoves.add(move);
	  }
	  else {
		BoardPanel.previousMoves.add(move);
		BoardPanel.previousMoves.removeFirst();
	  }
	  
	  //Display when testing win/loss/draw condition starts
	  System.out.println("Starting tests for game results in changeTurn");
	  resultsOfGame = BoardPanel.my_rulebook.testGameEnded(fen);
	  if(!resultsOfGame.equals("noResult")) {
		GameResults result = new GameResults();
	  }
	  else {
		playMoveFromStockfish();
	  }
                            // turn switching
    } else {
      playersTurn = true;
    }
  }

  public void firstStockfishTurn(){
    playMoveFromStockfish();
  }

  public char getTurn() {
    return turn;
  }

   public void playMoveFromStockfish(){
	String fenBeforeMove = BoardPanel.my_storage.getFen();   
	int countPiecesBefore = 0;
	int countPiecesAfter = 0;
	
    String bestMove = LaboonChess.stockfish.getBestMove(BoardPanel.my_storage.getFen(), 1000);
    System.out.println("best mvoe from stockfish " + bestMove);
    LaboonChess.stockfish.movePiece(bestMove, BoardPanel.my_storage.getFen());
    String fen = LaboonChess.stockfish.getFen();
    System.out.println("New fen " + fen);
    BoardPanel.my_storage.setFen(fen);
    char old_x_board = bestMove.charAt(0);
    int old_x = (int)old_x_board - 97;
    int old_y = Integer.parseInt(bestMove.substring(1,2));
    old_y = 8-old_y;
    char x_board = bestMove.charAt(2);
    int x = (int) x_board - 97;
    int y = Integer.parseInt(bestMove.substring(3,4));
    y = 8 - y;
    BoardPanel.my_storage.movePiece(old_y, old_x, y, x);
    LaboonChess.stockfish.drawBoard();
    LaboonChess.changeTurn();

	System.out.println("Move Counter: " + moveRule50Counter);
	
	//Check if the move counter before stockfish makes it's move was 49
	//Indicates that one more move could lead to a draw
	//Stockfish's next move could initiate a draw
	if(moveRule50Counter == 49) {
		//Check if a piece was taken
		//Need to do this check since stockfish automatically resets the 50 move counter to 0 when it hits 50
		//Therefore we need to test it manually
		String[] fenSectionsBeforeMove = fen.split(" ");
		String fenBefore = fenSectionsBeforeMove[0];
		char temp;
		
		//Count number of pieces before stockfish makes a move
		for(int i = 0; i < fenBefore.length(); i++) {
			temp = fenBefore.charAt(i);
			if(Character.isLetter(temp)) {
				countPiecesBefore++;
			}
		}
		
		String fenAfterMove = BoardPanel.my_storage.getFen();
		String[] fenSectionsAfterMove = fen.split(" ");
		String fenAfter = fenSectionsAfterMove[0];
		
		//Count number of pieces after stockfish makes a move
		for(int i = 0; i < fenAfter.length(); i++) {
			temp = fenAfter.charAt(i);
			if(Character.isLetter(temp)) {
				countPiecesAfter++;
			}
		}
		
		System.out.println("Number of pieces before the move: " + countPiecesBefore);
		System.out.println("Number of pieces after the move: " + countPiecesAfter);
		
		//Indicates that stockfish made a move and didn't take piece, therefore it's a draw
		if(countPiecesBefore == countPiecesAfter) {
			
			resultsOfGame = "draw";
			System.out.println("Draw by 50 move rule");
			
			//Update board with move made by stockfish
			//This implementation avoids "non-static method cannot be referenced from static context error"
			BoardPanel tempBoardPanel = ConsoleGraphics.board;
			tempBoardPanel.setPieces();
			ConsoleGraphics.board = tempBoardPanel;
			
			GameResults result = new GameResults();
		}
	}
	//Update board with move made by stockfish
	//This implementation avoids "non-static method cannot be referenced from static context error"
	BoardPanel tempBoardPanel = ConsoleGraphics.board;
	tempBoardPanel.setPieces();
	ConsoleGraphics.board = tempBoardPanel;
	
	//Keep track of the last 6 moves

	if(BoardPanel.previousMoves.size() < 6) {
		BoardPanel.previousMoves.add(bestMove);
	}
	else {
		BoardPanel.previousMoves.add(bestMove);
		BoardPanel.previousMoves.removeFirst();
	}
	
	

	//Display when testing win/loss/draw condition starts
	System.out.println("Starting tests for game results in playMoveFromStockfish");
	resultsOfGame = BoardPanel.my_rulebook.testGameEnded(fen);
	if(!resultsOfGame.equals("noResult")) {
		GameResults result = new GameResults();
	}

  }

}
