package VAST.HexGame.GameWidget;

import java.util.Date;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import Aid.MathAid;
import Aid.MyGraphics;
import Aid.MyPoint;
import Aid.MyRectangle;
import Aid.SoundController;
import VAST.HexGame.Game.Ball;
import VAST.HexGame.Game.ConnectionInterface;
import VAST.HexGame.Game.StandardBallFiller;
import VAST.HexGame.Game.Statistics;
import VAST.HexGame.GameItem.AbstractBonusItem;
import VAST.HexGame.GameItem.IntegerItem;
import VAST.HexGame.GameItem.StandardGameButtonItem;
import VAST.HexGame.GameItem.TextItem;
import VAST.HexGame.Widgets.AbstractSimpleWidget;

/**
 * Class to play a timing game.
 * 
 * @author SidneyTTW
 * 
 */
public class TimingGameWidget extends AbstractNonePuzzleGameWidget {
  protected IntegerItem currentScoreItem;

  private static final int PauseButton = AbstractNonePuzzleGameWidget.BUILT_IN_BUTTON_COUNT + 0;

  private Timer timingTimer = null;

  private TextItem pauseItem = new TextItem(316);
  private boolean paused = false;

  private boolean timeUp = false;
  private LinkedList<Integer> endAnimCount = new LinkedList<Integer>();
  private LinkedList<AbstractBonusItem> endAnimBonusKind = new LinkedList<AbstractBonusItem>();
  private static final int END_ANIM_INTERVAL = 4;
  private static final int END_ANIM_LAST_TIME = 9;
  private static final int FLAME_SCORE = 7;
  private static final int STAR_SCORE = 19;

  protected StandardGameButtonItem pauseButton;

  public TimingGameWidget(StandardGesture gesture) {
    rollBack = true;
    autoRotate = true;
    endlessFill = true;

    this.gesture = gesture;

    NonPuzzleGameRecord record = new NonPuzzleGameRecord();
    record.load();

    balls = record.balls;

    standardInit();

    ballFiller = new StandardBallFiller(rule, 0);
    rule.setBallFiller(ballFiller);

    verticalBar.setMax(60);
    verticalBar.setCurrent(60);

    currentScoreItem = new IntegerItem(180);
    currentScoreItem.setDescription("Current Score");
    currentScoreItem.setLogicalPosition((new MyPoint((int) (width() * 0.1),
        (int) (height() * 0.25))));
    addItem(currentScoreItem, AbstractSimpleWidget.ItemType.SimpleItem);

    highestScoreItem.setNumber(record.highestScore);

    pauseButton = new StandardGameButtonItem();
    pauseButton.setText("Cancel");
    pauseButton.setLogicalPosition(new MyPoint((int) (width() * 0.1),
        (int) (height() * 0.7)));
    addItem(pauseButton, AbstractSimpleWidget.ItemType.ButtonItem);

    pauseItem.setText("Press any position to continue");
    pauseItem.setEnabled(false);
    pauseItem.setVisible(false);
    pauseItem.setLogicalPosition(new MyPoint((int) (width() * 0.5),
        (int) (height() * 0.5)));
    addItem(pauseItem, AbstractSimpleWidget.ItemType.SimpleItem);
  }

  /**
   * @author SidneyTTW
   * 
   * @brief The timer task to timing.
   */
  public class TimingTimerTask extends TimerTask {
    public void run() {
      oneSecond();
    }
  }

  @Override
  public void stableConnectionTested(ConnectionInterface connections) {
    super.stableConnectionTested(connections);
    Vector<Vector<Integer>> allChains = connections.allChains();
    for (int i = 0; i < gameBoard.totalBallCount(); ++i) {
      int relatedChainCount = connections.relatedChainCount(i);
      if (gameEffectAdapter != null && relatedChainCount >= 2)
        gameEffectAdapter.flash(new MyRectangle(0, 0, width(), height()));
      if (relatedChainCount == 2) {
        // Add sound effect
        SoundController.addSound(SoundController.GetFlame);
        // Get a flame
        flameItem.addOne();
        // Connect to statistic
        Statistics.addStatistic(Statistics.FlameGetCount, 1);
      } else if (relatedChainCount > 2) {
        // Add sound effect
        SoundController.addSound(SoundController.GetStar);
        // Get a flame
        starItem.addOne();
        // Connect to statistic
        Statistics.addStatistic(Statistics.StarGetCount, 1);
      }
    }

    for (int i = 0; i < allChains.size(); ++i) {
      int size = allChains.elementAt(i).size();
      MyPoint pos1 = gameBoard.ballLogicalPositionOfIndex(
          allChains.elementAt(i).elementAt(0)).clone();
      MyPoint pos2 = gameBoard.ballLogicalPositionOfIndex(
          allChains.elementAt(i).elementAt(size - 1)).clone();
      if (gameEffectAdapter != null)
        gameEffectAdapter.wordsAt(MathAid.midPosition(pos1, pos2, 0.5), ""
            + size, size * 4 + 15);
      if (gameEffectAdapter != null && size >= 4)
        gameEffectAdapter.flash(new MyRectangle(0, 0, width(), height()));
      if (size == 4) {
        // Add sound effect
        SoundController.addSound(SoundController.GetFlame);
        // Get a flame
        flameItem.addOne();
        // Connect to statistic
        Statistics.addStatistic(Statistics.FlameGetCount, 1);
      } else if (size > 4) {
        // Add sound effect
        SoundController.addSound(SoundController.GetStar);
        // Get a flame
        starItem.addOne();
        // Connect to statistic
        Statistics.addStatistic(Statistics.StarGetCount, 1);
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameInterface#eliminated(int)
   */
  @Override
  public void eliminated(int count) {
    currentScoreItem.setNumber(currentScoreItem.getNumber() + count);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.GameWidget.AbstractStandardGameWidget#reset()
   */
  @Override
  public void reset() {
    TimingGameWidget nextStageWidget = new TimingGameWidget(gesture);
    mainWidget.changeControl(nextStageWidget, true);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.GameWidget.AbstractStandardGameWidget#exit()
   */
  @Override
  public void exit() {
    mainWidget.changeControl(null, true);
  }

  @Override
  public void getFocus() {
    super.getFocus();
    if (timingTimer == null) {
      TimingTimerTask task = new TimingTimerTask();
      timingTimer = new Timer();
      timingTimer.schedule(task, new Date(), 1000);
    }
  }

  @Override
  public void loseFocus() {
    timingTimer.cancel();
    super.loseFocus();
  }

  @Override
  public void paint(MyGraphics g) {
    super.paint(g);
    if (paused)
      pauseItem.paint(g, frame);

    if (timeUp) {
      for (int i = 0; i < endAnimCount.size(); ++i) {
        Integer count = endAnimCount.get(i);
        AbstractBonusItem item = endAnimBonusKind.get(i);

        g.setAlpha(255 * (END_ANIM_LAST_TIME - count) / END_ANIM_LAST_TIME);
        MyPoint pos = new MyPoint((currentScoreItem.getLogicalPosition().x
            * count + item.getLogicalPosition().x
            * (END_ANIM_LAST_TIME - count))
            / END_ANIM_LAST_TIME, (currentScoreItem.getLogicalPosition().y
            * count + item.getLogicalPosition().y
            * (END_ANIM_LAST_TIME - count))
            / END_ANIM_LAST_TIME);
        item.paintShadow(pos, g, frame);
        g.setAlpha(255);
      }
    }
  }

  @Override
  public void advance() {
    super.advance();
    if (!hasFocus)
      return;
    if (timeUp) {
      for (int i = 0; i < endAnimCount.size(); ++i) {
        endAnimCount.set(i, endAnimCount.get(i) + 1);
      }

      if (!endAnimCount.isEmpty())
        if (endAnimCount.getLast() >= END_ANIM_LAST_TIME) {
          currentScoreItem.setNumber(currentScoreItem.getNumber()
              + ((endAnimBonusKind.getLast() == flameItem) ? FLAME_SCORE
                  : STAR_SCORE));
          endAnimCount.pollFirst();
          endAnimBonusKind.pollFirst();
        }
      if (endAnimCount.isEmpty()) {
        if (flameItem.getCurrent() > 0) {
          flameItem.minusOne();
          endAnimCount.push(0);
          endAnimBonusKind.push(flameItem);
        } else if (starItem.getCurrent() > 0) {
          starItem.minusOne();
          endAnimCount.push(0);
          endAnimBonusKind.push(starItem);
        } else {

          boolean allStable = true;
          Ball balls[] = coreController.getBalls();
          for (int i = 0; i < gameBoard.totalBallCount(); ++i)
            if (balls[i] == null || balls[i].getState() != Ball.State.Stable) {
              allStable = false;
              break;
            }

          if (allStable) {
            gameOver();
            return;
          }
        }
      } else {
        if (endAnimCount.getFirst() == END_ANIM_INTERVAL) {
          if (flameItem.getCurrent() > 0) {
            flameItem.minusOne();
            endAnimCount.push(0);
            endAnimBonusKind.push(flameItem);
          } else if (starItem.getCurrent() > 0) {
            starItem.minusOne();
            endAnimCount.push(0);
            endAnimBonusKind.push(starItem);
          }
        }
      }
    }
  }

  public void oneSecond() {
    if (paused || !hasFocus)
      return;
    int last = verticalBar.getCurrent();
    if (last > 0)
      verticalBar.setCurrent(last - 1);
    else {
      timingTimer.cancel();
      timeUp = true;
    }
  }

  private void gameOver() {
    hasFocus = false;

    // Test the highest score
    NonPuzzleGameRecord record = new NonPuzzleGameRecord();
    boolean newRecord = record.testHighest(currentScoreItem.getNumber());

    // // Create the game over widget and give control to it
    GameOverWidget w = new GameOverWidget(GameOverWidget.Timing, gesture,
        currentScoreItem.getNumber(), newRecord);
    // Create another widget of endless game and give control to it
    mainWidget.changeControl(w, true);
  }

  @Override
  public void buttonClicked(int indexOfTheButton) {
    super.buttonClicked(indexOfTheButton);
    switch (indexOfTheButton) {
    case PauseButton:
      startPause();
      break;
    case ResetMaskButton:
      if (paused) {
        paused = false;
        resetMask.setVisible(false);
        resetMask.setEnabled(false);
      }
      break;
    }
  }

  private void startPause() {
    paused = true;
    resetMask.setVisible(true);
    resetMask.setEnabled(true);
  }

  @Override
  public int typeBase() {
    return AbstractNonePuzzleGameWidget.NonPuzzleGameRecord.SwapTiming;
  }

  @Override
  public void mousePressed(MyPoint logicalPos, int button, int mouseId) {
    if (!timeUp)
      super.mousePressed(logicalPos, button, mouseId);
  }

  @Override
  public void mouseDragged(MyPoint logicalPos, int button, int mouseId) {
    if (!timeUp)
      super.mouseDragged(logicalPos, button, mouseId);
  }

  @Override
  public void mouseReleased(MyPoint logicalPos, int button, int mouseId) {
    if (!timeUp)
      super.mouseReleased(logicalPos, button, mouseId);
  }
}
