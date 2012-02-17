/**
 * 
 */
package VAST.HexGame.Widgets;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import AidPackage.ImageAid;

/**
 * Class of a rectangle button.
 * 
 * @author SidneyTTW
 *
 */
public class RectButtonItem extends RectItem {
  /**
   * The text to show.
   */
  protected String text = "";

  /**
   * The color of the text.
   */
  protected Color color = Color.black;

  /**
   * The color of the text.
   */
  protected Font font = new Font("Default",Font.BOLD, 15);

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
  
  /**
   * @return the font
   */
  public Font getFont() {
    return font;
  }

  /**
   * @param font the font to set
   */
  public void setFont(Font font) {
    this.font = font;
  }

  /* (non-Javadoc)
   * @see VAST.HexGame.Widgets.AbstractItem#paint(java.awt.Graphics, int)
   */
  @Override
  public void paint(Graphics g, int frame) {
    super.paint(g, frame);
    Point center = (Point) getLogicalPosition().clone();
    Font lastFont = g.getFont();
    g.setFont(font);
    g.setColor(color);
    if (isPressed())
      center.setLocation(center.getX() + 2, center.getY() + 2);
    ImageAid.drawText((Graphics2D) g, center, text, rotation);
    g.setFont(lastFont);
  }
}
