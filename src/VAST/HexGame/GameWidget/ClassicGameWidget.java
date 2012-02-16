/**
 * 
 */
package VAST.HexGame.GameWidget;

import java.awt.Point;

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
import VAST.HexGame.GameItem.StandardGameButtonItem;
import VAST.HexGame.Widgets.AbstractSimpleWidget;

/**
 * Class to play a classic game.
 * 
 * @author SidneyTTW
 * 
 */
public class ClassicGameWidget extends AbstractStandardGameWidget {
  private static final int HintButton = AbstractStandardGameWidget.BUILT_IN_BUTTON_COUNT + 0;
  private static final int ResetButton = AbstractStandardGameWidget.BUILT_IN_BUTTON_COUNT + 1;
  private static final int ExitButton = AbstractStandardGameWidget.BUILT_IN_BUTTON_COUNT + 2;

  public ClassicGameWidget(StandardGesture gesture) {
    standardInit();
    
    this.gesture = gesture;
    gameBoard = new ThirtySevenGameBoard();
    balls = new Ball[gameBoard.totalBallCount()];
    for (int i = 0; i < gameBoard.totalBallCount(); ++i)
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

    StandardGameButtonItem button = new StandardGameButtonItem();
    button.setText("Hint");
    button.setLogicalPosition(new Point((int) (width() * 0.1),
        (int) (height() * 0.7)));
    addItem(button, AbstractSimpleWidget.ItemType.ButtonItem);

    button = new StandardGameButtonItem();
    button.setText("Reset");
    button.setLogicalPosition(new Point((int) (width() * 0.1),
        (int) (height() * 0.8)));
    addItem(button, AbstractSimpleWidget.ItemType.ButtonItem);

    button = new StandardGameButtonItem();
    button.setText("Exit");
    button.setLogicalPosition(new Point((int) (width() * 0.1),
        (int) (height() * 0.9)));
    addItem(button, AbstractSimpleWidget.ItemType.ButtonItem);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameInterface#eliminated(int)
   */
  @Override
  public void eliminated(int count) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.AbstractSimpleWidget#buttonClicked(int)
   */
  @Override
  public void buttonClicked(int indexOfTheButton) {
    super.buttonClicked(indexOfTheButton);
    switch (indexOfTheButton) {
    case HintButton:
      hint();
      break;
    case ResetButton:
      startReset();
      break;
    case ExitButton:
      exit();
      break;
    }
  }

  @Override
  public void reset() {
    // TODO Auto-generated method stub

  }

  public void exit() {
    // TODO Auto-generated method stub

  }

  public void nextStage() {
    // TODO Auto-generated method stub

  }

}
