/**
 * 
 */
package Aid;

import android.graphics.Typeface;

/**
 * Class to replace the Font in awt.
 * 
 * @author SidneyTTW
 * 
 */
public class MyFont {
  public static final int BOLD = Typeface.BOLD;
  public static final int PLAIN = Typeface.NORMAL;
  Typeface font;
  int size;

  public MyFont(Typeface font, int size) {
    this.font = font;
    this.size = size;
  }

  public MyFont(String type, int format, int size) {
    font = Typeface.create(Typeface.DEFAULT, format);
    this.size = size;
  }

}
