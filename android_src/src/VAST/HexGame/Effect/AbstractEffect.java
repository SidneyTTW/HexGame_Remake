/**
 * 
 */
package VAST.HexGame.Effect;

import Aid.MyGraphics;

/**
 * Abstract class of effects.
 * 
 * @author SidneyTTW
 * 
 */
public abstract class AbstractEffect {
  public static final int IdBorder = 0;
  public static final int IdFill = 1;
  public static final int IdExplosion = 2;
  public static final int IdLightning = 3;
  public static final int IdHint = 4;
  public static final int IdWords = 5;
  public static final int IdFlash = 6;
  public static final int TOTAL_EFFECT_COUNT = 7;
  
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
  public abstract void paint(MyGraphics graphics);

  /**
   * @return The id of the effect.
   */
  public abstract int effectId();

  /**
   * @return Whether only one should exist at a time.
   */
  public boolean oneAtATime() {
    return false;
  }
  
  /**
   * Advance the effect.
   * 
   * @return Whether it should last for more steps.
   */
  public boolean advance() {
    return true;
  }

  /**
   * Abstract class of information of an effect.
   */
  public abstract class AbstractEffectPrivateInfo{}
}
