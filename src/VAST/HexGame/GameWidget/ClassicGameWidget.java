/**
 * 
 */
package VAST.HexGame.GameWidget;

import VAST.HexGame.Effect.EffectPainter;
import VAST.HexGame.Game.Ball;
import VAST.HexGame.Game.CoreController;
import VAST.HexGame.Game.GameEffectAdapter;
import VAST.HexGame.Game.RotateGestureController;
import VAST.HexGame.Game.RotateStandardConnectionCalculator;
import VAST.HexGame.Game.StandardBallFiller;
import VAST.HexGame.Game.StandardGameRule;
import VAST.HexGame.Game.SwapGestureController;
import VAST.HexGame.Game.SwapStandardConnectionCalculator;
import VAST.HexGame.Game.ThirtySevenGameBoard;

/**
 * Class to play a classic game.
 * 
 * @author SidneyTTW
 *
 */
public class ClassicGameWidget extends AbstractStandardGameWidget {
  
  public ClassicGameWidget(StandardGesture gesture) {
    gameBoard = new ThirtySevenGameBoard();
    balls = new Ball[gameBoard.totalBallCount()];
    for (int i = 0;i < gameBoard.totalBallCount();++i)
      balls[i] = null;
    
    rule = new StandardGameRule();
    gameEffectAdapter = new GameEffectAdapter(new EffectPainter());
    coreController = new CoreController(this, rule, balls);
    ballFiller = new StandardBallFiller(rule, gameBoard.totalBallCount() / 3);
    
    switch (gesture) {
    case Swap:
      connectionCalculator = new SwapStandardConnectionCalculator();
      gestureController = new SwapGestureController(this, rule);
      break;
    case Rotate:
      connectionCalculator = new RotateStandardConnectionCalculator();
      gestureController = new RotateGestureController(this, rule);
      break;
    }

    boolean rollBack = true;
    boolean autoRotate = true;
    boolean endlessFill = false;
    
    rule.setBallFiller(ballFiller);
    rule.setConnectionCalculator(connectionCalculator);
    rule.setCoreController(coreController);
    rule.setEffectPainter(gameEffectAdapter);
    rule.setGameBoard(gameBoard);
    rule.setGestureController(gestureController);
    rule.setSomeBooleans(rollBack, autoRotate, endlessFill);
  }

  /* (non-Javadoc)
   * @see VAST.HexGame.Game.GameInterface#eliminated(int)
   */
  @Override
  public void eliminated(int count) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see VAST.HexGame.Widgets.AbstractSimpleWidget#buttonClicked(int)
   */
  @Override
  public void buttonClicked(int indexOfTheButton) {
    // TODO Auto-generated method stub

  }

}
