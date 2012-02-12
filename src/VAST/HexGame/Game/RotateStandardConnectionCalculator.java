/**
 * 
 */
package VAST.HexGame.Game;

/**
 * Class to calculate the hint of rotate gesture.
 * 
 * @author VencentQiuuu in c++, SidneyTTW turned it into Java
 *
 */
public class RotateStandardConnectionCalculator extends
    AbstractStandardConnectionCalculator {
  private static java.util.Random r = new java.util.Random();

  /* (non-Javadoc)
   * @see VAST.HexGame.Game.ConnectionCalculatorInterface#hint(VAST.HexGame.Game.Ball[], VAST.HexGame.Game.GameBoardInterface)
   */
  @Override
  public int hint(Ball[] balls, GameBoardInterface gameBoard) {
    // Code made by VincentQiuuu!!!
    int totalBallCount = gameBoard.totalBallCount();

    // A copy of the balls
    Ball [] copiedBalls = new Ball[totalBallCount];
    for (int i = 0;i < totalBallCount;++i)
      copiedBalls[i] = balls[i];

    // Whether the balls have been tried
    boolean [] triedIndexes = new boolean[totalBallCount];
    for (int i = 0;i < totalBallCount;++i)
      triedIndexes[i] = false;

    // Try the balls one by one according to a random order
    for (int i = 0;i < totalBallCount;++i)
    {
      int randIndex = r.nextInt() % (totalBallCount - i);
      int p = -1;
      for (int j = 0;j < randIndex + 1;++j)
      {
        ++p;
        while (triedIndexes[p])
          ++p;
      }
      int tryingIndex = p;
      triedIndexes[tryingIndex] = true;

      // Test rotate
      if (gameBoard.canBeRotateCenter(tryingIndex))
      {
        // The chain around the ball
        int [] chain = new int[6];
        for (int j = 0;j < 6;++j)
          chain[j] = gameBoard.nearbyIndex(tryingIndex, j);
        // Calculate whether all the balls exist and not
        // locked
        boolean allOccupied = true;
        for (int j = 0;j < 6;++j)
        {
          if (chain[j] == -1 ||
              copiedBalls[chain[j]] == null ||
              copiedBalls[chain[j]].isLocked())
          {
            allOccupied = false;
            break;
          }
        }
        if (allOccupied)
        {
          // For each offset
          for (int j = 0;j < 6;++j)
          {
            Ball ball1 = copiedBalls[chain[0]];
            // Set the balls and check
            for (int k = 0;k < 5;++k)
              copiedBalls[chain[k]] =
                  copiedBalls[chain[k+1]];
            copiedBalls[chain[5]] = ball1;
            if (j != 6)
              for (int k = 0;k < 6;++k)
                if (check(copiedBalls, gameBoard, chain[k]))
                  return tryingIndex;
          }
        }
      }
    }
    
    return -1;
  }

}
