/**
 * 
 */
package VAST.HexGame.Widgets;

import java.awt.Graphics;
import java.awt.Point;

/**
 * Interface of draggable items.
 * 
 * @author SidneyTTW
 *
 */
public interface DraggableItemInterface extends ItemInterface {
  /**
   * Function to paint the shadow of the item.
   * 
   * @param position
   *          The position to paint.
   * @param g
   *          The graphics to paint on.
   * @param frame
   *          The frame to paint.
   */
  public void paintShadow(Point position, Graphics g, int frame);
}
