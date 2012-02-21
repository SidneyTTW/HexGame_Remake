/**
 * 
 */
package AidPackage;

import java.awt.BasicStroke;
import java.awt.Stroke;

/**
 * Class to replace the Stroke in awt.
 * 
 * @author SidneyTTW
 * 
 */
public class MyStroke {
  public static final int CAP_SQUARE = BasicStroke.CAP_SQUARE;
  public static final int JOIN_ROUND = BasicStroke.JOIN_ROUND;
  Stroke stroke;

  public MyStroke(Stroke Stroke) {
    this.stroke = Stroke;
  }

  public MyStroke(int width, int cap, int joint) {
    stroke = new BasicStroke(width, cap, joint);
  }

}
