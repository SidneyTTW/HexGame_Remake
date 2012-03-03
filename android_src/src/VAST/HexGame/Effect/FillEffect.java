/**
 * 
 */
package VAST.HexGame.Effect;

import Aid.MyColor;
import Aid.MyGraphics;
import Aid.MyPolygon;

/**
 * Class of an effect to show a fill.
 * 
 * @author SidneyTTW
 * 
 */
public class FillEffect extends AbstractEffect {
  /**
   * Constructor.
   * 
   * @param border
   *          The border.
   * @param color
   *          The color.
   */
  public FillEffect(MyPolygon border, MyColor color) {
    this.info = new FillEffectPrivateInfo(border, color);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Effect.AbstractEffect#paint(java.awt.Graphics)
   */
  @Override
  public void paint(MyGraphics graphics) {
    FillEffectPrivateInfo myInfo = (FillEffectPrivateInfo) info;
    graphics.setColor(myInfo.color);
    graphics.fillPolygon(myInfo.border);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Effect.AbstractEffect#effectId()
   */
  @Override
  public int effectId() {
    return IdFill;
  }

  /**
   * Class of information of fill effect.
   */
  public class FillEffectPrivateInfo extends AbstractEffectPrivateInfo {
    /**
     * The border of the effect.
     */
    private MyPolygon border;

    /**
     * The color of the effect.
     */
    private MyColor color;

    /**
     * Constructor
     * 
     * @param border
     *          The border.
     * @param color
     *          The color.
     */
    public FillEffectPrivateInfo(MyPolygon border, MyColor color) {
      this.border = border;
      this.color = color;
    }

    /**
     * Set the border.
     */
    public void setBorder(MyPolygon border) {
      this.border = border;
    }

    /**
     * Get the border.
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
