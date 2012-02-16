/**
 * 
 */
package AidPackage;

import java.awt.Point;

/**
 * Several functions which helps to calculate.
 * 
 * @author SidneyTTW
 * 
 */
public class MathAid {
  /**
   * PI.
   */
  public static final double PI = Math.PI;

  /**
   * Returns the angle of the point according to the origin. Up will be the
   * positive direction of y. Right will be the positive direction of x. The
   * angle will be counterclockwise.
   * 
   * @param point
   *          The point.
   * @param origin
   *          The origin.
   * @return The angle(in [0, 2*PI)).
   */
  public static double angle(Point point, Point origin) {
    double dx = point.getX() - origin.getX();
    double dy = origin.getY() - point.getY();
    double result = Math.atan(Math.abs(dy / dx));
    if (dx < 0 && dy >= 0)
      return PI - result;
    if (dx <= 0 && dy < 0)
      return PI + result;
    if (dx > 0 && dy < 0)
      return 2 * PI - result;
    if (result != result) // NaN !!!
      return 0;
    return result;
  }

  /**
   * Distance from the center with the angle in a hexagon.Helps to locate the
   * balls.
   * 
   * @param angle
   *          The angle.
   * @param maxR
   *          The max radius.
   * @return Distance of two points.
   */
  public static double distanceFromTheCenterWithTheAngle(double angle,
      double maxR) {
    while (angle < 0)
      angle += PI / 3;
    while (angle >= PI / 3)
      angle -= PI / 3;
    return maxR * Math.sin(PI / 3) / Math.sin(2 * PI / 3 - angle);
  }

  /**
   * Returns the position according to the angle, radius and origin. Up will be
   * the positive direction of y. Right will be the positive direction of x. The
   * angle will be counterclockwise.
   * 
   * @param a
   *          The angle.
   * @param r
   *          The radius.
   * @param origin
   *          The origin.
   * @return The position.
   */
  public static Point calculatePosition(double a, double r, Point origin) {
    double dx = Math.cos(a) * r;
    double dy = -Math.sin(a) * r;
    if ((PI - a < 0.02 && PI - a > -0.02)) {
      if (dx < 0)
        dx = -r;
      else
        dx = r;
      dy = 0;
    }
    double x = dx + origin.getX();
    double y = dy + origin.getY();
    return new Point((int) x, (int) y);
  }

  /**
   * Returns the scale in Y direction in a curve. When the translate path is ^,
   * suppose that the distance between begin position and end position is 1,
   * inputs the x position, returns the y position(I should draw a picture to
   * show how it works-.-)
   * 
   * @param bridgeX
   *          The position in X direction.
   * @return The position in Y direction.
   */
  public static double bridgeY(double bridgeX) {
    if (bridgeX <= 0 || bridgeX >= 1)
      return 0;
    double dx = Math.abs(0.5 - bridgeX);
    return Math.sqrt(1 - Math.pow(dx, 2.0)) - 0.8660;
  }

  /**
   * @param from
   *          The start position.
   * @param to
   *          The destination position.
   * @param percentage
   *          The percentage of the progress.
   * @return The middle Position of the two points.
   */
  public static Point midPosition(Point from, Point to, double percentage) {
    double midX = from.x * (1 - percentage) + to.x * percentage;
    double midY = from.y * (1 - percentage) + to.y * percentage;
    return new Point((int) midX, (int) midY);
  }
}
