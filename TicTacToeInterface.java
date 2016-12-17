/**
 * CS230 Staff
 *
 **/

interface TicTacToeInterface {
  /**
 * Plays one step of the game. Checks the cell(x,y) to see if it is
 * already marked. If so, it exits. Otherwise, it marks the cell(x,y)
 * according to the player's turn.
 * Updates the player's turn, and checks to see
 * if the game is over.
 * @param x the x co-ordinate of the playing cell .
 * @param y the y co-ordinate of the playing cell.
 * 
 */ 
  public void takeAStep (int x, int y);

  
  /** 
  * Checks if game is over.
  * A game is over either because one player has won or
  * because all squares have been filled in.
  * returns: 0 if game is over and it is a tie
  *          1 if game is over and playerX wins
  *          2 if game is over and playerO wins
  *         -1 if the game is not over
  * @return -1 if the game is not over, or
  *          0 if it is over and it is a tie, or
  *          1 if playerX has won, or 
  *          2 if playerO has won
  */
  public int isGameOver();
  
  
  
  /**
   * returns true if it is X's turn to play,
   * false otherwise.
   * */
  public boolean getIsXTurn();
  
  
}
