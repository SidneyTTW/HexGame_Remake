/**
 * 
 */
package VAST.HexGame.Effect;

/**
 * Abstract class of long last effect. 
 * 
 * @author SidneyTTW
 *
 */
public abstract class AbstractLongLastEffect extends AbstractEffect {
  /* (non-Javadoc)
   * @see VAST.HexGame.Effect.AbstractEffect#advance()
   */
  @Override
  public boolean advance() {
    return true;
  }
}
