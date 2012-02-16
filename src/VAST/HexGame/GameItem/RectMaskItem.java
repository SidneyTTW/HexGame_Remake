/**
 * 
 */
package VAST.HexGame.GameItem;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import AidPackage.ImageAid;
import VAST.HexGame.Widgets.RectButtonItem;

/**
 * Class of items to mask a rectangle area. I made it a button item in order to
 * mask other buttons in the widget.
 * 
 * @author SidneyTTW
 * 
 */
public class RectMaskItem extends RectButtonItem {
  public static final Color STANDARD_COLOR = new Color(0, 0, 0, 100);

  /**
   * Constructor which create a mask with standard color.
   */
  public RectMaskItem() {
    setColor(STANDARD_COLOR);
  }

  @Override
  public void paint(Graphics g, int frame) {
    Point center = getLogicalPosition();
    Point leftUp = new Point((int) (center.getX() - getWidth() / 2),
        (int) (center.getY() - getHeight() / 2));
    g.setColor(color);
    g.fillRect(leftUp.x, leftUp.y, getWidth(), getHeight());
  }
}
