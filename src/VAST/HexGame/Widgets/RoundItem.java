/**
 * 
 */
package VAST.HexGame.Widgets;

import java.awt.Graphics;
import java.awt.Graphics2D;
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
   * Rotation of the item.
   */
  private int rotation = 0;
  
  /**
   * @param radius The radius of the item.
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

  /**
   * @param rotation The rotation to set.
   */
  public void setRotation(int rotation) {
    this.rotation = rotation;
  }
  
  @Override
  public void paint(Graphics g, int frame) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.rotate(rotation);
    super.paint(g2d, frame);
    g2d.rotate(-rotation);
  }

  /* (non-Javadoc)
   * @see VAST.HexGame.Widgets.ItemInterface#isIn(java.awt.Point)
   */
  @Override
  public boolean isIn(Point logicalPosition) {
    return getLogicalPosition().distance(logicalPosition) <= radius;
  }
}
