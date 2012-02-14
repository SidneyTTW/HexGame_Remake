/**
 * 
 */
package VAST.HexGame.Effect;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

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
  public FillEffect(Polygon border, Color color) {
    this.info = new FillEffectPrivateInfo(border, color);
  }

  /**
   * The border of the effect.
   */
  private Polygon border;

  /**
   * The color of the effect.
   */
  private Color color;

  /**
   * Set the position.
   */
  public void setBorder(Polygon border) {
    this.border = border;
  }

  /**
   * Get the position.
   */
  public Polygon getBorder() {
    return border;
  }

  /**
   * Set the color.
   */
  public void setColor(Color color) {
    this.color = color;
  }

  /**
   * Get the color.
   */
  public Color getColor() {
    return color;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Effect.AbstractEffect#paint(java.awt.Graphics)
   */
  @Override
  public void paint(Graphics graphics) {
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
    private Polygon border;

    /**
     * The color of the effect.
     */
    private Color color;

    /**
     * Constructor
     * 
     * @param border
     *          The border.
     * @param color
     *          The color.
     */
    public FillEffectPrivateInfo(Polygon border, Color color) {
      this.border = border;
      this.color = color;
    }

    /**
     * Set the border.
     */
    public void setBorder(Polygon border) {
      this.border = border;
    }

    /**
     * Get the border.
     */
    public Polygon getBorder() {
      return border;
    }

    /**
     * Set the color.
     */
    public void setColor(Color color) {
      this.color = color;
    }

    /**
     * Get the color.
     */
    public Color getColor() {
      return color;
    }
  }
}
