/**
 * 
 */
package VAST.HexGame.Widgets;

import java.awt.Point;

/**
 * Class of a round item with radius.
 * 
 * @author SidneyTTW
 *
 */
public class RoundItem extends AbstractItem {
  /**
   * Width of the item.
   */
  private int radius = 0;
  
  /**
   * Set the radius of the item.
   * @param radius The radius of the item.
   */
  public void setRadius(int radius) {
    this.radius = radius;
  }

  /* (non-Javadoc)
   * @see VAST.HexGame.Widgets.ItemInterface#isIn(java.awt.Point)
   */
  @Override
  public boolean isIn(Point logicalPosition) {
    return getLogicalPosition().distance(logicalPosition) <= radius;
  }
}
