/**
 * 
 */
package VAST.HexGame.GameWidget;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Vector;

import AidPackage.MathAid;
import AidPackage.SoundController;
import VAST.HexGame.Effect.EffectPainter;
import VAST.HexGame.Game.Ball;
import VAST.HexGame.Game.ConnectionInterface;
import VAST.HexGame.Game.CoreController;
import VAST.HexGame.Game.GameEffectAdapter;
import VAST.HexGame.Game.RotateGestureController;
import VAST.HexGame.Game.RotateStandardConnectionCalculator;
import VAST.HexGame.Game.StandardBallFiller;
import VAST.HexGame.Game.StandardGameRule;
import VAST.HexGame.Game.Statistics;
import VAST.HexGame.Game.SwapGestureController;
import VAST.HexGame.Game.SwapStandardConnectionCalculator;
import VAST.HexGame.Game.ThirtySevenGameBoard;
import VAST.HexGame.GameItem.IntegerItem;
import VAST.HexGame.GameItem.ProgressBarIterface;
import VAST.HexGame.GameItem.StandardGameButtonItem;
import VAST.HexGame.GameItem.VerticalProgressBarItem;
import VAST.HexGame.GameWidget.AbstractNonePuzzleGameWidget.NonPuzzleGameRecord;
import VAST.HexGame.Widgets.AbstractSimpleWidget;

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
        gameEffectAdapter.flash(new Rectangle(0, 0, width(), height()));
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
      Point pos1 = new Point(gameBoard.ballLogicalPositionOfIndex(allChains
          .elementAt(i).elementAt(0)));
      Point pos2 = new Point(gameBoard.ballLogicalPositionOfIndex(allChains
          .elementAt(i).elementAt(size - 1)));
      if (gameEffectAdapter != null)
        gameEffectAdapter.wordsAt(MathAid.midPosition(pos1, pos2, 0.5), ""
            + size, size * 4 + 15);
      if (gameEffectAdapter != null && size >= 4)
        gameEffectAdapter.flash(new Rectangle(0, 0, width(), height()));
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

  /* (non-Javadoc)
   * @see VAST.HexGame.GameWidget.AbstractStandardGameWidget#reset()
   */
  @Override
  public void reset() {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
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
    // TODO Auto-generated method stub
    return 0;
  }

}
