/**
 * 
 */
package AidPackage;

import java.awt.Color;
import java.awt.Point;
import java.awt.RadialGradientPaint;

/**
 * Class to replace the RadialGradientPaint in awt.
 * 
 * @author SidneyTTW
 * 
 */
public class MyRadialGradientPaint {
  RadialGradientPaint paint;

  public MyRadialGradientPaint(RadialGradientPaint paint) {
    this.paint = paint;
  }

  public MyRadialGradientPaint(MyPoint center, int r, float[] dist,
      MyColor[] colors, MyCycleMethod cycleMethod) {
    Color[] c = new Color[colors.length];
    for (int i = 0; i < colors.length; ++i)
      c[i] = colors[i].color;

    paint = new RadialGradientPaint(new Point(center.x, center.y), r, dist, c,
        cycleMethod.cycleMethod);
  }
}
