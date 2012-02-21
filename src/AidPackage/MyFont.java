/**
 * 
 */
package AidPackage;

import java.awt.Font;

/**
 * Class to replace the Font in awt.
 * 
 * @author SidneyTTW
 * 
 */
public class MyFont {
  public static final int BOLD = Font.BOLD;
  public static final int PLAIN = Font.PLAIN;
  Font font;

  public MyFont(Font font) {
    this.font = font;
  }

  public MyFont(String type, int format, int size) {
    font = new Font(type, format, size);
  }

}
