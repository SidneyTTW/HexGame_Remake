/**
 * 
 */
package AidPackage;

import java.awt.Color;
import java.awt.LinearGradientPaint;
import java.awt.Point;

/**
 * Class to replace the LinearGradientPaint in awt.
 * 
 * @author SidneyTTW
 * 
 */
public class MyLinearGradientPaint {
  LinearGradientPaint paint;

  public MyLinearGradientPaint(LinearGradientPaint paint) {
    this.paint = paint;
  }

  public MyLinearGradientPaint(MyPoint center, MyPoint to, float[] dist,
      MyColor[] colors, MyCycleMethod cycleMethod) {
    Color[] c = new Color[colors.length];
    for (int i = 0; i < colors.length; ++i)
      c[i] = colors[i].color;
    paint = new LinearGradientPaint(new Point(center.x, center.y), new Point(
        to.x, to.y), dist, c, cycleMethod.cycleMethod);
  }
}
