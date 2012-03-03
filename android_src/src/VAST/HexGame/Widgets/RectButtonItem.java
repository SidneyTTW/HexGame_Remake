/**
 * 
 */
package VAST.HexGame.Widgets;

import Aid.ImageAid;
import Aid.MyColor;
import Aid.MyFont;
import Aid.MyGraphics;
import Aid.MyPoint;

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
  protected MyColor color = MyColor.black;

  /**
   * The color of the text.
   */
  protected MyFont font = new MyFont("Default", MyFont.BOLD, 15);

  /**
   * @param text
   *          The new text.
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * @param color
   *          The new color.
   */
  public void setColor(MyColor color) {
    this.color = color;
  }

  /**
   * @return the font
   */
  public MyFont getFont() {
    return font;
  }

  /**
   * @param font
   *          the font to set
   */
  public void setFont(MyFont font) {
    this.font = font;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.AbstractItem#paint(java.awt.Graphics, int)
   */
  @Override
  public void paint(MyGraphics g, int frame) {
    super.paint(g, frame);
    MyPoint center = (MyPoint) getLogicalPosition().clone();
    MyFont lastFont = g.getFont();
    g.setFont(font);
    g.setColor(color);
    if (isPressed())
      center.translate(2, 2);
    ImageAid.drawText(g, center, text, rotation);
    g.setFont(lastFont);
  }
}
