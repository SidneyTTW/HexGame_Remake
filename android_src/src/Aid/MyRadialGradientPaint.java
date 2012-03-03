/**
 * 
 */
package Aid;

import android.graphics.RadialGradient;

/**
 * Class to replace the RadialGradientPaint in awt.
 * 
 * @author SidneyTTW
 * 
 */
public class MyRadialGradientPaint {
  RadialGradient shader;

  public MyRadialGradientPaint(RadialGradient shader) {
    this.shader = shader;
  }

  public MyRadialGradientPaint(MyPoint center, int r, float[] dist,
      MyColor[] colors, MyTileMode mode) {
    int[] c = new int[colors.length];
    for (int i = 0; i < colors.length; ++i)
      c[i] = colors[i].color;

    shader = new RadialGradient(center.x, center.y, r, c, dist, mode.mode);
  }
}
