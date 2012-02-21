/**
 * 
 */
package AidPackage;

/**
 * Class to replace the Point in awt.
 * 
 * @author SidneyTTW
 * 
 */
public class MyPoint {
  public int x;
  public int y;

  public MyPoint() {
    x = 0;
    y = 0;
  }

  public MyPoint(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int distance(MyPoint logicalPosition) {
    int dx = logicalPosition.x - this.x;
    int dy = logicalPosition.y - this.y;
    return (int) Math.sqrt(dx * dx + dy * dy);
  }

  public MyPoint clone() {
    return new MyPoint(x, y);
  }

  public void translate(int x, int y) {
    this.x += x;
    this.y += y;
  }
}
