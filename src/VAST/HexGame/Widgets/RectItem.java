/**
 * 
 */
package VAST.HexGame.Widgets;

import AidPackage.MyPoint;

/**
 * Class of a rectangle item with width and height.
 * 
 * @author SidneyTTW
 *
 */
public class RectItem extends AbstractItem {
  /**
   * Width of the item.
   */
  private int width = 0;
  
  /**
   * Height of the item.
   */
  private int height = 0;
  
  /**
   * Set the width of the item.
   * @param width The width of the item.
   */
  public void setWidth(int width) {
    this.width = width;
  }
  
  /**
   * Set the height of the item.
   * @param height The height of the item.
   */
  public void setHeight(int height) {
    this.height = height;
  }
  
  /**
   * @return the width
   */
  public int getWidth() {
    return width;
  }

  /**
   * @return the height
   */
  public int getHeight() {
    return height;
  }

  /* (non-Javadoc)
   * @see VAST.HexGame.Widgets.ItemInterface#isIn(java.awt.Point)
   */
  @Override
  public boolean isIn(MyPoint logicalPosition) {
    MyPoint center = getLogicalPosition();
    return (logicalPosition.x >= center.x - width / 2) &&
           (logicalPosition.x <= center.x + width / 2) &&
           (logicalPosition.y >= center.y - height / 2) &&
           (logicalPosition.y <= center.y + height / 2);
  }
}
