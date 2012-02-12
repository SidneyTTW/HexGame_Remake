/**
 * 
 */
package VAST.HexGame.Game;

/**
 * Interface of things to calculate connections.
 * 
 * @author SidneyTTW
 * 
 */
public interface ConnectionCalculatorInterface {
  /**
   * @param balls
   *          The balls.
   * @param gameBoard
   *          The game board.
   * @param onlyStableBalls
   *          Only considerate the Stable and AlmostStable balls.
   * @return All the connections.
   */
  public ConnectionInterface calculateConnection(Ball[] balls,
      GameBoardInterface gameBoard, boolean onlyStableBalls);
  
  /**
   * The hint of the balls.
   * 
   * @param balls
   *          The balls.
   * @param gameBoard
   *          The game board.
   * @param onlyStableBalls
   *          Only considerate the Stable and AlmostStable balls.
   * @return The index of the key ball.
   */
  public int hint(Ball[] balls, GameBoardInterface gameBoard);
}
