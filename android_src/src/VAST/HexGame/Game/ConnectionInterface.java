/**
 * 
 */
package VAST.HexGame.Game;

import java.util.Vector;

/**
 * Interface to store the information of connections of a game.
 * 
 * @author SidneyTTW
 *
 */
public interface ConnectionInterface {
  public static final int ToLeftDown = 0;
  public static final int ToRightDown = 1;
  public static final int ToRight = 2;
  public static final int IsCenter = 3;
  public static final int IsLeftDown = 4;
  public static final int IsRightDown = 5;
  public static final int IsRight = 6;
  public static final int IsRightUp = 7;
  public static final int IsLeftUp = 8;
  public static final int IsLeft = 9;
  public static final int ConnectionPositionsCount = 10;
  
  /**
   * Get related chains of given ball.
   * 
   * @param index
   *          The given index of the ball.
   * @return The related chains.
   */
  public Vector<Integer>[] relatedChains(int index);
  
  /**
   * @param index
   *          The given index of the ball.
   * @return Whether the ball is in a chain.
   */
  public boolean isInAChain(int index);
  
  /**
   * @param index
   *          The given index of the ball.
   * @return The number of hte chains related.
   */
  public int relatedChainCount(int index);
  
  /**
   * @return All the chains detected.
   */
  public Vector<Vector<Integer> > allChains();
  
  /**
   * Add a chain.
   * 
   * @param index
   *          The given index.
   * @param direction
   *          The given direction.
   * @param chain
   *          The given chain.
   */
  public void addChain(int index, int direction, Vector<Integer> chain);
}
