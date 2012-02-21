/**
 * 
 */
package VAST.HexGame.GameWidget;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import AidPackage.MathAid;
import AidPackage.MyPoint;
import AidPackage.MyRectangle;
import AidPackage.SoundController;
import VAST.HexGame.Game.ConnectionInterface;
import VAST.HexGame.Game.Statistics;
import VAST.HexGame.GameItem.IntegerItem;
import VAST.HexGame.GameWidget.AbstractNonePuzzleGameWidget.NonPuzzleGameRecord;
import VAST.HexGame.Widgets.AbstractSimpleWidget;

/**
 * Class to play a timing game.
 * 
 * @author SidneyTTW
 * 
 */
public class TimingGameWidget extends AbstractNonePuzzleGameWidget {
  protected IntegerItem currentScoreItem;
  
  private Timer timingTimer = null;

  public TimingGameWidget(StandardGesture gesture) {
    rollBack = true;
    autoRotate = true;
    endlessFill = true;

    this.gesture = gesture;
    
    NonPuzzleGameRecord record = new NonPuzzleGameRecord();
    record.load();
    
    balls = record.balls;

    standardInit();
    
    verticalBar.setMax(60);
    verticalBar.setCurrent(60);

    currentScoreItem = new IntegerItem(180);
    currentScoreItem.setDescription("Current Score");
    currentScoreItem.setLogicalPosition((new MyPoint((int) (width() * 0.1),
        (int) (height() * 0.25))));
    addItem(currentScoreItem, AbstractSimpleWidget.ItemType.SimpleItem);
    
    highestScoreItem.setNumber(record.highestScore);
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
      MyPoint pos1 = gameBoard.ballLogicalPositionOfIndex(allChains
          .elementAt(i).elementAt(0)).clone();
      MyPoint pos2 = gameBoard.ballLogicalPositionOfIndex(allChains
          .elementAt(i).elementAt(size - 1)).clone();
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
    // TODO Auto-generated method stub

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
  
  public void oneSecond() {
    int last = verticalBar.getCurrent();
    if (last > 0)
      verticalBar.setCurrent(last - 1);
    else
      gameOver();
  }
  
  private void gameOver() {
    //TODO
  }

  @Override
  public int typeBase() {
    return AbstractNonePuzzleGameWidget.NonPuzzleGameRecord.SwapTiming;
  }
}
