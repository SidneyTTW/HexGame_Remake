/**
 * 
 */
package VAST.HexGame.Effect;

import java.awt.Graphics;

/**
 * Abstract class of effects.
 * 
 * @author SidneyTTW
 * 
 */
public abstract class AbstractEffect {
  public static final int IdSelection = 0;
  public static final int IdElimination = 1;
  public static final int IdExplosion = 2;
  public static final int IdLightning = 3;
  public static final int IdHighlight = 4;
  public static final int IdHint = 5;
  public static final int IdWords = 6;
  public static final int IdFlash = 7;
  public static final int TOTAL_EFFECT_COUNT = 8;
  
  /**
   * Private information of the effect.
   */
  protected AbstractEffectPrivateInfo info;
  
  /**
   * Paint the effect.
   * 
   * @param graphics
   *          The graphics.
   */
  public abstract void paint(Graphics graphics);

  /**
   * @return The id of the effect.
   */
  public abstract int effectId();

  /**
   * @return Whether only one should exist at a time.
   */
  public abstract boolean oneAtATime();
  
  /**
   * Advance the effect.
   * 
   * @return Whether it should last for more steps.
   */
  public abstract boolean advance();

  /**
   * Abstract class of information of an effect.
   */
  public abstract class AbstractEffectPrivateInfo{}
}
