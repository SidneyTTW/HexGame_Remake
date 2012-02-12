/**
 * 
 */
package VAST.HexGame.Game;

/**
 * Interface of a ball filler.
 * 
 * @author SidneyTTW
 * 
 */
public interface BallFillerInterface {
  /**
   * Fill the balls according to given policy.
   * 
   * @param balls
   *          The given balls.
   * @return Whether any ball has been added.
   */
  public boolean fillBalls(Ball[] balls);
}
