/** 
* FILE NAME: TicTacToe.java
* WHO:  CS230 staff
* WHAT: Implements the game of TicTacToe, without an interface. Just the functionality
* It is meant to work together with a GUI program.
*/
public class TicTacToe implements TicTacToeInterface {
  // instance variables 
  private int[][] cells; //array of ints, holding 0 or 1, or -1 
  //-1 indicates that the cell is empty
  
  private boolean isXTurn;  // Player X's turn or Player O's turn
  private int gameOver;  // Indicates if the game is over:
                            //0 if it is a tie, 1 if X won, 2 if O won and -1 if game is not over
  
  public TicTacToe () {
    isXTurn = true;
    gameOver = -1;  
    cells = new int[3][3];
    for (int i = 0; i < 3; i++) {
      for (int j=0; j < 3; j++)
 // all cells initialized to -1 as in not yet a marked space
        cells[i][j] = -1;
    }
  }
  /**
   * prints the array in a3x3 format. Used for testing purposes
   * during development
   */
  private void printArray() {
    for (int i = 0; i < 3; i++) {
      for (int j=0; j < 3; j++)
        System.out.print(cells[i][j] + "  ");
      System.out.print("\n");
    }
    System.out.println();
  }
  
  // val should be 0 or 1. 
  // Used for testing purposes during development
  private void setCell(int val, int i, int j) {
    cells[i][j] = val;
  }
  
/**
 * Plays one step of the game. Checks the cell(x,y) to see if it is
 * already marked. If so, it exits. Otherwise, it marks the cell(x,y)
 * with 1 or 0, depending on who player's turn this is.
 * After, it sets the other player as the current one, and checks to see
 * if the game is over.
 * @param x the x co-ordinate of the cell to mark.
 * @param y the y co-ordinate of the cell to mark.
 * 
 */ 
  public void takeAStep (int x, int y) {
    if (cells[x][y] != -1) return; //there is already something in that cell
      if (isXTurn) {  // Player X's move
        cells[x][y] = 1;
        isXTurn = false;
        System.out.println("Player O's turn");
      }
      else {  // Player O's move
        cells[x][y] = 0;
        isXTurn = true;
        System.out.println("Player X's turn");
      }
      gameOver = isGameOver();
  }
 
/** Checks if game is over either because one player has won or
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
  public int isGameOver() {
    // Check for row win
    for (int i=0; i<3; i++) {
      if ((cells[i][0] == 1) && (cells[i][1] == 1) && (cells[i][2] == 1)){
 //System.out.println("X row win in row "+i);
        return 1;
      }
      if ((cells[i][0] == 0) && (cells[i][1] == 0) && (cells[i][2] == 0)) {
 //System.out.println("0 row win in row "+i);
        return 2;
      }
    }
    // Check for column win
    for (int i=0; i<3; i++) {
      if ((cells[0][i] == 1) && (cells[1][i] == 1) && (cells[2][i] == 1)) {
 //System.out.println("X col win in col "+i);
        return 1;
      }
      if ((cells[0][i] == 0) && (cells[1][i] == 0) && (cells[2][i] == 0)) {
 //System.out.println("O col win in col "+i);
        return 2;
      }
    }
    // Check for forward diagonal win
    if ((cells[0][0] == 1) && (cells[1][1] == 1) && (cells[2][2] == 1)){
      //System.out.println("X forward diag win");
      return 1;
    }
    if ((cells[0][0] == 0) && (cells[1][1] == 0) && (cells[2][2] == 0)) {
      //System.out.println("O forward diag win");
      return 2;
    }
    
    // Check for backward diagonal win
    if ((cells[2][0] == 1) && (cells[1][1] == 1) && (cells[0][2] == 1)) {
      //System.out.println("X backward diag win");
      return 1;
    }
      if ((cells[2][0] == 0) && (cells[1][1] == 0) && (cells[0][2] == 0)) {
 //System.out.println("O backward diag win");
      return 2;
      }
    
    // Check if all the squares have been filled in
    int numFilledSquares = 0;
    for (int i=0; i<3; i++) {
      for (int j=0; j<3; j++) {
        if (cells[i][j] == 1 || cells[i][j] == 0) numFilledSquares++;
      }
    }
    if (numFilledSquares == 9){
      //System.out.println("The game is a tie");
      return 0;
    }
    return -1; //if this point is reached, the game is not over
  }
  
  /**
   * A getter for the instance var isXTurn(). 
   * (Used in the GUI part)
   * @return 1 if it is X player's turn, 
   * and 0 if it is the O's player turn
   */ 
  public boolean getIsXTurn() {
    return isXTurn;
  }

  /**
   * A getter for the instance var gameOver(). 
   * (Used in the GUI part)
   * @return -1 if the game is not over, or
   *          0 if it is over and it is a tie, or
   *          1 if playerX has won, or 
   *          2 if playerO has won
   */
  public int getGameOver() {
    return gameOver;
  }
  
  public static void main (String[] args) {
    TicTacToe game = new TicTacToe();
    
    //System.out.println("Game over: " + game.isGameOver());

    game.takeAStep(1,1); //1
    game.takeAStep(0,0); //0
    game.takeAStep(0,2); //1
    game.takeAStep(2,0); //0
    game.takeAStep(2,2); //1
    game.takeAStep(1,0); //0
    game.printArray();
    game.takeAStep(2,1); //1
    game.printArray();
  }
  
}
