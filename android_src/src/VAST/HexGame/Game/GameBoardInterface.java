/**
 * 
 */
package VAST.HexGame.Game;

import java.util.Vector;

import Aid.MyPoint;

/**
 * Interface of a game board, have the ability to tell detailed information of a
 * game board itself.
 * 
 * @author SidneyTTW
 * 
 */
public interface GameBoardInterface {
  public static final int LEFT_DOWN = 0;
  public static final int RIGHT_DOWN = 1;
  public static final int RIGHT = 2;
  public static final int RIGHT_UP = 3;
  public static final int LEFT_UP = 4;
  public static final int LEFT = 5;

  /**
   * @return The width of the game board.
   */
  public int getWidth();

  /**
   * @return The height of the game board.
   */
  public int getHeight();

  /**
   * @return The radius of the balls on the game board.
   */
  public int getBallRadius();

  /**
   * @param position
   *          The given logical position.
   * @return The index of the ball. -1 means no ball at given position.
   */
  public int ballIndexAtLogicalPosition(MyPoint position);

  /**
   * @param index
   *          The given index of the ball.
   * @return The logical position. Point() means no such index.
   */
  public MyPoint ballLogicalPositionOfIndex(int index);

  /**
   * @param index
   *          The given index of the ball.
   * @return The index of the left down ball. -1 means no ball at given
   *         direction.
   */
  public int leftDownIndex(int index);

  /**
   * @param index
   *          The given index of the ball.
   * @return The index of the right down ball. -1 means no ball at given
   *         direction.
   */
  public int rightDownIndex(int index);

  /**
   * @param index
   *          The given index of the ball.
   * @return The index of the right ball. -1 means no ball at given direction.
   */
  public int rightIndex(int index);

  /**
   * @param index
   *          The given index of the ball.
   * @return The index of the right up ball. -1 means no ball at given
   *         direction.
   */
  public int rightUpIndex(int index);

  /**
   * @param index
   *          The given index of the ball.
   * @return The index of the left up ball. -1 means no ball at given direction.
   */
  public int leftUpIndex(int index);

  /**
   * @param index
   *          The given index of the ball.
   * @return The index of the left ball. -1 means no ball at given direction.
   */
  public int leftIndex(int index);

  /**
   * @param index
   *          The given index of the ball.
   * @param direction
   *          The given direction.
   * @return The index of the ball at given direction of given ball. -1 means no
   *         ball at given direction.
   */
  public int nearbyIndex(int index, int direction);

  /**
   * @return All the fill in chains.
   */
  public Vector<Vector<Integer>> chains();

  /**
   * @param index
   *          The given index of the ball.
   * @return Chain around given ball.
   */
  public Vector<Integer> chainAroundIndex(int index);

  /**
   * @param index
   *          The given index of the ball.
   * @return Whether the given ball can be the center of the rotation.
   */
  public boolean canBeRotateCenter(int index);

  /**
   * @param index
   *          The given index of the ball.
   * @return Whether the given ball is a joint while filling in.
   */
  public boolean isJoint(int index);

  /**
   * @return The center position of the game board.
   */
  public MyPoint centerPosition();

  /**
   * @return Total ball count.
   */
  public int totalBallCount();

  /**
   * @return Interval between two layers.
   */
  public int intervalBetweenTwoLayers();
}
