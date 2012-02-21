/**
 * 
 */
package AidPackage;

import java.awt.Polygon;

/**
 * Class to replace the Polygon in awt.
 * 
 * @author SidneyTTW
 * 
 */
public class MyPolygon {
  Polygon polygon;

  public MyPolygon(Polygon polygon) {
    this.polygon = polygon;
  }

  public MyPolygon() {
    polygon = new Polygon();
  }

  public void addPoint(int x, int y) {
    polygon.addPoint(x, y);
  }

}
