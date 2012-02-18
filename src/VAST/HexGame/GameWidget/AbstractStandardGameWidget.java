/**
 * 
 */
package VAST.HexGame.GameWidget;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import AidPackage.MathAid;
import AidPackage.SoundController;
import VAST.HexGame.Aid.SourceManagement;
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
import VAST.HexGame.Game.RotateGestureController;
import VAST.HexGame.Game.RotateStandardConnectionCalculator;
import VAST.HexGame.Game.StandardGameRule;
import VAST.HexGame.Game.Statistics;
import VAST.HexGame.Game.SwapGestureController;
import VAST.HexGame.Game.SwapStandardConnectionCalculator;
import VAST.HexGame.GameItem.AbstractBonusItem;
import VAST.HexGame.GameItem.FlameBonusItem;
import VAST.HexGame.GameItem.RectMaskItem;
import VAST.HexGame.GameItem.StandardGameButtonItem;
import VAST.HexGame.GameItem.StarBonusItem;
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

  protected Ball[] balls;
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
  private static final Point resetBackgroundFrom = new Point(
      (int) (AbstractSimpleWidget.SIMPLE_WIDGET_WIDTH * 0.5), -100);
  private static final Point resetBackgroundTo = new Point(
      (int) (AbstractSimpleWidget.SIMPLE_WIDGET_WIDTH * 0.5),
      (int) (AbstractSimpleWidget.SIMPLE_WIDGET_HEIGHT * 0.5));
  private static final Point confirmFrom = new Point(-100,
      (int) (AbstractSimpleWidget.SIMPLE_WIDGET_HEIGHT * 0.6));
  private static final Point confirmTo = new Point(
      (int) (AbstractSimpleWidget.SIMPLE_WIDGET_WIDTH * 0.4),
      (int) (AbstractSimpleWidget.SIMPLE_WIDGET_HEIGHT * 0.6));

  private static final Point cancelFrom = new Point(
      AbstractSimpleWidget.SIMPLE_WIDGET_WIDTH + 100,
      (int) (AbstractSimpleWidget.SIMPLE_WIDGET_HEIGHT * 0.6));
  private static final Point cancelTo = new Point(
      (int) (AbstractSimpleWidget.SIMPLE_WIDGET_WIDTH * 0.6),
      (int) (AbstractSimpleWidget.SIMPLE_WIDGET_HEIGHT * 0.6));
  private static final int ResetConfirmButton = 0;
  private static final int ResetCancelButton = 1;
  private static final int FlameItem = 0;
  private static final int StarItem = 1;
  public static final int BUILT_IN_BUTTON_COUNT = 3;
  public static final int BUILT_IN_BONUS_ITEM_COUNT = 2;

  protected int reseting = -1;

  protected ItemInterface resetMask;
  protected ItemInterface resetBackgound = null;
  protected ItemInterface resetConfirmButton;
  protected ItemInterface resetCancelButton;

  protected AbstractBonusItem flameItem = null;
  protected AbstractBonusItem starItem = null;

  protected void standardInit() {
    flameItem = new FlameBonusItem();
    starItem = new StarBonusItem();

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
    resetMask.setLogicalPosition(new Point(
        (int) (AbstractSimpleWidget.SIMPLE_WIDGET_WIDTH * 0.5),
        (int) (AbstractSimpleWidget.SIMPLE_WIDGET_HEIGHT * 0.5)));
    ((RectItem) resetMask).setWidth(AbstractSimpleWidget.SIMPLE_WIDGET_WIDTH);
    ((RectItem) resetMask).setHeight(AbstractSimpleWidget.SIMPLE_WIDGET_HEIGHT);
    addItem(resetMask, AbstractSimpleWidget.ItemType.ButtonItem);

    addItem(flameItem, AbstractSimpleWidget.ItemType.SimpleItem);
    addItem(starItem, AbstractSimpleWidget.ItemType.SimpleItem);
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
  public void mousePressed(Point logicalPos, int button, int mouseId) {
    super.mousePressed(logicalPos, button, mouseId);
    if (gestureController != null && reseting < 0)
      gestureController.pressAt(logicalPos, button, mouseId);
  }

  @Override
  public void mouseDragged(Point logicalPos, int button, int mouseId) {
    super.mouseDragged(logicalPos, button, mouseId);
    if (gestureController != null && reseting < 0)
      gestureController.dragAt(logicalPos, button, mouseId);
  }

  @Override
  public void mouseReleased(Point logicalPos, int button, int mouseId) {
    super.mouseReleased(logicalPos, button, mouseId);
    if (gestureController != null && reseting < 0)
      gestureController.releaseAt(logicalPos, button, mouseId);
  }

  @Override
  public void paint(Graphics g) {
    BasicPainter.paintBackGround(BasicPainter.Game37, g, width(), height(), 0);
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
  public void eliminated(int count) {
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
   * Give hint to the user.
   */
  protected void hint() {
    if (connectionCalculator == null)
      return;
    int hintIndex = connectionCalculator.hint(balls, gameBoard);
    Point hintPos;
    int hintType;
    if (hintIndex >= 0) {
      hintPos = (Point) gameBoard.ballLogicalPositionOfIndex(hintIndex).clone();
      switch (gesture) {
      case Swap:
        hintPos.translate(0, -gameBoard.getBallRadius() * 2);
        hintType = SourceManagement.ArrowHint;
        break;
      case Rotate:
        hintType = SourceManagement.RotateHint;
        break;
      default:
        return;
      }
    } else {
      hintType = SourceManagement.ArrowHint;
      if (!flameItem.isEmpty()) {
        hintPos = (Point) flameItem.getLogicalPosition().clone();
        hintPos.translate(0, -flameItem.getRadius() * 2);
      } else if (!starItem.isEmpty()) {
        hintPos = (Point) starItem.getLogicalPosition().clone();
        hintPos.translate(0, -starItem.getRadius() * 2);
      } else {
        // TODO -.-
        return;
      }
    }
    if (gameEffectAdapter != null)
      gameEffectAdapter.hintAt(hintPos, hintType);
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

  @Override
  public void dragTo(int indexOfTheDraggableItem, Point position) {
    if (gameEffectAdapter != null)
      gameEffectAdapter.clearBonusEliminationHints();
    int index = gameBoard.ballIndexAtLogicalPosition(position);
    if (index < 0)
      return;
    Vector<Integer> indexesToEliminate = null;
    switch (indexOfTheDraggableItem) {
    case FlameItem:
      if (flameItem.isEmpty())
        return;
      indexesToEliminate = flameChain(index);
      break;
    case StarItem:
      if (starItem.isEmpty())
        return;
      indexesToEliminate = starChain(index);
      break;
    default:
      return;
    }
    if (gameEffectAdapter != null)
      for (int i = 0; i < indexesToEliminate.size(); ++i)
        if (indexesToEliminate.elementAt(i) >= 0)
          gameEffectAdapter.bonusEliminationHintAt(gameBoard,
              indexesToEliminate.elementAt(i));
  }

  @Override
  public void dragApplied(int indexOfTheDraggableItem, Point position) {
    if (gameEffectAdapter != null)
      gameEffectAdapter.clearBonusEliminationHints();
    int index = gameBoard.ballIndexAtLogicalPosition(position);
    if (index < 0)
      return;
    switch (indexOfTheDraggableItem) {
    case FlameItem: {
      if (flameItem.isEmpty())
        return;
      flameItem.minusOne();
      if (gameEffectAdapter != null)
        gameEffectAdapter.flash(new Rectangle(0, 0, width(), height()));
      // Add sound effect
      SoundController.addSound(SoundController.UseFlame);
      // Connect to statistic
      Statistics.addStatistic(Statistics.FlameUsedCount, 1);
      // Apply the bonus item
      Vector<Integer> indexesToEliminate = flameChain(index);
      coreController.eliminate(indexesToEliminate);
      break;
    }
    case StarItem: {
      if (starItem.isEmpty())
        return;
      starItem.minusOne();
      if (gameEffectAdapter != null)
        gameEffectAdapter.flash(new Rectangle(0, 0, width(), height()));
      // Add sound effect
      SoundController.addSound(SoundController.UseStar);
      // Connect to statistic
      Statistics.addStatistic(Statistics.StarUsedCount, 1);
      Vector<Integer> indexesToEliminate = starChain(index);
      coreController.eliminate(indexesToEliminate);
      break;
    }
    }
  }

  protected Vector<Integer> flameChain(int index) {
    Vector<Integer> result = (Vector<Integer>) gameBoard
        .chainAroundIndex(index).clone();
    result.add(index);
    return result;
  }

  protected Vector<Integer> starChain(int index) {
    Vector<Integer> result = new Vector<Integer>();
    result.add(index);
    for (int i = 0; i < 6; ++i) {
      int currentIndex = gameBoard.nearbyIndex(index, i);
      while (currentIndex >= 0) {
        result.add(currentIndex);
        currentIndex = gameBoard.nearbyIndex(currentIndex, i);
      }
    }
    return result;
  }
}
