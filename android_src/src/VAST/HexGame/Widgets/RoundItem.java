/**
 * 
 */
package VAST.HexGame.Widgets;

import Aid.MyPoint;

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
   * @param radius
   *          The radius of the item.
   */
  public void setRadius(int radius) {
    this.radius = radius;
  }

  /**
   * @return The radius of the item.
   */
  public int getRadius() {
    return radius;
  }

  /*
   * (non-Javadoc)
   */
  @Override
  public boolean isIn(MyPoint logicalPosition) {
    return getLogicalPosition().distance(logicalPosition) <= radius;
  }
}
