/** 
* FILE NAME: TicTacToeGUI.java
* WHO: Cs230 Staff
* WHAT: Sets up the GUI for the TicTacToe game.
*/
import javax.swing.JFrame;

public class TicTacToeGUI {

  public static void main (String[] args) {
    // creates and shows a Frame 
    JFrame frame = new JFrame("Tic Tac Toe");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    //create an instance of the TicTacToe game
    TicTacToe game = new TicTacToe();
    
    //create a panel, and add it to the frame
    TicTacToePanel panel = new TicTacToePanel(game);
    frame.getContentPane().add(panel);
    
    frame.pack();
    frame.setVisible(true);
  }
}
