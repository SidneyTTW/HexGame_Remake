/**
 * 
 */
package VAST.HexGame.Game;

import java.awt.Point;
import java.util.Vector;

import AidPackage.MyPoint;

/**
 * @author SidneyTTW
 * 
 */
public interface CoreControllerInterface {
  /**
   * Set the balls.
   * 
   * @param balls
   *          The balls.
   */
  public void setBalls(Ball[] balls);

  /**
   * @return The balls.
   */
  public Ball[] getBalls();

  /**
   * Advance the simulator.
   * 
   * @return Whether anything have been changed.
   */
  public boolean advance();

  /**
   * Eliminate the balls with given indexes.
   * 
   * @param indexes
   *          Given indexes.
   */
  public void eliminate(Vector<Integer> indexes);

  /**
   * Rotate a ball to the destination according to some settings.
   * 
   * @param ball
   *          The ball.
   * @param myPoint
   *          The destination.
   * @param centerPosition
   *          The center of the rotation.
   * @param steps
   *          The steps used to reach the destination.
   * @param forceFillDirection
   *          Whether to use the fill in direction.
   * @param distance
   *          The distance between the ball and the center in count of layer.
   */
  public void rotateABallTo(Ball ball, MyPoint myPoint, MyPoint centerPosition, int steps,
      boolean forceFillDirection, int distance);
  
  /**
   * Translate a ball to a position.
   * 
   * @param ball
   *          The ball.
   * @param toPos
   *          The destination.
   * @param steps
   *          The steps the animation takes.
   * @param plain
   *          Whether the ball move directly to the position.
   */
  void translateABallTo(Ball ball, MyPoint toPos, int steps, boolean plain);
}
