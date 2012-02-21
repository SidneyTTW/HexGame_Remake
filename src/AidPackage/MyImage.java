/**
 * 
 */
package AidPackage;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * Class to package image, maybe the Image of awt.
 * 
 * Currently, it only supports Image of awt.
 * 
 * @author SidneyTTW
 * 
 */
public class MyImage {
  public static int TYPE_4BYTE_ABGR = BufferedImage.TYPE_4BYTE_ABGR;
  Image image;

  public MyImage(Image image) {
    this.image = image;
  }

  public MyImage(int width, int height, int type) {
    image = new BufferedImage(width, height, type);
  }

  public int getWidth() {
    return image.getWidth(null);
  }

  public int getHeight() {
    return image.getHeight(null);
  }

  public MyGraphics getGraphics() {
    return new MyGraphics((Graphics2D) image.getGraphics());
  }
}
