/**
 * 
 */
package VAST.HexGame.GameWidget;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import VAST.HexGame.Game.Ball;
import VAST.HexGame.Game.BallFillerInterface;
import VAST.HexGame.Game.BasicPainter;
import VAST.HexGame.Game.ConnectionCalculatorInterface;
import VAST.HexGame.Game.ConnectionInterface;
import VAST.HexGame.Game.CoreControllerInterface;
import VAST.HexGame.Game.GameBoardInterface;
import VAST.HexGame.Game.GameEffectAdapter;
import VAST.HexGame.Game.GameInterface;
import VAST.HexGame.Game.GestureControllerInterface;
import VAST.HexGame.Game.StandardGameRule;
import VAST.HexGame.Widgets.AbstractSimpleWidget;

/**
 * Abstract class of a standard game
 * 
 * @author SidneyTTW
 *
 */
public class AbstractStandardGameWidget extends AbstractSimpleWidget implements
    GameInterface {
  public static final int ADVANCE_INTERVAL = 60;
  public static enum StandardGesture {Swap, Rotate};
  
  protected Ball [] balls;
  protected StandardGameRule rule;
  protected ConnectionCalculatorInterface connectionCalculator;
  protected BallFillerInterface ballFiller;
  protected GameBoardInterface gameBoard;
  protected GestureControllerInterface gestureController;
  protected CoreControllerInterface coreController;
  protected GameEffectAdapter gameEffectAdapter;
  protected Timer advanceTimer = null;
  
  /**
   * @author SidneyTTW
   *
   * @brief The timer task to do the advance.
   */
  public class AdvanceTimerTask extends TimerTask {
    public void run() {
      advance();
    }
  }

  /* (non-Javadoc)
   * @see VAST.HexGame.Widgets.WidgetInterface#getFocus()
   */
  @Override
  public void getFocus() {
    if (advanceTimer == null) {
      AdvanceTimerTask task = new AdvanceTimerTask();
      advanceTimer = new Timer();
      advanceTimer.schedule(task, new Date(), advanceInterval()); 
    }
  }

  /* (non-Javadoc)
   * @see VAST.HexGame.Game.GameInterface#stableConnectionTested(VAST.HexGame.Game.ConnectionInterface)
   */
  @Override
  public void stableConnectionTested(ConnectionInterface connections) {
    for (int i = 0;i < gameBoard.totalBallCount();++i)
      if (connections.isInAChain(i))
        gameEffectAdapter.highlightAt(gameBoard, i);
  }

  /* (non-Javadoc)
   * @see VAST.HexGame.Game.GameInterface#userMovingConnectionTested(VAST.HexGame.Game.ConnectionInterface)
   */
  @Override
  public void userMovingConnectionTested(ConnectionInterface connections) {
    gameEffectAdapter.clearUserMovingEliminationHints();
    for (int i = 0;i < gameBoard.totalBallCount();++i)
      if (connections.isInAChain(i))
        gameEffectAdapter.userMovingEliminationHintAt(gameBoard, i);
  }

  /* (non-Javadoc)
   * @see VAST.HexGame.Game.GameInterface#goodMove()
   */
  @Override
  public void goodMove() {
    // TODO will be finished after finishing the statistic
  }

  /* (non-Javadoc)
   * @see VAST.HexGame.Game.GameInterface#badMove()
   */
  @Override
  public void badMove() {
    // TODO will be finished after finishing the statistic
  }
  @Override
  public void mousePressed(Point logicalPos, int button, int mouseId) {
    super.mousePressed(logicalPos, button, mouseId);
    if (gestureController != null)
      gestureController.pressAt(logicalPos, button, mouseId);
  }
  
  @Override
  public void mouseDragged(Point logicalPos, int button, int mouseId) {
    super.mouseDragged(logicalPos, button, mouseId);
    if (gestureController != null)
      gestureController.dragAt(logicalPos, button, mouseId);
  }
  
  @Override
  public void mouseReleased(Point logicalPos, int button, int mouseId) {
    super.mouseReleased(logicalPos, button, mouseId);
    if (gestureController != null)
      gestureController.releaseAt(logicalPos, button, mouseId);
  }
  
  @Override
  public void paint(Graphics g) {
    BasicPainter.paintBackGround(BasicPainter.Game37, g, width(), height(), 0);
    super.paint(g);
    BasicPainter.paintBasicBalls(rule.getGameBoard(), balls, g, frame);
    if (gameEffectAdapter != null)
      gameEffectAdapter.paint(g);
  }

  @Override
  public void eliminated(int count) {}

  @Override
  public void buttonClicked(int indexOfTheButton) {}
  
  /**
   * Advance the game
   */
  public void advance() {
    if (gestureController != null)
      gestureController.advance();
    if (gameEffectAdapter != null)
      gameEffectAdapter.advance();
    if (coreController != null)
      coreController.advance();
  }

  /**
   * @return The interval of the advance.
   */
  public int advanceInterval() {
    return ADVANCE_INTERVAL;
  }
}
