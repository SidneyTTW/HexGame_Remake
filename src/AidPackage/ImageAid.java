package AidPackage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 
 */

/**
 * @author SidneyTTW
 * 
 */
public class ImageAid {
  /**
   * Initialize images of a thing.
   */
  public static Vector<MyImage> loadFromFile(String dir, String file) {
    Vector<MyImage> result = new Vector<MyImage>();
    List<File> fileList = new ArrayList<File>();
    FileSearcher.findFiles(dir, file, fileList);
    for (int i = 0; i < fileList.size(); i++) {
      result.add(new MyImage(Toolkit.getDefaultToolkit().getImage(
          fileList.get(i).getAbsolutePath())));
    }
    return result;
  }

  /**
   * Initialize images of some thing, each thing has only one image.
   */
  public static Vector<MyImage> loadFromFile(String dir, String[] files) {
    Vector<MyImage> result = new Vector<MyImage>();
    result.ensureCapacity(files.length);
    for (int i = 0; i < files.length; ++i) {
      List<File> fileList = new ArrayList<File>();
      FileSearcher.findFiles(dir, files[i], fileList);
      if (fileList.isEmpty())
        result.add(null);
      else
        result.add(new MyImage(Toolkit.getDefaultToolkit().getImage(
            fileList.get(0).getAbsolutePath())));
    }
    return result;
  }

  /**
   * Initialize images of some things, each thing has several images.
   */
  public static void loadFromFile(String dir, String[] files,
      Vector<Vector<MyImage>> images, Vector<Integer> frameCounts) {
    int count = files.length;
    images.clear();
    images.ensureCapacity(count);
    frameCounts.clear();
    frameCounts.ensureCapacity(count);
    for (int i = 0; i < count; ++i) {
      Vector<MyImage> currentImages = loadFromFile(dir, files[i]);
      images.add(currentImages);
      frameCounts.add(currentImages.size());
    }
  }

  public static void drawText(MyGraphics graphics, MyPoint centerPosition,
      String text, double rotation) {
    graphics.translate(centerPosition.x, centerPosition.y);
    graphics.rotate(rotation);
    int stringWidth = graphics.stringWidth(text);
    int stringHeight = graphics.stringHeight();
    graphics.drawString(text, -stringWidth / 2, stringHeight / 2);
    graphics.rotate(-rotation);
    graphics.translate(-centerPosition.x, -centerPosition.y);
  }

  public static void drawText(MyGraphics graphics, MyPoint centerPosition,
      String text) {
    int stringWidth = graphics.stringWidth(text);
    int stringHeight = graphics.stringHeight();
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
    graphics.rotate(rotation);

    // Calculate the left up position of the image
    MyPoint leftUp = new MyPoint(0, 0);
    if (center)
      leftUp = new MyPoint(-width / 2, -height / 2);

    graphics.translate((int) leftUp.x, (int) leftUp.y);
    graphics.scale(xRate, yRate);

    // Draw the image
    graphics.drawImage(image, 0, 0);

    graphics.scale(1.0 / xRate, 1.0 / yRate);
    graphics.translate(-leftUp.x, -leftUp.y);

    graphics.rotate(-rotation);
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
    graphics.scale(xRate, yRate);

    // Draw the pixmap
    graphics.drawImage(image, 0, 0);

    graphics.scale(1.0 / xRate, 1.0 / yRate);
    graphics.translate(-leftUp.x, -leftUp.y);
  }

}
