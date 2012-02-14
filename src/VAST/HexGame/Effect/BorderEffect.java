/**
 * 
 */
package VAST.HexGame.Effect;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;

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
  public BorderEffect(Polygon border, Color color, int width) {
    this.info = new BorderEffectPrivateInfo(border, color, width);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Effect.AbstractEffect#paint(java.awt.Graphics)
   */
  @Override
  public void paint(Graphics graphics) {
    Graphics2D g2d = (Graphics2D) graphics;
    Stroke oldStroke = g2d.getStroke();
    BorderEffectPrivateInfo myInfo = (BorderEffectPrivateInfo) info;
    g2d.setStroke(new BasicStroke(myInfo.width, BasicStroke.CAP_SQUARE,
        BasicStroke.JOIN_ROUND));
    g2d.setColor(myInfo.color);
    g2d.drawPolygon(myInfo.border);
    g2d.setStroke(oldStroke);
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
    private Polygon border;

    /**
     * The color of the border.
     */
    private Color color;

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
    public BorderEffectPrivateInfo(Polygon border, Color color, int width) {
      this.border = border;
      this.color = color;
      this.width = width;
    }

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
  }
}
