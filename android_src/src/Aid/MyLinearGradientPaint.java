/**
 * 
 */
package Aid;

import android.graphics.LinearGradient;

/**
 * Class to replace the LinearGradientPaint in awt.
 * 
 * @author SidneyTTW
 * 
 */
public class MyLinearGradientPaint {
  LinearGradient shader;

  public MyLinearGradientPaint(LinearGradient shader) {
    this.shader = shader;
  }

  public MyLinearGradientPaint(MyPoint center, MyPoint to, float[] dist,
      MyColor[] colors, MyTileMode mode) {
    int[] c = new int[colors.length];
    for (int i = 0; i < colors.length; ++i)
      c[i] = colors[i].color;
    shader = new LinearGradient(center.x, center.y, to.x, to.y, c, dist,
        mode.mode);
  }
}
