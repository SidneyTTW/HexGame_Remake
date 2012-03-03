/**
 * 
 */
package VAST.HexGame.Effect;

import Aid.MyColor;
import Aid.MyTileMode;
import Aid.MyGraphics;
import Aid.MyLinearGradientPaint;
import Aid.MyPoint;
import Aid.MyStroke;

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
  public LightningEffect(MyPoint center, MyPoint[] gradientOffsets,
      MyPoint[] lineOffsets, float[] dist, MyColor[] colors, int limit) {
    info = new LightningEffectPrivateInfo(center, gradientOffsets, lineOffsets,
        dist, colors, limit);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Effect.AbstractEffect#paint(java.awt.Graphics)
   */
  @Override
  public void paint(MyGraphics graphics) {
    LightningEffectPrivateInfo myInfo = (LightningEffectPrivateInfo) info;
    MyStroke oldStroke = graphics.getStroke();
    for (int i = 0; i < myInfo.gradientOffsets.length; ++i) {
      MyPoint to = myInfo.center.clone();
      to.translate(myInfo.gradientOffsets[i].x, myInfo.gradientOffsets[i].y);
      MyLinearGradientPaint gradient = new MyLinearGradientPaint(myInfo.center,
          to, myInfo.dist, myInfo.colors, MyTileMode.REFLECT);
      int width = 2 * (int) (new MyPoint(0, 0))
          .distance(myInfo.gradientOffsets[i]);
      graphics.setStroke(new MyStroke(width, MyStroke.CAP_SQUARE,
          MyStroke.JOIN_ROUND));
      graphics.setPaint(gradient);
      MyPoint linePoint1 = myInfo.center.clone();
      linePoint1.translate(
          myInfo.lineOffsets[i].x * myInfo.getAge() / myInfo.getLimit(),
          myInfo.lineOffsets[i].y * myInfo.getAge() / myInfo.getLimit());
      MyPoint linePoint2 = myInfo.center.clone();
      linePoint2.translate(
          -myInfo.lineOffsets[i].x * myInfo.getAge() / myInfo.getLimit(),
          -myInfo.lineOffsets[i].y * myInfo.getAge() / myInfo.getLimit());
      graphics.drawLine(linePoint1.x, linePoint1.y, linePoint2.x, linePoint2.y);
    }
    graphics.setStroke(oldStroke);
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
    private MyPoint center;

    /**
     * The offsets of the gradient.
     */
    private MyPoint[] gradientOffsets;

    /**
     * The offsets of the lines.
     */
    private MyPoint[] lineOffsets;

    /**
     * The distance of each color.
     */
    private float[] dist;

    /**
     * The color of each distance.
     */
    private MyColor[] colors;

    public LightningEffectPrivateInfo(MyPoint center,
        MyPoint[] gradientOffsets, MyPoint[] lineOffsets, float[] dist,
        MyColor[] colors, int limit) {
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
    public void setOffsets(MyPoint[] gradientOffsets, MyPoint[] lineOffsets) {
      this.gradientOffsets = gradientOffsets;
      this.lineOffsets = lineOffsets;
    }

    /**
     * Get the offsets of gradient.
     */
    public MyPoint[] getGradientOffsets() {
      return gradientOffsets;
    }

    /**
     * Get the offsets of line.
     */
    public MyPoint[] getLineOffsets() {
      return lineOffsets;
    }

    /**
     * Set the center.
     */
    public void setCenter(MyPoint center) {
      this.center = center;
    }

    /**
     * Get the center.
     */
    public MyPoint getCenter() {
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
    public void setColors(MyColor[] colors) {
      this.colors = colors;
    }

    /**
     * Get the color.
     */
    public MyColor[] getColors() {
      return colors;
    }
  }
}
