/**
 * 
 */
package AidPackage;

import java.awt.Color;

/**
 * Class to replace the Color in awt.
 * 
 * @author SidneyTTW
 * 
 */
public class MyColor {
  public static final MyColor black = new MyColor(Color.black);
  public static final MyColor blue = new MyColor(Color.blue);
  public static final MyColor white = new MyColor(Color.white);
  public static final MyColor yellow = new MyColor(Color.yellow);
  Color color;

  public MyColor(Color color) {
    this.color = color;
  }

  public MyColor(int r, int g, int b, int a) {
    color = new Color(r, g, b, a);
  }

  public MyColor(int r, int g, int b) {
    color = new Color(r, g, b);
  }

}
