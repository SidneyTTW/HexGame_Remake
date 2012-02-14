/**
 * 
 */
package VAST.HexGame.Game;

import java.awt.Point;
import java.util.Vector;

/**
 * Class of balls.
 * 
 * @author SidneyTTW
 * 
 */
public class Ball {
  public static final int MAX_VALID_NUMBER_COUNT = 6;
  public static final int Red = 0;
  public static final int Blue = 1;
  public static final int Green = 2;
  public static final int Yellow = 3;
  public static final int Purple = 4;
  public static final int White = 5;
  public static final int BadColor = 6;
  
  public Ball(int color) {
    this.color = color;
  }

  enum State {
    Stable, AlmostStable, UserMoving, UserReleased, SystemMoving, JustCreated
  };

  /**
   * The color of the ball.
   */
  private int color = Red;

  /**
   * The state of the ball.
   */
  private State state = State.JustCreated;

  /**
   * The position of the ball.
   */
  private Point position = new Point(0, 0);

  /**
   * Whether the ball is locked.
   */
  private boolean locked = false;

  /**
   * The positions to be stopped later.
   */
  private Vector<Point> stopPositions = new Vector<Point>();

  /**
   * @return The color.
   */
  public int getColor() {
    return color;
  }

  /**
   * @param color
   *          The color to set.
   */
  public void setColor(int color) {
    this.color = color;
  }

  /**
   * @return The state.
   */
  public State getState() {
    return state;
  }

  /**
   * @param state
   *          The state to set.
   */
  public void setState(State state) {
    this.state = state;
  }

  /**
   * @return The position.
   */
  public Point getPosition() {
    return position;
  }

  /**
   * @param position
   *          The position to set.
   */
  public void setPosition(Point position) {
    this.position = position;
  }

  /**
   * @return Whether the ball is locked.
   */
  public boolean isLocked() {
    return locked;
  }

  /**
   * @param locked
   *          Whether the ball is locked.
   */
  public void setLocked(boolean locked) {
    this.locked = locked;
  }

  /**
   * @return The positions to be stopped later.
   */
  public Vector<Point> getStopPositions() {
    return stopPositions;
  }

  /**
   * @param stopPositions
   *          The positions to be stopped later.
   */
  public void setStopPositions(Vector<Point> stopPositions) {
    this.stopPositions = stopPositions;
  }

  /**
   * @param stopPositions
   *          The positions to be stopped later.
   */
  public boolean sameColor(Ball anotherBall) {
    return color != BadColor && anotherBall != null
        && color == anotherBall.color;
  }

  /**
   * Advance the ball to the next position. May change the state of the ball.
   * 
   * @return Whether the state is changed.
   */
  public boolean advance() {
    State lastState = state;

    // If there is a position to stop
    if (!stopPositions.isEmpty()) {
      // Change the state
      state = State.SystemMoving;

      // Change the position
      position = stopPositions.elementAt(stopPositions.size() - 1);

      // Delete the position in the vector
      stopPositions.remove(stopPositions.size() - 1);

      // Currently not used, may be used later to speed up
      // if (stopPositions.size() < 2)
      // state = State.AlmostStable;
      
      // Change the state
      if (stopPositions.size() == 0)
        state = State.Stable;
    } else if (state == State.SystemMoving)
      state = State.Stable;

    return state != lastState;
  }

  /**
   * Move the ball to the last position and change the state to stable.
   */
  public void advanceToStatblePosition() {
    if (!stopPositions.isEmpty()) {
      position = stopPositions.elementAt(0);
      stopPositions.clear();
    }
    state = State.Stable;
  }

}
