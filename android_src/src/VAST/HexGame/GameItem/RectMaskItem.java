/**
 * 
 */
package VAST.HexGame.GameItem;

import Aid.MyColor;
import Aid.MyGraphics;
import Aid.MyPoint;
import VAST.HexGame.Widgets.RectButtonItem;

/**
 * Class of items to mask a rectangle area. I made it a button item in order to
 * mask other buttons in the widget.
 * 
 * @author SidneyTTW
 * 
 */
public class RectMaskItem extends RectButtonItem {
  public static final MyColor STANDARD_COLOR = new MyColor(0, 0, 0, 100);

  /**
   * Constructor which create a mask with standard color.
   */
  public RectMaskItem() {
    setColor(STANDARD_COLOR);
  }

  @Override
  public void paint(MyGraphics g, int frame) {
    MyPoint center = getLogicalPosition();
    MyPoint leftUp = new MyPoint(center.x - getWidth() / 2, center.y
        - getHeight() / 2);
    g.setColor(color);
    g.fillRect(leftUp.x, leftUp.y, getWidth(), getHeight());
  }
}
