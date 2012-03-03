/**
 * 
 */
package VAST.HexGame.Game;

/**
 * Interface of game rule, will have functions to return policy of each step.
 * 
 * @author SidneyTTW
 * 
 */
public interface GameRuleInterface {
  /**
   * @return The connection calculator.
   */
  ConnectionCalculatorInterface getConnectionCalculator();

  /**
   * @return The core calculator.
   */
  CoreControllerInterface getCoreController();

  /**
   * @return The ball filler.
   */
  BallFillerInterface getBallFiller();

  /**
   * @return The game board.
   */
  GameBoardInterface getGameBoard();

  /**
   * @return The gesture controller.
   */
  GestureControllerInterface getGestureController();

  /**
   * @return The effect painter.
   */
  GameEffectAdapter getEffectPainter();

  /**
   * @return Whether to roll back when there's no connection.
   */
  boolean rollBackWhenNoConnection();

  /**
   * @return Whether to rotate automatically.
   */
  boolean autoRotate();

  /**
   * @return Whether the fill will ensure the move.
   */
  boolean endlessFill();
}
