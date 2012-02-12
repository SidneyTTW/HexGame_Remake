/**
 * 
 */
package VAST.HexGame.Game;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class realizes the function to fill balls.
 * 
 * @author SidneyTTW
 * 
 */
public class StandardBallFiller implements BallFillerInterface {
  private static java.util.Random r = new java.util.Random(); 
  
  /**
   * The rule of the game.
   */
  GameRuleInterface rule;

  /**
   * The max number of locks.
   */
  int maxLockNumber;

  /**
   * Constructor.
   * 
   * @param rule
   *          The rule of the game.
   * @param maxLockNumber
   *          The max number of locks.
   */
  public StandardBallFiller(GameRuleInterface rule, int maxLockNumber) {
    this.rule = rule;
    this.maxLockNumber = maxLockNumber;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * VAST.HexGame.Game.BallFillerInterface#fillBalls(VAST.HexGame.Game.Ball[])
   */
  @Override
  public boolean fillBalls(Ball[] balls) {
    GameBoardInterface gameBoard = rule.getGameBoard();
    ConnectionCalculatorInterface connectionCalculator = rule.getConnectionCalculator();
    
    // Get the indexes which are blank and count the locks
    ArrayList <Integer> blankIndexes = new ArrayList <Integer>();
    int lockCount = 0;
    for (int i = 0;i < gameBoard.totalBallCount();++i) {
      if (balls[i] == null)
        blankIndexes.add(i);
      else if (balls[i].isLocked())
        ++lockCount;
    }
    
    if (blankIndexes.isEmpty())
      return false;
    

    int thisMaxLockNumber = maxLockNumber - lockCount;
    if (thisMaxLockNumber < 0)
      thisMaxLockNumber = 0;
    double lockRate = 1.0 * thisMaxLockNumber / blankIndexes.size();

    do
    {
      // Record the number of locks at this time
      int thisLockCount = 0;
      // Unlock the balls
      Iterator<Integer> itr = blankIndexes.iterator();
      while(itr.hasNext()) {
        int index = itr.next();
        if (balls[index] != null)
          balls[index].setLocked(false);
      } 

      // Create the balls
      itr = blankIndexes.iterator();
      while(itr.hasNext()) {
        int index = itr.next();
        // Set the color of the ball and whether it's locked
        if (balls[index] == null)
          balls[index] = new Ball(r.nextInt() % Ball.MAX_VALID_NUMBER_COUNT);
        else
          balls[index].setColor(r.nextInt() % Ball.MAX_VALID_NUMBER_COUNT);
        if (thisLockCount < thisMaxLockNumber)
        {
          boolean setToLock = (r.nextInt() % 100) < lockRate * 100;
          balls[index].setLocked(setToLock);
          if (setToLock)
            ++thisLockCount;
        }
      }
    } while (rule.endlessFill() && connectionCalculator.hint(balls, gameBoard) < 0);
    return true;
  }
}
