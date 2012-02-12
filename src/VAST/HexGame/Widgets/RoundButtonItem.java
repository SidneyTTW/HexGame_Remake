/**
 * 
 */
package VAST.HexGame.Widgets;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import AidPackage.ImageAid;

/**
 * Class of a round button.
 * 
 * @author SidneyTTW
 *
 */
public class RoundButtonItem extends RoundItem {
  /**
   * The text to show.
   */
  String text = "";

  /**
   * The color of the text.
   */
  Color color = Color.black;

  /**
   * @param text The new text.
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * @param color The new color.
   */
  public void setColor(Color color) {
    this.color = color;
  }
  
  /* (non-Javadoc)
   * @see VAST.HexGame.Widgets.AbstractItem#paint(java.awt.Graphics, int)
   */
  @Override
  public void paint(Graphics g, int frame) {
    super.paint(g, frame);
    Point center = (Point) getLogicalPosition().clone();
    g.setColor(color);
    if (isPressed())
      center.setLocation(center.getX() + 2, center.getY() + 2);
    ImageAid.drawText((Graphics2D) g, center, text);
  }
}
