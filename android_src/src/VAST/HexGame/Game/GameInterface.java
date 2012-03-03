/**
 * 
 */
package VAST.HexGame.Game;

/**
 * Interface of a game.
 * 
 * @author SidneyTTW
 *
 */
public interface GameInterface {
  /**
   * Called when stable connection is tested.
   * 
   * @param connections
   *          The connections.
   */
  public void stableConnectionTested(ConnectionInterface connections);
  
  /**
   * Called when user moving connection is tested.
   * 
   * @param connections
   *          The connections.
   */
  public void userMovingConnectionTested(ConnectionInterface connections);
  
  /**
   * Called when some balls are eliminated.
   * 
   * @param count
   *          The number of balls eliminated.
   */
  public void eliminated(int count);
  
  /**
   * Called when a good move is made.
   */
  public void goodMove();
  
  /**
   * Called when a bad move is made.
   */
  public void badMove();
}
