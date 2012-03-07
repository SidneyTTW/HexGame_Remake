package VAST.HexGame.GameWidget;

import java.util.Vector;
import Aid.MathAid;
import Aid.MyPoint;
import Aid.MyRectangle;
import Aid.SoundController;
import VAST.HexGame.Game.Ball;
import VAST.HexGame.Game.ConnectionInterface;
import VAST.HexGame.Game.StandardBallFiller;
import VAST.HexGame.Game.Statistics;

/**
 * Class to play a classic game.
 * 
 * @author SidneyTTW
 * 
 */
public class ClassicGameWidget extends AbstractNonePuzzleGameWidget {
  int currentLevel;
  int endAnimCount;

  public ClassicGameWidget(StandardGesture gesture) {
    rollBack = true;
    autoRotate = true;
    endlessFill = false;

    this.gesture = gesture;

    NonPuzzleGameRecord record = new NonPuzzleGameRecord();
    record.load();

    currentLevel = record.currentLevel;

    balls = record.balls;

    standardInit();

    int lock = Math.min(2 * record.currentLevel, gameBoard.totalBallCount());
    ballFiller = new StandardBallFiller(rule, lock);
    rule.setBallFiller(ballFiller);

    highestScoreItem.setNumber(record.highestScore);
    verticalBar.setMin(record.levelMinScore);
    verticalBar.setMax(record.levelMaxScore);
    verticalBar.setCurrent(record.levelCurrentScore);
    flameItem.setCurrent(record.flame);
    starItem.setCurrent(record.star);

    endAnimCount = -1;
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
    NonPuzzleGameRecord record = new NonPuzzleGameRecord();
    record.clear();
    ClassicGameWidget nextStageWidget = new ClassicGameWidget(gesture);

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
    record.levelMinScore = verticalBar.getMin();
    record.levelMaxScore = verticalBar.getMax();
    record.levelCurrentScore = verticalBar.getCurrent();
    record.flame = flameItem.getCurrent();
    record.star = starItem.getCurrent();
    record.balls = balls;
    record.balls = balls;
    record.save();

    mainWidget.changeControl(null, true);
  }

  public void nextStage() {
    ended = true;
    
    // Add sound effect
    SoundController.addSound(SoundController.NextStage);

    // Record the initial state of next stage
    NonPuzzleGameRecord record = new NonPuzzleGameRecord();
    record.currentLevel = currentLevel + 1;
    record.levelMinScore = verticalBar.getMax();
    record.levelMaxScore = 2 * verticalBar.getMax();
    record.levelCurrentScore = verticalBar.getCurrent();
    record.flame = flameItem.getCurrent();
    record.star = starItem.getCurrent();
    record.save();
    ClassicGameWidget nextStageWidget = new ClassicGameWidget(gesture);

    // Create another widget of endless game and give control to it
    mainWidget.changeControl(nextStageWidget, true);
  }

  @Override
  public int typeBase() {
    return AbstractNonePuzzleGameWidget.NonPuzzleGameRecord.SwapClassic;
  }

  @Override
  public void advance() {
    if (ended)
      return;
    if (endAnimCount == -1) {
      // Advance the controller
      super.advance();

      // Go to next stage if the score has been reached
      if (verticalBar.getCurrent() >= verticalBar.getMax()) {
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

      // Record the no solution count
      if (flameItem.getCurrent() == 0 && starItem.getCurrent() == 0) {
        boolean allStable = true;
        for (int i = 0; i < gameBoard.totalBallCount(); ++i)
          if ((balls[i] == null) || balls[i].getState() != Ball.State.Stable) {
            allStable = false;
            break;
          }
        if (allStable && connectionCalculator.hint(balls, gameBoard) < 0)
          preGameOver();
      }
    } else {
      --endAnimCount;
      for (int i = 0; i < gameBoard.totalBallCount(); ++i)
        if (balls[i] != null)
          balls[i].advance();
      if (endAnimCount == 0)
        gameOver();
    }
  }

  private void preGameOver() {
    preEnded = true;
    
    // Add sound effect
    SoundController.addSound(SoundController.GameOver);

    endAnimCount = 20;

    int dxs[] = { 1, 2, 3, 4, 4, 4, 4, 4 };

    double y = gameBoard.getHeight() + gameBoard.getBallRadius();
    double centerX = gameBoard.centerPosition().x;

    for (int i = 0; i < gameBoard.totalBallCount(); ++i)
      if (balls[i] != null) {
        double cx = balls[i].getPosition().x;
        double cy = balls[i].getPosition().y;
        double dx = MathAid.nextUInt() % gameBoard.getWidth() / 2
            - gameBoard.getWidth() / 4;

        for (int j = 0; j < 6; ++j) {
          int positive = 1;
          if (j % 2 == i % 2)
            positive = -1;
          coreController.translateABallTo(balls[i], new MyPoint(cx + positive
              * dxs[j], cy), 1, true);
        }

        coreController.translateABallTo(balls[i], new MyPoint(centerX + dx, y),
            endAnimCount - 6, false);
      }
  }

  private void gameOver() {
    // Clear the game record
    NonPuzzleGameRecord record = new NonPuzzleGameRecord();
    record.clear();

    // Test the highest score
    boolean newRecord = record.testHighest(verticalBar.getCurrent());

    // // Create the game over widget and give control to it
     GameOverWidget w = new GameOverWidget
     ("Classic Game", verticalBar.getCurrent(), newRecord, gesture);
     // Create another widget of endless game and give control to it
     mainWidget.changeControl(w, true);
  }
}
