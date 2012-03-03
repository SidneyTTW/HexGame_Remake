/**
 * 
 */
package VAST.HexGame.Game;

import java.util.Vector;

/**
 * An abstract class of standard connection calculator, left hint to be
 * realized.
 * 
 * @author VencentQiuuu and SidneyTTW
 * 
 */
public abstract class AbstractStandardConnectionCalculator implements
    ConnectionCalculatorInterface {

  /*
   * (non-Javadoc)
   * 
   * @see
   * VAST.HexGame.Game.ConnectionCalculatorInterface#calculateConnection(VAST
   * .HexGame.Game.Ball[], VAST.HexGame.Game.GameBoardInterface, boolean)
   */
  /**
   * @author SidneyTTW
   */
  @Override
  public ConnectionInterface calculateConnection(Ball[] balls,
      GameBoardInterface gameBoard, boolean onlyStableBalls) {
    int totalBallCount = balls.length;
    ConnectionInterface result = new StandardConnection(totalBallCount);
    for (int i = 0; i < totalBallCount; ++i) {
      // If the ball doesn't exist, go to next ball
      if (balls[i] == null)
        continue;

      // Test 3 in a line
      Ball firstBall = balls[i];
      for (int j = 0; j < 3; ++j) {
        if (result.relatedChains(i)[j] != null)
          continue;
        Vector<Integer> currentConnection = new Vector<Integer>();
        int currentIndex = i;

        // A complex condition to judge whether the ball can
        // be in a connection and whether the ball has the
        // same color with the first ball
        while (currentIndex != -1
            && balls[currentIndex] != null
            && firstBall.sameColor(balls[currentIndex])
            && ((!onlyStableBalls) || ((balls[currentIndex].getState() == Ball.State.Stable) || (balls[currentIndex]
                .getState() == Ball.State.AlmostStable)))) {
          currentConnection.add(currentIndex);
          currentIndex = gameBoard.nearbyIndex(currentIndex, j);
        }

        if (currentConnection.size() >= 3)
          result.addChain(i, j, currentConnection);
      }

      // Test 6 in a circle
      if (gameBoard.canBeRotateCenter(i)) {
        boolean chainCanBeEliminated = true;
        Vector<Integer> chain = gameBoard.chainAroundIndex(i);
        if (chain.size() == 6) {
          if (balls[chain.elementAt(0)] != null) {
            firstBall = balls[chain.elementAt(0)];
            for (int j = 0; j < 6; ++j) {
              int currentIndex = chain.elementAt(j);
              // A complex condition to judge whether the
              // ball can be in a connection and whether the
              // ball has the same color with the first ball
              if (balls[currentIndex] == null
                  || (!firstBall.sameColor(balls[currentIndex]))
                  || (!((!onlyStableBalls) || ((balls[currentIndex].getState() == Ball.State.Stable) || (balls[chain
                      .elementAt(j)].getState() == Ball.State.AlmostStable))))) {
                chainCanBeEliminated = false;
                break;
              }
            }
          } else
            chainCanBeEliminated = false;
        } else
          chainCanBeEliminated = false;
        if (chainCanBeEliminated) {
          // Record the chain
          result.addChain(i, ConnectionInterface.IsCenter, new Vector<Integer>(
              chain));
        }
      }
    }
    return result;
  }

  /**
   * @author VencentQiuuu
   * 
   *         Part of the "hint". Used to Check while a move is valid.
   * 
   * @param copiedBalls
   *          The copy of the balls.
   * @param gameBoard
   *          The game board.
   * @param tryingIndex
   *          The index of the ball trying.
   */
  protected boolean check(Ball[] copiedBalls, GameBoardInterface gameBoard,
      int tryingIndex) {
    for (int i = 0; i < 3; ++i) {
      int lenOfChain = 1;
      for (int j = 0; j < 2; ++j) {
        int movingIndex = tryingIndex;
        while (true) {
          int nextIndex = gameBoard.nearbyIndex(movingIndex, i + 3 * j);
          if (nextIndex == -1 || copiedBalls[nextIndex] == null
              || (!copiedBalls[tryingIndex].sameColor(copiedBalls[nextIndex])))
            break;
          ++lenOfChain;
          movingIndex = nextIndex;
        }
      }
      if (lenOfChain >= 3)
        return true;
    }

    int[] centers = new int[6];
    for (int i = 0; i < 6; ++i) {
      centers[i] = gameBoard.nearbyIndex(tryingIndex, i);
      int firstNearbyIndex = gameBoard.nearbyIndex(centers[i], 0);
      if (firstNearbyIndex >= 0 && copiedBalls[firstNearbyIndex] != null) {
        boolean result = true;
        for (int j = 1; j < 6; ++j) {
          int nearbyIndex = gameBoard.nearbyIndex(centers[i], j);
          if (nearbyIndex < 0
              || copiedBalls[nearbyIndex] == null
              || (!copiedBalls[nearbyIndex]
                  .sameColor(copiedBalls[firstNearbyIndex]))) {
            result = false;
            break;
          }
        }
        if (result)
          return true;
      }
    }
    return false;
  }
}
