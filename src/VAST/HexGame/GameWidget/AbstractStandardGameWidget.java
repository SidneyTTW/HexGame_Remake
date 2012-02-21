/**
 * 
 */
package VAST.HexGame.GameWidget;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import AidPackage.MathAid;
import AidPackage.MyGraphics;
import AidPackage.MyPoint;
import AidPackage.SoundController;
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
import VAST.HexGame.Game.Statistics;
import VAST.HexGame.GameItem.RectMaskItem;
import VAST.HexGame.GameItem.StandardGameButtonItem;
import VAST.HexGame.Widgets.AbstractSimpleWidget;
import VAST.HexGame.Widgets.ItemInterface;
import VAST.HexGame.Widgets.RectButtonItem;
import VAST.HexGame.Widgets.RectItem;

/**
 * Abstract class of a standard game
 * 
 * @author SidneyTTW
 * 
 */
public abstract class AbstractStandardGameWidget extends AbstractSimpleWidget
    implements GameInterface {

  public static final int ADVANCE_INTERVAL = 60;

  public static enum StandardGesture {
    Swap, Rotate
  };

  protected Ball[] balls = null;
  protected StandardGameRule rule;
  protected ConnectionCalculatorInterface connectionCalculator;
  protected BallFillerInterface ballFiller;
  protected GameBoardInterface gameBoard;
  protected GestureControllerInterface gestureController;
  protected CoreControllerInterface coreController;
  protected GameEffectAdapter gameEffectAdapter;
  protected Timer advanceTimer = null;

  protected StandardGesture gesture;

  protected static final int RESET_ANIM_MAX = 5;
  private static final double[] ANIM_PERCENTAGE = { 1, 0.85, 0.425, 0.25, 0.05,
      0 };
  private static final MyPoint resetBackgroundFrom = new MyPoint(
      (int) (AbstractSimpleWidget.SIMPLE_WIDGET_WIDTH * 0.5), -100);
  private static final MyPoint resetBackgroundTo = new MyPoint(
      (int) (AbstractSimpleWidget.SIMPLE_WIDGET_WIDTH * 0.5),
      (int) (AbstractSimpleWidget.SIMPLE_WIDGET_HEIGHT * 0.5));
  private static final MyPoint confirmFrom = new MyPoint(-100,
      (int) (AbstractSimpleWidget.SIMPLE_WIDGET_HEIGHT * 0.6));
  private static final MyPoint confirmTo = new MyPoint(
      (int) (AbstractSimpleWidget.SIMPLE_WIDGET_WIDTH * 0.4),
      (int) (AbstractSimpleWidget.SIMPLE_WIDGET_HEIGHT * 0.6));

  private static final MyPoint cancelFrom = new MyPoint(
      AbstractSimpleWidget.SIMPLE_WIDGET_WIDTH + 100,
      (int) (AbstractSimpleWidget.SIMPLE_WIDGET_HEIGHT * 0.6));
  private static final MyPoint cancelTo = new MyPoint(
      (int) (AbstractSimpleWidget.SIMPLE_WIDGET_WIDTH * 0.6),
      (int) (AbstractSimpleWidget.SIMPLE_WIDGET_HEIGHT * 0.6));
  private static final int ResetConfirmButton = 0;
  private static final int ResetCancelButton = 1;
  protected static final int ResetMaskButton = 2;
  private static final int ResetButton = 3;
  private static final int ExitButton = 4;

  public static final int BUILT_IN_BUTTON_COUNT = 5;
  public static final int BUILT_IN_BONUS_ITEM_COUNT = 0;

  protected StandardGameButtonItem resetButton;
  protected StandardGameButtonItem exitButton;

  protected int reseting = -1;

  protected ItemInterface resetMask;
  protected ItemInterface resetBackgound = null;
  protected ItemInterface resetConfirmButton;
  protected ItemInterface resetCancelButton;

  protected void standardInit() {

    if (resetBackgound == null)
      resetBackgound = new RectItem();
    resetBackgound.setVisible(false);
    addItem(resetBackgound, AbstractSimpleWidget.ItemType.SimpleItem);

    resetConfirmButton = new StandardGameButtonItem();
    resetConfirmButton.setVisible(false);
    resetConfirmButton.setEnabled(false);
    ((RectButtonItem) resetConfirmButton).setText("Confirm");
    addItem(resetConfirmButton, AbstractSimpleWidget.ItemType.ButtonItem);

    resetCancelButton = new StandardGameButtonItem();
    resetCancelButton.setVisible(false);
    resetCancelButton.setEnabled(false);
    ((RectButtonItem) resetCancelButton).setText("Cancel");
    addItem(resetCancelButton, AbstractSimpleWidget.ItemType.ButtonItem);

    resetMask = new RectMaskItem();
    resetMask.setVisible(false);
    resetMask.setEnabled(false);
    resetMask.setLogicalPosition(new MyPoint(
        (int) (AbstractSimpleWidget.SIMPLE_WIDGET_WIDTH * 0.5),
        (int) (AbstractSimpleWidget.SIMPLE_WIDGET_HEIGHT * 0.5)));
    ((RectItem) resetMask).setWidth(AbstractSimpleWidget.SIMPLE_WIDGET_WIDTH);
    ((RectItem) resetMask).setHeight(AbstractSimpleWidget.SIMPLE_WIDGET_HEIGHT);
    addItem(resetMask, AbstractSimpleWidget.ItemType.ButtonItem);

    resetButton = new StandardGameButtonItem();
    resetButton.setText("Reset");
    resetButton.setLogicalPosition(new MyPoint((int) (width() * 0.1),
        (int) (height() * 0.8)));
    addItem(resetButton, AbstractSimpleWidget.ItemType.ButtonItem);

    exitButton = new StandardGameButtonItem();
    exitButton.setText("Exit");
    exitButton.setLogicalPosition(new MyPoint((int) (width() * 0.1),
        (int) (height() * 0.9)));
    addItem(exitButton, AbstractSimpleWidget.ItemType.ButtonItem);
  }

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

  /*
   * (non-Javadoc)
   * 
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * VAST.HexGame.Game.GameInterface#stableConnectionTested(VAST.HexGame.Game
   * .ConnectionInterface)
   */
  @Override
  public void stableConnectionTested(ConnectionInterface connections) {
    if (gameEffectAdapter == null)
      return;
    for (int i = 0; i < gameBoard.totalBallCount(); ++i)
      if (connections.isInAChain(i))
        gameEffectAdapter.highlightAt(gameBoard, i);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * VAST.HexGame.Game.GameInterface#userMovingConnectionTested(VAST.HexGame
   * .Game.ConnectionInterface)
   */
  @Override
  public void userMovingConnectionTested(ConnectionInterface connections) {
    if (gameEffectAdapter == null)
      return;
    gameEffectAdapter.clearUserMovingEliminationHints();
    for (int i = 0; i < gameBoard.totalBallCount(); ++i)
      if (connections.isInAChain(i))
        gameEffectAdapter.userMovingEliminationHintAt(gameBoard, i);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameInterface#goodMove()
   */
  @Override
  public void goodMove() {
    SoundController.addSound(SoundController.GoodMove);
    Statistics.addStatistic(Statistics.GoodMoveCount, 1);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameInterface#badMove()
   */
  @Override
  public void badMove() {
    SoundController.addSound(SoundController.BadMove);
    Statistics.addStatistic(Statistics.BadMoveCount, 1);
  }

  @Override
  public void mousePressed(MyPoint logicalPos, int button, int mouseId) {
    super.mousePressed(logicalPos, button, mouseId);
    if (gestureController != null && reseting < 0)
      gestureController.pressAt(logicalPos, button, mouseId);
  }

  @Override
  public void mouseDragged(MyPoint logicalPos, int button, int mouseId) {
    super.mouseDragged(logicalPos, button, mouseId);
    if (gestureController != null && reseting < 0)
      gestureController.dragAt(logicalPos, button, mouseId);
  }

  @Override
  public void mouseReleased(MyPoint logicalPos, int button, int mouseId) {
    super.mouseReleased(logicalPos, button, mouseId);
    if (gestureController != null && reseting < 0)
      gestureController.releaseAt(logicalPos, button, mouseId);
  }

  @Override
  public void paint(MyGraphics g) {
    BasicPainter.paintBasicBalls(rule.getGameBoard(), balls, g, frame);
    super.paint(g);
    if (gameEffectAdapter != null)
      gameEffectAdapter.paint(g);
    if (reseting >= 0) {
      resetMask.paint(g, frame);
      resetBackgound.paint(g, frame);
      resetConfirmButton.paint(g, frame);
      resetCancelButton.paint(g, frame);
    }
  }

  @Override
  public void buttonClicked(int indexOfTheButton) {
    switch (indexOfTheButton) {
    case ResetConfirmButton:
      reseting = -1;
      resetMask.setEnabled(false);
      resetConfirmButton.setEnabled(false);
      resetCancelButton.setEnabled(false);
      reset();
      break;
    case ResetCancelButton:
      reseting = -1;
      resetMask.setEnabled(false);
      resetConfirmButton.setEnabled(false);
      resetCancelButton.setEnabled(false);
      break;
    case ResetButton:
      startReset();
      break;
    case ExitButton:
      exit();
      break;
    }
  }

  /**
   * Advance the game
   */
  public void advance() {
    if (reseting > 0) {
      --reseting;
      resetResetItemPositions();
    }
    if (reseting >= 0)
      return;
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

  /**
   * Start to choose whether to reset.
   */
  protected void startReset() {
    reseting = RESET_ANIM_MAX;
    resetMask.setEnabled(true);
    resetConfirmButton.setEnabled(true);
    resetCancelButton.setEnabled(true);
    resetResetItemPositions();
  }

  private void resetResetItemPositions() {
    if (reseting >= 0) {
      double percentage = ANIM_PERCENTAGE[reseting];
      resetConfirmButton.setLogicalPosition(MathAid.midPosition(confirmFrom,
          confirmTo, percentage));
      resetCancelButton.setLogicalPosition(MathAid.midPosition(cancelFrom,
          cancelTo, percentage));
      resetBackgound.setLogicalPosition(MathAid.midPosition(
          resetBackgroundFrom, resetBackgroundTo, percentage));
    }
  }

  public abstract void reset();

  public abstract void exit();
}
