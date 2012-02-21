/**
 * 
 */
package VAST.HexGame.GameWidget;

import java.util.Vector;

import AidPackage.MathAid;
import AidPackage.MyPoint;
import AidPackage.MyRectangle;
import AidPackage.SoundController;
import VAST.HexGame.Game.ConnectionInterface;
import VAST.HexGame.Game.Statistics;

/**
 * Class to play a classic game.
 * 
 * @author SidneyTTW
 * 
 */
public class ClassicGameWidget extends AbstractNonePuzzleGameWidget {

  public ClassicGameWidget(StandardGesture gesture) {
    rollBack = true;
    autoRotate = true;
    endlessFill = false;

    this.gesture = gesture;

    NonPuzzleGameRecord record = new NonPuzzleGameRecord();
    record.load();

    balls = record.balls;

    standardInit();

    highestScoreItem.setNumber(record.highestScore);
    verticalBar.setMin(record.levelMinScore);
    verticalBar.setMax(record.levelMaxScore);
    verticalBar.setCurrent(record.levelCurrentScore);
    flameItem.setCurrent(record.flame);
    starItem.setCurrent(record.star);
  }

  @Override
  public void stableConnectionTested(ConnectionInterface connections) {
    super.stableConnectionTested(connections);
    int scoreToAdd = 0;
    Vector<Vector<Integer>> allChains = connections.allChains();
    for (int i = 0; i < gameBoard.totalBallCount(); ++i) {
      int relatedChainCount = connections.relatedChainCount(i);
      if (gameEffectAdapter != null && relatedChainCount >= 2)
        gameEffectAdapter.flash(new MyRectangle(0, 0, width(), height()));
      if (relatedChainCount > 0)
        ++scoreToAdd;
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
    verticalBar.setCurrent(verticalBar.getCurrent() + scoreToAdd);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameInterface#eliminated(int)
   */
  @Override
  public void eliminated(int count) {
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
    NonPuzzleGameRecord record = new NonPuzzleGameRecord();
    record.highestScore = highestScoreItem.getNumber();
    record.levelMinScore = verticalBar.getMin();
    record.levelMaxScore = verticalBar.getMax();
    record.levelCurrentScore = verticalBar.getCurrent();
    record.flame = flameItem.getCurrent();
    record.star = starItem.getCurrent();
    record.balls = balls;
    record.save();

    mainWidget.changeControl(null, true);
  }

  public void nextStage() {
    // TODO Auto-generated method stub

  }

  @Override
  public int typeBase() {
    return AbstractNonePuzzleGameWidget.NonPuzzleGameRecord.SwapClassic;
  }

}
