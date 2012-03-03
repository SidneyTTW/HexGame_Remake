package Aid;

import java.io.InputStream;
import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

/**
 * @author SidneyTTW
 * 
 */
public class ImageAid {
  /**
   * Initialize images of a thing.
   */
  public static Vector<MyImage> loadFromFile(int id) {
    InputStream is = SourceManagement.r.openRawResource(id);
    Bitmap bitmap;
    BitmapDrawable bd = new BitmapDrawable(is);
    bitmap = bd.getBitmap();
    Vector<MyImage> result = new Vector<MyImage>();
    result.add(new MyImage(bitmap));
    return result;
  }

  /**
   * Initialize images of some thing, each thing has only one image.
   */
  public static Vector<MyImage> loadFromFile(int[] ids) {
    Vector<MyImage> result = new Vector<MyImage>();
    for (int i = 0; i < ids.length; ++i) {
      InputStream is = SourceManagement.r.openRawResource(ids[i]);
      Bitmap bitmap;
      BitmapDrawable bd = new BitmapDrawable(is);
      bitmap = bd.getBitmap();
      result.add(new MyImage(bitmap));
    }
    return result;
  }

  /**
   * Initialize images of some things, each thing has several images.
   */
  public static void loadFromFile(int[] ids, Vector<Vector<MyImage>> images,
      Vector<Integer> frameCounts) {
    int count = ids.length;
    images.clear();
    images.ensureCapacity(count);
    frameCounts.clear();
    frameCounts.ensureCapacity(count);
    for (int i = 0; i < count; ++i) {
      Vector<MyImage> currentImages = loadFromFile(ids[i]);
      images.add(currentImages);
      frameCounts.add(currentImages.size());
    }
  }

  public static void drawText(MyGraphics graphics, MyPoint centerPosition,
      String text, float rotation) {
    graphics.translate(centerPosition.x, centerPosition.y);
    graphics.rotate(rotation);
    float stringWidth = graphics.stringWidth(text);
    float stringHeight = graphics.stringHeight();
    graphics.drawString(text, -stringWidth / 2, stringHeight / 2);
    graphics.rotate(-rotation);
    graphics.translate(-centerPosition.x, -centerPosition.y);
  }

  public static void drawText(MyGraphics graphics, MyPoint centerPosition,
      String text) {
    float stringWidth = graphics.stringWidth(text);
    float stringHeight = graphics.stringHeight();
    graphics.drawString(text, centerPosition.x - stringWidth / 2,
        centerPosition.y + stringHeight / 2);
  }

  /**
   * Draw a image with some options.
   */
  public static void drawImageAt(MyGraphics graphics, MyImage image,
      double xRate, double yRate, MyPoint pos, boolean resize, boolean center,
      double rotation) {
    if (image.image == null)
      return;

    // Calculate the width and height the image should be
    int width = image.getWidth();
    int height = image.getHeight();

    if (width < 1 || height < 1)
      return;

    if (xRate >= 0.999 && xRate <= 1.001 && yRate >= 0.999 && yRate <= 1.001)
      resize = false;

    if (resize) {
      width *= xRate;
      height *= yRate;
    }

    graphics.translate(pos.x, pos.y);
    graphics.rotate((float) rotation);

    // Calculate the left up position of the image
    MyPoint leftUp = new MyPoint(0, 0);
    if (center)
      leftUp = new MyPoint(-width / 2, -height / 2);

    graphics.translate((int) leftUp.x, (int) leftUp.y);
    graphics.scale((float) xRate, (float) yRate);

    // Draw the image
    graphics.drawImage(image, 0, 0);

    graphics.scale((float) (1.0 / xRate), (float) (1.0 / yRate));
    graphics.translate(-leftUp.x, -leftUp.y);

    graphics.rotate((float) -rotation);
    graphics.translate(-pos.x, -pos.y);
  }

  /**
   * Draw a image with some options.
   */
  public static void drawImageAt(MyGraphics graphics, MyImage image,
      double xRate, double yRate, MyPoint pos, boolean resize, boolean center) {
    if (image.image == null)
      return;

    // Calculate the width and height the image should be
    int width = image.getWidth();
    int height = image.getHeight();

    if (width < 1 || height < 1)
      return;

    if (xRate >= 0.999 && xRate <= 1.001 && yRate >= 0.999 && yRate <= 1.001)
      resize = false;

    if (resize) {
      width *= xRate;
      height *= yRate;
    }

    // Calculate the left up position of the pixmap
    MyPoint leftUp = pos;
    if (center) {
      leftUp = new MyPoint(leftUp.x - width / 2, leftUp.y - height / 2);
    }

    graphics.translate(leftUp.x, leftUp.y);
    graphics.scale((float) xRate, (float) yRate);

    // Draw the pixmap
    graphics.drawImage(image, 0, 0);

    graphics.scale((float) (1.0 / xRate), (float) (1.0 / yRate));
    graphics.translate(-leftUp.x, -leftUp.y);
  }

}
