/**
 * 
 */
package Aid;

import android.graphics.Color;

/**
 * Class to replace the Color in awt.
 * 
 * @author SidneyTTW
 * 
 */
public class MyColor {
  public static final MyColor black = new MyColor(Color.BLACK);
  public static final MyColor blue = new MyColor(Color.BLUE);
  public static final MyColor white = new MyColor(Color.WHITE);
  public static final MyColor yellow = new MyColor(Color.YELLOW);
  int color;

  public MyColor(int color) {
    this.color = color;
  }

  public MyColor(int r, int g, int b, int a) {
    color = Color.argb(a, r, g, b);
  }

  public MyColor(int r, int g, int b) {
    color = Color.rgb(r, g, b);
  }
  
  public MyColor(MyColor color) {
    this.color = color.color;
  }

  public int alpha() {
    return Color.alpha(color);
  }

}
