/**
 * 
 */
package VAST.HexGame.Effect;

import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Class to realize EffectPainterInterface.
 * 
 * @author SidneyTTW
 * 
 */
public class EffectPainter implements EffectPainterInterface {

  LinkedList<AbstractEffect>[] effects;

  public EffectPainter() {
    effects = new LinkedList[AbstractEffect.TOTAL_EFFECT_COUNT];
    for (int i = 0; i < AbstractEffect.TOTAL_EFFECT_COUNT; ++i) {
      effects[i] = new LinkedList<AbstractEffect>();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * VAST.HexGame.Effect.EffectPainterInterface#addEffect(VAST.HexGame.Effect
   * .AbstractEffect)
   */
  @Override
  public void addEffect(AbstractEffect effect) {
    if (effect.oneAtATime() && !effects[effect.effectId()].isEmpty())
      effects[effect.effectId()].clear();
    effects[effect.effectId()].add(effect);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Effect.EffectPainterInterface#clearEffect(int)
   */
  @Override
  public void clearEffect(int effectId) {
    effects[effectId].clear();
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Effect.EffectPainterInterface#clearAllEffects()
   */
  @Override
  public void clearAllEffects() {
    for (int i = 0; i < AbstractEffect.TOTAL_EFFECT_COUNT; ++i)
      effects[i].clear();
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Effect.EffectPainterInterface#paint(java.awt.Graphics)
   */
  @Override
  public void paint(Graphics graphics) {
    try {
      for (int i = 0; i < AbstractEffect.TOTAL_EFFECT_COUNT; ++i) {
        for (Iterator<AbstractEffect> itr = effects[i].iterator(); itr
            .hasNext();) {
          AbstractEffect effect = itr.next();
          effect.paint(graphics);
        }
      }
    } catch (java.util.ConcurrentModificationException e) {}
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Effect.EffectPainterInterface#advance()
   */
  @Override
  public void advance() {
    for (int i = 0; i < AbstractEffect.TOTAL_EFFECT_COUNT; ++i) {
      for (Iterator<AbstractEffect> itr = effects[i].iterator(); itr.hasNext();) {
        AbstractEffect effect = itr.next();
        if (!effect.advance()) {
          itr.remove();
        }
      }
    }
  }

}
