/**
 * 
 */
package VAST.HexGame.Widgets;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import AidPackage.ImageAid;

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
   * 
   * @see VAST.HexGame.Widgets.ItemInterface#isIn(java.awt.Point)
   */
  @Override
  public boolean isIn(Point logicalPosition) {
    return getLogicalPosition().distance(logicalPosition) <= radius;
  }
}
