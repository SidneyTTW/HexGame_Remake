package VAST.HexGame.GameWidget;

import java.util.Vector;

import Aid.MathAid;
import Aid.MyPoint;
import Aid.MyRectangle;
import Aid.SoundController;
import VAST.HexGame.Game.Ball;
import VAST.HexGame.Game.ConnectionInterface;
import VAST.HexGame.Game.GameInterface;
import VAST.HexGame.Game.StandardBallFiller;
import VAST.HexGame.Game.Statistics;

/**
 * Class to play a endless game.
 * 
 * @author SidneyTTW
 * 
 */
public class EndlessGameWidget extends AbstractNonePuzzleGameWidget implements
    GameInterface {
  int currentLevel;

  public EndlessGameWidget(StandardGesture gesture) {
    rollBack = true;
    autoRotate = true;
    endlessFill = true;

    this.gesture = gesture;

    NonPuzzleGameRecord record = new NonPuzzleGameRecord();
    record.load();

    currentLevel = record.currentLevel;

    balls = record.balls;

    standardInit();

    ballFiller = new StandardBallFiller(rule, 0);
    rule.setBallFiller(ballFiller);

    highestScoreItem.setNumber(record.highestScore);
    verticalBar.setMin(record.levelMinScore);
    verticalBar.setMax(record.levelMaxScore);
    verticalBar.setCurrent(record.levelCurrentScore);
    flameItem.setCurrent(record.flame);
    starItem.setCurrent(record.star);
    flameItem.setCurrent(50);
    starItem.setCurrent(50);
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
    verticalBar.setCurrent(verticalBar.getCurrent() + count);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.GameWidget.AbstractNonePuzzleGameWidget#typeBase()
   */
  @Override
  public int typeBase() {
    return AbstractNonePuzzleGameWidget.NonPuzzleGameRecord.SwapEndless;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.GameWidget.AbstractStandardGameWidget#reset()
   */
  @Override
  public void reset() {
    NonPuzzleGameRecord record = new NonPuzzleGameRecord();
    record.clear();
    EndlessGameWidget nextStageWidget = new EndlessGameWidget(gesture);

    mainWidget.changeControl(nextStageWidget, true);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.GameWidget.AbstractStandardGameWidget#exit()
   */
  @Override
  public void exit() {
    NonPuzzleGameRecord record = new NonPuzzleGameRecord();
    record.currentLevel = currentLevel;
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

  @Override
  public void advance() {
    if (ended)
      return;
    super.advance();
    // Go to next stage if the score has been reached
    if (verticalBar.getCurrent() >= verticalBar.getMax()) {
      preEnded = true;
      boolean allStable = true;
      for (int i = 0; i < gameBoard.totalBallCount(); ++i)
        if ((balls[i] == null) || balls[i].getState() != Ball.State.Stable) {
          allStable = false;
          break;
        }

      if (allStable)
        nextStage();
      return;
    }
  }

  public void nextStage() {
    ended = true;
    
    // Add sound effect
    SoundController.addSound(SoundController.NextStage);

    // Record the initial state of next stage
    NonPuzzleGameRecord record = new NonPuzzleGameRecord();
    record.currentLevel = currentLevel + 1;
    record.highestScore = highestScoreItem.getNumber();
    record.levelMinScore = verticalBar.getMax();
    record.levelMaxScore = 2 * verticalBar.getMax();
    record.levelCurrentScore = verticalBar.getCurrent();
    record.flame = flameItem.getCurrent();
    record.star = starItem.getCurrent();
    record.save();

    // Create another widget of endless game and give control to it
    EndlessGameWidget nextStage = new EndlessGameWidget(gesture);
    mainWidget.changeControl(nextStage, true);
  }

}
