/**
 * 
 */
package VAST.HexGame.Effect;

import Aid.MyColor;
import Aid.MyGraphics;
import Aid.MyPolygon;
import Aid.MyStroke;

/**
 * Class of an effect to show a polygon border.
 * 
 * @author SidneyTTW
 * 
 */
public class BorderEffect extends AbstractEffect {
  /**
   * Constructor
   * 
   * @param border
   *          The border.
   * @param color
   *          The color.
   */
  public BorderEffect(MyPolygon border, MyColor color, int width) {
    this.info = new BorderEffectPrivateInfo(border, color, width);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Effect.AbstractEffect#paint(java.awt.Graphics)
   */
  @Override
  public void paint(MyGraphics graphics) {
    MyStroke oldStroke = graphics.getStroke();
    BorderEffectPrivateInfo myInfo = (BorderEffectPrivateInfo) info;
    graphics.setStroke(new MyStroke(myInfo.width, MyStroke.CAP_SQUARE,
        MyStroke.JOIN_ROUND));
    graphics.setColor(myInfo.color);
    graphics.drawPolygon(myInfo.border);
    graphics.setStroke(oldStroke);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Effect.AbstractEffect#effectId()
   */
  @Override
  public int effectId() {
    return IdBorder;
  }

  /**
   * Class of information of border effect.
   */
  public class BorderEffectPrivateInfo extends
      AbstractEffectPrivateInfo {
    /**
     * The border of the effect.
     */
    private MyPolygon border;

    /**
     * The color of the border.
     */
    private MyColor color;

    /**
     * The width of the border.
     */
    private int width;

    /**
     * Constructor
     * 
     * @param border
     *          The border.
     * @param color
     *          The color.
     */
    public BorderEffectPrivateInfo(MyPolygon border, MyColor color, int width) {
      this.border = border;
      this.color = color;
      this.width = width;
    }

    /**
     * Set the position.
     */
    public void setBorder(MyPolygon border) {
      this.border = border;
    }

    /**
     * Get the position.
     */
    public MyPolygon getBorder() {
      return border;
    }

    /**
     * Set the color.
     */
    public void setColor(MyColor color) {
      this.color = color;
    }

    /**
     * Get the color.
     */
    public MyColor getColor() {
      return color;
    }
  }
}
