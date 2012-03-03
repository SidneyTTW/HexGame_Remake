/**
 * 
 */
package VAST.HexGame.Effect;

import Aid.MyColor;
import Aid.MyTileMode;
import Aid.MyGraphics;
import Aid.MyPoint;
import Aid.MyRadialGradientPaint;

/**
 * Class of an effect to show an explosion.
 * 
 * @author SidneyTTW
 * 
 */
public class ExplosionEffect extends AbstractTimingEffect {
  /**
   * Constructor.
   * 
   * @param center
   *          The center to set.
   * @param radios
   *          The radios to set.
   * @param distance
   *          The distances to set.
   * @param colors
   *          The colors to set.
   * @param limit
   *          The limit to set.
   */
  public ExplosionEffect(MyPoint center, int radios, float[] dist,
      MyColor[] colors, int limit) {
    info = new ExplosionEffectPrivateInfo(center, radios, dist, colors, limit);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Effect.AbstractEffect#paint(java.awt.Graphics)
   */
  @Override
  public void paint(MyGraphics graphics) {
    ExplosionEffectPrivateInfo myInfo = (ExplosionEffectPrivateInfo) info;
    int r = myInfo.radios * myInfo.getAge() / myInfo.getLimit();
    if (r <= 0)
      r = 1;
    MyRadialGradientPaint gradient = new MyRadialGradientPaint(myInfo.center,
        r, myInfo.dist, myInfo.colors, MyTileMode.NO_CYCLE);
    graphics.setPaint(gradient);
    graphics.fillOval(myInfo.center.x - r / 2, myInfo.center.y - r / 2, r, r);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Effect.AbstractEffect#effectId()
   */
  @Override
  public int effectId() {
    return IdExplosion;
  }

  /**
   * Class of information of explosion effect.
   */
  public class ExplosionEffectPrivateInfo extends
      AbstractTimingEffectPrivateInfo {
    /**
     * The center of the effect.
     */
    private MyPoint center;

    /**
     * The radios of the effect.
     */
    private int radios;

    /**
     * The distance of each color.
     */
    private float[] dist;

    /**
     * The color of each distance.
     */
    private MyColor[] colors;

    /**
     * Constructor.
     * 
     * @param center
     *          The center to set.
     * @param radios
     *          The radios to set.
     * @param distance
     *          The distances to set.
     * @param colors
     *          The colors to set.
     * @param limit
     *          The limit to set.
     */
    public ExplosionEffectPrivateInfo(MyPoint center, int radios, float[] dist,
        MyColor[] colors, int limit) {
      this.center = center;
      this.radios = radios;
      this.dist = dist;
      this.colors = colors;
      this.setLimit(limit);
    }

    /**
     * @param center
     *          The center to set.
     */
    public void setCenter(MyPoint center) {
      this.center = center;
    }

    /**
     * @return The center.
     */
    public MyPoint getCenter() {
      return center;
    }

    /**
     * @param radios
     *          The radios to set.
     */
    public void setRadios(int radios) {
      this.radios = radios;
    }

    /**
     * @return The radios.
     */
    public int getRadios() {
      return radios;
    }

    /**
     * @param distance
     *          The distances to set.
     */
    public void setDist(float[] dist) {
      this.dist = dist;
    }

    /**
     * @return The distances.
     */
    public float[] getDist() {
      return dist;
    }

    /**
     * @param colors
     *          The colors to set.
     */
    public void setColors(MyColor[] colors) {
      this.colors = colors;
    }

    /**
     * @return The colors.
     */
    public MyColor[] getColors() {
      return colors;
    }
  }

}
