package AidPackage;


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
  public static Vector<Image> loadFromFile(String dir, String file) {
    Vector<Image> result = new Vector<Image>();
    List<File> fileList = new ArrayList<File>();  
    FileSearcher.findFiles(dir, file, fileList);   
    for (int i = 0; i < fileList.size(); i++) {
      result.add(Toolkit.getDefaultToolkit().getImage(fileList.get(i).getAbsolutePath()));
    }
    return result;
  }
  
  public static void drawText(Graphics2D g2d, Point centerPosition, String text) {
    int stringWidth = g2d.getFontMetrics().stringWidth(text);
    int stringAscent = g2d.getFontMetrics().getAscent();
    int stringHeight = g2d.getFontMetrics().getAscent() + g2d.getFontMetrics().getDescent();
    g2d.drawString(text,
                   (int) (centerPosition.getX() - stringWidth / 2),
                   (int) (centerPosition.getY() - stringHeight / 2));
  }

}
