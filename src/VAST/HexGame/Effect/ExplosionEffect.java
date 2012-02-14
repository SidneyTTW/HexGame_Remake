/**
 * 
 */
package VAST.HexGame.Effect;

import java.awt.Color;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

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
  public ExplosionEffect(Point center, int radios, float[] dist,
      Color[] colors, int limit) {
    info = new ExplosionEffectPrivateInfo(center, radios, dist, colors, limit);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Effect.AbstractEffect#paint(java.awt.Graphics)
   */
  @Override
  public void paint(Graphics graphics) {
    Graphics2D g2d = (Graphics2D) graphics;
    ExplosionEffectPrivateInfo myInfo = (ExplosionEffectPrivateInfo) info;
    int r = myInfo.radios * myInfo.getAge() / myInfo.getLimit();
    RadialGradientPaint gradient = new RadialGradientPaint(myInfo.center, r,
        myInfo.dist, myInfo.colors, CycleMethod.NO_CYCLE);
    g2d.setPaint(gradient);
    g2d.fillOval((int) myInfo.center.getX() - r / 2, (int) myInfo.center.getY()
        - r / 2, r, r);
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
    private Point center;

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
    private Color[] colors;

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
    public ExplosionEffectPrivateInfo(Point center, int radios, float[] dist,
        Color[] colors, int limit) {
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
    public void setCenter(Point center) {
      this.center = center;
    }

    /**
     * @return The center.
     */
    public Point getCenter() {
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
    public void setColors(Color[] colors) {
      this.colors = colors;
    }

    /**
     * @return The colors.
     */
    public Color[] getColors() {
      return colors;
    }
  }

}
