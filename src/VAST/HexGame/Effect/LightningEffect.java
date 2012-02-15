/**
 * 
 */
package VAST.HexGame.Effect;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint;
import java.awt.Point;
import java.awt.LinearGradientPaint;
import java.awt.Stroke;

/**
 * Class of an effect to show a lightning.
 * 
 * @author SidneyTTW
 * 
 */
public class LightningEffect extends AbstractTimingEffect {
  /**
   * Constructor.
   * 
   * @param info
   *          The information of the effect.
   */
  public LightningEffect(Point center, Point[] gradientOffsets,
      Point[] lineOffsets, float[] dist, Color[] colors, int limit) {
    info = new LightningEffectPrivateInfo(center, gradientOffsets, lineOffsets,
        dist, colors, limit);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Effect.AbstractEffect#paint(java.awt.Graphics)
   */
  @Override
  public void paint(Graphics graphics) {
    Graphics2D g2d = (Graphics2D) graphics;
    LightningEffectPrivateInfo myInfo = (LightningEffectPrivateInfo) info;
    Stroke oldStroke = g2d.getStroke();
    for (int i = 0; i < myInfo.gradientOffsets.length; ++i) {
      Point to = (Point) myInfo.center.clone();
      to.translate(myInfo.gradientOffsets[i].x, myInfo.gradientOffsets[i].y);
      LinearGradientPaint gradient = new LinearGradientPaint(myInfo.center, to,
          myInfo.dist, myInfo.colors, MultipleGradientPaint.CycleMethod.REFLECT);
      int width = 2 * (int) (new Point(0, 0)).distance(myInfo.gradientOffsets[i]);
      g2d.setStroke(new BasicStroke(width, BasicStroke.CAP_SQUARE,
          BasicStroke.JOIN_ROUND));
      g2d.setPaint(gradient);
      Point linePoint1 = (Point) myInfo.center.clone();
      linePoint1.translate(
          myInfo.lineOffsets[i].x * myInfo.getAge() / myInfo.getLimit(),
          myInfo.lineOffsets[i].y * myInfo.getAge() / myInfo.getLimit());
      Point linePoint2 = (Point) myInfo.center.clone();
      linePoint2.translate(
          -myInfo.lineOffsets[i].x * myInfo.getAge() / myInfo.getLimit(),
          -myInfo.lineOffsets[i].y * myInfo.getAge() / myInfo.getLimit());
      g2d.drawLine(linePoint1.x, linePoint1.y, linePoint2.x, linePoint2.y);
    }
    g2d.setStroke(oldStroke);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Effect.AbstractEffect#effectId()
   */
  @Override
  public int effectId() {
    return IdLightning;
  }

  /**
   * Class of information of lightning effect.
   */
  public class LightningEffectPrivateInfo extends
      AbstractTimingEffectPrivateInfo {
    /**
     * The center of the effect.
     */
    private Point center;

    /**
     * The offsets of the gradient.
     */
    private Point[] gradientOffsets;

    /**
     * The offsets of the lines.
     */
    private Point[] lineOffsets;

    /**
     * The distance of each color.
     */
    private float[] dist;

    /**
     * The color of each distance.
     */
    private Color[] colors;

    public LightningEffectPrivateInfo(Point center, Point[] gradientOffsets,
        Point[] lineOffsets, float[] dist, Color[] colors, int limit) {
      this.center = center;
      this.gradientOffsets = gradientOffsets;
      this.lineOffsets = lineOffsets;
      this.dist = dist;
      this.colors = colors;
      setLimit(limit);
    }

    /**
     * Set the offsets.
     */
    public void setOffsets(Point[] gradientOffsets, Point[] lineOffsets) {
      this.gradientOffsets = gradientOffsets;
      this.lineOffsets = lineOffsets;
    }

    /**
     * Get the offsets of gradient.
     */
    public Point[] getGradientOffsets() {
      return gradientOffsets;
    }

    /**
     * Get the offsets of line.
     */
    public Point[] getLineOffsets() {
      return lineOffsets;
    }

    /**
     * Set the center.
     */
    public void setCenter(Point center) {
      this.center = center;
    }

    /**
     * Get the center.
     */
    public Point getCenter() {
      return center;
    }

    /**
     * Set the distance.
     */
    public void setDist(float[] dist) {
      this.dist = dist;
    }

    /**
     * Get the color.
     */
    public float[] getDist() {
      return dist;
    }

    /**
     * Set the color.
     */
    public void setColors(Color[] colors) {
      this.colors = colors;
    }

    /**
     * Get the color.
     */
    public Color[] getColors() {
      return colors;
    }
  }
}
