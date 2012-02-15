/**
 * 
 */
package VAST.HexGame.GameWidget;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Vector;

import VAST.HexGame.Effect.EffectPainter;
import VAST.HexGame.Game.Ball;
import VAST.HexGame.Game.BallFillerInterface;
import VAST.HexGame.Game.BasicPainter;
import VAST.HexGame.Game.ConnectionCalculatorInterface;
import VAST.HexGame.Game.ConnectionInterface;
import VAST.HexGame.Game.CoreController;
import VAST.HexGame.Game.CoreControllerInterface;
import VAST.HexGame.Game.GameBoardInterface;
import VAST.HexGame.Game.GameEffectAdapter;
import VAST.HexGame.Game.GameInterface;
import VAST.HexGame.Game.GestureControllerInterface;
import VAST.HexGame.Game.RotateGestureController;
import VAST.HexGame.Game.RotateStandardConnectionCalculator;
import VAST.HexGame.Game.StandardBallFiller;
import VAST.HexGame.Game.StandardGameRule;
import VAST.HexGame.Game.SwapGestureController;
import VAST.HexGame.Game.SwapStandardConnectionCalculator;
import VAST.HexGame.Game.ThirtySevenGameBoard;
import VAST.HexGame.Widgets.AbstractSimpleWidget;

/**
 * Class for test.
 * 
 * @author SidneyTTW
 *
 */
public class TestGameWidget extends AbstractSimpleWidget implements
    GameInterface {

  Ball [] balls;
  StandardGameRule rule;
  ConnectionCalculatorInterface connectionCalculator;
  BallFillerInterface ballFiller;
  GameBoardInterface gameBoard;
  GestureControllerInterface gestureController;
  CoreControllerInterface coreController;
  GameEffectAdapter gameEffectAdapter;
  
  public TestGameWidget() {
    balls = new Ball[37];
    for (int i = 0;i < 37;++i)
      balls[i] = null;
    
    rule = new StandardGameRule();
    
    //ConnectionCalculatorInterface connectionCalculator = new SwapStandardConnectionCalculator();
    connectionCalculator = new RotateStandardConnectionCalculator();

    coreController = new CoreController(this, rule, balls);

    ballFiller = new StandardBallFiller(rule, 0);

    gameBoard = new ThirtySevenGameBoard();

    //gestureController = new SwapGestureController(this, rule);
    gestureController = new RotateGestureController(this, rule);

    gameEffectAdapter = new GameEffectAdapter(new EffectPainter());

    boolean rollBack = true;

    boolean autoRotate = true;

    boolean endlessFill = true;
    
    rule.setBallFiller(ballFiller);
    rule.setConnectionCalculator(connectionCalculator);
    rule.setCoreController(coreController);
    rule.setEffectPainter(gameEffectAdapter);
    rule.setGameBoard(gameBoard);
    rule.setGestureController(gestureController);
    rule.setSomeBooleans(rollBack, autoRotate, endlessFill);
  }

  /* (non-Javadoc)
   * @see VAST.HexGame.Widgets.WidgetInterface#getFocus()
   */
  @Override
  public void getFocus() {}

  /* (non-Javadoc)
   * @see VAST.HexGame.Game.GameInterface#stableConnectionTested(VAST.HexGame.Game.ConnectionInterface)
   */
  @Override
  public void stableConnectionTested(ConnectionInterface connections) {
    for (int i = 0;i < gameBoard.totalBallCount();++i)
      if (connections.isInAChain(i))
        gameEffectAdapter.highlightAt(gameBoard, i);
  }

  /* (non-Javadoc)
   * @see VAST.HexGame.Game.GameInterface#userMovingConnectionTested(VAST.HexGame.Game.ConnectionInterface)
   */
  @Override
  public void userMovingConnectionTested(ConnectionInterface connections) {
    gameEffectAdapter.clearUserMovingEliminationHints();
    for (int i = 0;i < gameBoard.totalBallCount();++i)
      if (connections.isInAChain(i))
        gameEffectAdapter.userMovingEliminationHintAt(gameBoard, i);
  }

  /* (non-Javadoc)
   * @see VAST.HexGame.Game.GameInterface#eliminated(int)
   */
  @Override
  public void eliminated(int count) {}

  /* (non-Javadoc)
   * @see VAST.HexGame.Game.GameInterface#goodMove()
   */
  @Override
  public void goodMove() {}

  /* (non-Javadoc)
   * @see VAST.HexGame.Game.GameInterface#badMove()
   */
  @Override
  public void badMove() {}

  /* (non-Javadoc)
   * @see VAST.HexGame.Widgets.AbstractSimpleWidget#buttonClicked(int)
   */
  @Override
  public void buttonClicked(int indexOfTheButton) {}


  @Override
  public void mousePressed(Point logicalPos, int button, int mouseId) {
    super.mousePressed(logicalPos, button, mouseId);
    gestureController.pressAt(logicalPos, button, mouseId);
  }
  
  @Override
  public void mouseDragged(Point logicalPos, int button, int mouseId) {
    super.mouseDragged(logicalPos, button, mouseId);
    gestureController.dragAt(logicalPos, button, mouseId);
  }
  
  @Override
  public void mouseReleased(Point logicalPos, int button, int mouseId) {
    super.mouseReleased(logicalPos, button, mouseId);
    gestureController.releaseAt(logicalPos, button, mouseId);
  }

//  @Override
//  public int refreshInterval() {
//    return 300;
//  }
  
  @Override
  public void paint(Graphics g) {
    gestureController.advance();
    rule.getEffectPainter().advance();
    BasicPainter.paintBackGround(BasicPainter.Game37, g, width(), height(), 0);
    super.paint(g);
    BasicPainter.paintBasicBalls(rule.getGameBoard(), balls, g, frame);
    rule.getEffectPainter().paint(g);
    coreController.advance();
  }
}
