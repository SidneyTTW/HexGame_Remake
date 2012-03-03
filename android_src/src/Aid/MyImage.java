/**
 * 
 */
package Aid;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;

/**
 * Class to package image.
 * 
 * @author SidneyTTW
 * 
 */
public class MyImage {
  public static Config TYPE_4BYTE_ABGR = Bitmap.Config.ARGB_8888;
  Bitmap image;

  public MyImage(Bitmap image) {
    this.image = image;
  }

  public MyImage(int width, int height, Config type) {
    image = Bitmap.createBitmap(width, height, type);
  }

  public int getWidth() {
    return image.getWidth();
  }

  public int getHeight() {
    return image.getHeight();
  }

  public MyGraphics getGraphics() {
    return new MyGraphics(new Canvas(image));
  }

  public void recycle() {
    image.recycle();
  }
}
