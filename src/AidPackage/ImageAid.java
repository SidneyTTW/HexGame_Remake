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
  public static Vector<Image> loadFromFile(String dir, String file) {
    Vector<Image> result = new Vector<Image>();
    List<File> fileList = new ArrayList<File>();
    FileSearcher.findFiles(dir, file, fileList);
    for (int i = 0; i < fileList.size(); i++) {
      result.add(Toolkit.getDefaultToolkit().getImage(
          fileList.get(i).getAbsolutePath()));
    }
    return result;
  }

  /**
   * Initialize images of some thing, each thing has only one image.
   */
  public static Vector<Image> loadFromFile(String dir, String[] files) {
    Vector<Image> result = new Vector<Image>();
    result.ensureCapacity(files.length);
    for (int i = 0; i < files.length; ++i) {
      List<File> fileList = new ArrayList<File>();
      FileSearcher.findFiles(dir, files[i], fileList);
      if (fileList.isEmpty())
        result.add(null);
      else
        result.add(Toolkit.getDefaultToolkit().getImage(
            fileList.get(0).getAbsolutePath()));
    }
    return result;
  }

  /**
   * Initialize images of some things, each thing has several images.
   */
  public static void loadFromFile(String dir, String[] files,
      Vector<Vector<Image>> images, Vector<Integer> frameCounts) {
    int count = files.length;
    images.clear();
    images.ensureCapacity(count);
    frameCounts.clear();
    frameCounts.ensureCapacity(count);
    for (int i = 0; i < count; ++i) {
      Vector<Image> currentImages = loadFromFile(dir, files[i]);
      images.add(currentImages);
      frameCounts.add(currentImages.size());
    }
  }

  public static void drawText(Graphics2D g2d, Point centerPosition, String text) {
    int stringWidth = g2d.getFontMetrics().stringWidth(text);
    int stringAscent = g2d.getFontMetrics().getAscent();
    int stringHeight = g2d.getFontMetrics().getAscent()
        + g2d.getFontMetrics().getDescent();
    g2d.drawString(text, (int) (centerPosition.getX() - stringWidth / 2),
        (int) (centerPosition.getY() - stringHeight / 2));
  }

  /**
   * Draw a image with some options.
   */
  public static void drawImageAt(Graphics graphics, Image image, double xRate,
      double yRate, Point pos, boolean resize, boolean center) {
    // Calculate the width and height the pixmap should be
    double width = image.getWidth(null);
    double height = image.getHeight(null);

    if (width < 1 || height < 1)
      return;

    if (xRate >= 0.999 && xRate <= 1.001 && yRate >= 0.999 && yRate <= 1.001)
      resize = false;

    if (resize) {
      width *= xRate;
      height *= yRate;
    }

    // Calculate the left up position of the pixmap
    Point leftUp = pos;
    if (center) {
      leftUp = new Point((int) (leftUp.getX() - width / 2),
          (int) (leftUp.getY() - height / 2));
    }

    Graphics2D g2d = (Graphics2D) graphics;
    g2d.translate((int) leftUp.getX(), (int) leftUp.getY());
    g2d.scale(xRate, yRate);

    // Draw the pixmap
    g2d.drawImage(image, 0, 0, null);

    g2d.scale(1.0 / xRate, 1.0 / yRate);
    g2d.translate((int) -leftUp.getX(), (int) -leftUp.getY());
  }

}
