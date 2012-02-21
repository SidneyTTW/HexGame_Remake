/**
 * 
 */
package AidPackage;

import java.awt.AlphaComposite;
import java.awt.Composite;

/**
 * Class to replace the AlphaComposite in awt.
 * 
 * @author SidneyTTW
 * 
 */
public class MyComposite {
  Composite composite;

  public static final int SRC_ATOP = AlphaComposite.SRC_ATOP;
  
  public MyComposite(Composite composite) {
    this.composite= composite;
  }

  public float getAlpha() {
    if (composite instanceof AlphaComposite)
      return ((AlphaComposite) composite).getAlpha();
    return 1.0f;
  }

  public static MyComposite getInstance(int srcAtop, float alpha) {
    return new MyComposite(AlphaComposite.getInstance(srcAtop, alpha));
  }

}
