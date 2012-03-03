/**
 * 
 */
package Aid;

import android.graphics.Path;

/**
 * Class to replace the Polygon in awt.
 * 
 * @author SidneyTTW
 * 
 */
public class MyPolygon {
  Path polygon;
  boolean first;
  boolean closed;

  public MyPolygon() {
    polygon = new Path();
    closed = false;
    first = true;
  }

  public void addPoint(int x, int y) {
    if (first)
      polygon.moveTo(x, y);
    else
      polygon.lineTo(x, y);
    first = false;
  }

  public void testClosed() {
    if (!closed)
      polygon.close();
    closed = true;
  }
}
