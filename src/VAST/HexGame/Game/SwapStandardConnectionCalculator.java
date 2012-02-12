/**
 * 
 */
package VAST.HexGame.Game;

/**
 * Class to calculate the hint of swap gesture.
 * 
 * @author VencentQiuuu in c++, SidneyTTW turned it into Java
 * 
 */
public class SwapStandardConnectionCalculator extends
    AbstractStandardConnectionCalculator {
  private static java.util.Random r = new java.util.Random();

  /*
   * (non-Javadoc)
   * 
   * @see
   * VAST.HexGame.Game.ConnectionCalculatorInterface#hint(VAST.HexGame.Game.
   * Ball[], VAST.HexGame.Game.GameBoardInterface)
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

      // Test swap
      if (copiedBalls[tryingIndex] != null &&
           (!copiedBalls[tryingIndex].isLocked()))
      {
        // The balls in the 3 directions
        int [] swapping = new int[3];
        swapping[0] = gameBoard.leftDownIndex(tryingIndex);
        swapping[1] = gameBoard.rightDownIndex(tryingIndex);
        swapping[2] = gameBoard.rightIndex(tryingIndex);
        // For each direction
        for (int k = 0;k < 3;++k)
        {
          if ( swapping[k] != -1 &&
               copiedBalls[swapping[k]] != null &&
               (!copiedBalls[swapping[k]].isLocked()))
          {
            // Set the balls and check
            Ball t = copiedBalls[tryingIndex];
            copiedBalls[tryingIndex] = copiedBalls[swapping[k]];
            copiedBalls[swapping[k]] = t;
            if (check(copiedBalls, gameBoard, tryingIndex))
              return swapping[k];
            if (check(copiedBalls, gameBoard, swapping[k]))
              return tryingIndex;
            t = copiedBalls[tryingIndex];
            copiedBalls[tryingIndex] = copiedBalls[swapping[k]];
            copiedBalls[swapping[k]] = t;
          }
        }
      }
    }
    
    return -1;
  }
}
