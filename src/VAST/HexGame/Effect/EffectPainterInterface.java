/**
 * 
 */
package VAST.HexGame.Effect;

import AidPackage.MyGraphics;

/**
 * Interface of effect painter
 * 
 * @author SidneyTTW
 * 
 */
public interface EffectPainterInterface {
  /**
   * Add an effect.
   * 
   * @param effect
   *          The effect.
   */
  public void addEffect(AbstractEffect effect);
  
  /**
   * Clear a type of effect.
   * 
   * @param effectId
   *          Type of the effect.
   */
  public void clearEffect(int effectId);
  
  /**
   * Clear all types of effect.
   */
  public void clearAllEffects();

  /**
   * Paint all the effects.
   * 
   * @param graphics
   *          The graphics.
   */
  public void paint(MyGraphics graphics);
  
  /**
   * Advance the effects.
   */
  public void advance();
}
