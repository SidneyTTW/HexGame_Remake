/**
 * 
 */
package VAST.HexGame.Widgets;

import Aid.MyGraphics;
import Aid.MyPoint;

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
  public void paintShadow(MyPoint position, MyGraphics g, int frame);
}
