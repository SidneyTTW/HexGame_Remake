/**
 * 
 */
package VAST.HexGame.Game;

import java.awt.Point;

import VAST.HexGame.Effect.EffectPainterInterface;

/**
 * An adapter to use EffectPainter in the game to satisfy the needs of effects in games
 * 
 * @author SidneyTTW
 *
 */
public class GameEffectAdapter {
  /**
   * The real effect painter.
   */
  private EffectPainterInterface effectPainter;

  /**
   * Constructor.
   */
  public GameEffectAdapter(EffectPainterInterface effectPainter) {
    this.effectPainter = effectPainter;
  }

  /**
   * Function to show that the user selected a ball.
   */
  public void selectAt(GameBoardInterface gameBoard, int index) {
    //TODO
  }

  /**
   * Function to clear the effect which shows that the user selected a
   * ball.
   */
  public void clearSelectionHints() {
    //TODO
  }

  /**
   * Function to show the elimination if user confirm the gesture.
   */
  public void bonusEliminationHintAt(GameBoardInterface gameBoard, int index) {
    //TODO
  }

  /**
   * Function to clear the effect which shows the elimination if user
   * confirm the gesture.
   */
  public void clearBonusEliminationHints() {
    //TODO
  }

  /**
   * Function to show the elimination if user confirm the gesture.
   */
  public void userMovingEliminationHintAt(GameBoardInterface gameBoard, int index) {
    //TODO
  }

  /**
   * Function to clear the effect which shows the elimintation if user
   * confirm the gesture.
   */
  public void clearUserMovingEliminationHints() {
    //TODO
  }

  /**
   * Explode at the position.
   */
  public void explodeAt(GameBoardInterface gameBoard, int index) {
    //TODO
  }

  /**
   * Lightning at the position.
   */
  public void lightningAt(GameBoardInterface gameBoard, int index) {
    //TODO
  }

  /**
   * Highlight at the position.
   */
  public void highlightAt(GameBoardInterface gameBoard, int index) {
    //TODO
  }

  /**
   * Show words at the position.
   */
  public void wordsAt(Point pos, String text, int size) {
    //TODO
  }

  /**
   * Flash the whole screen.
   */
  public void flash() {
    //TODO
  }

  /**
   * Show a hint at the position.
   */
  public void hintAt(Point pos, int hintType) {
    //TODO
  }
  
  /**
   * Advance the effects.
   */
  public void advance() {
    //TODO
  }
}
