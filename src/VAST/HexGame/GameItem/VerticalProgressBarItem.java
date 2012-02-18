/**
 * 
 */
package VAST.HexGame.GameItem;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;

import VAST.HexGame.Aid.SourceManagement;

import AidPackage.ImageAid;

/**
 * Class of a vertical progress bar. It will use the normalImages to paint the
 * bar and the pressedImages to paint the background.
 * 
 * @author SidneyTTW
 * 
 */
public class VerticalProgressBarItem extends AbstractProgressBarItem {
  /**
   * The pixels from the top of item to the top of bar.
   */
  int foreFrom = 0;

  /**
   * The pixels from the top of item to the bottom of bar.
   */
  int foreTo = 0;

  /**
   * The offset in x direction of words.
   */
  int wordXOffset = 0;

  /**
   * The offset in y direction of words.
   */
  int wordYOffset = 0;

  public VerticalProgressBarItem() {
    setImageSeries(SourceManagement.ProgressbarFolder,
        SourceManagement.VerticalProgressbarForeFile);
    setPressedImageSeries(SourceManagement.ProgressbarFolder,
        SourceManagement.VerticalProgressbarBackFile);
    setWidth(SourceManagement.VerticalProgressbarWidth);
    setHeight(SourceManagement.VerticalProgressbarHeight);
    foreFrom = SourceManagement.VerticalProgressbarForeFrom;
    foreTo = SourceManagement.VerticalProgressbarForeTo;
    wordXOffset = SourceManagement.VerticalProgressbarWordXOffset;
    wordYOffset = SourceManagement.VerticalProgressbarWordYOffset;
  }

  @Override
  public void paint(Graphics g, int frame) {
    if (pressedImages != null && !pressedImages.isEmpty())
      ImageAid.drawImageAt(g,
          pressedImages.elementAt(frame % pressedImages.size()), 1.0, 1.0,
          position, false, true, rotation);
    if (normalImages != null && !normalImages.isEmpty()) {
      Image image = normalImages.elementAt(frame % normalImages.size());
      int width = image.getWidth(null);
      int height = image.getHeight(null);
      Graphics2D g2d = (Graphics2D) g;
      g2d.translate(position.x - width / 2, position.y - height / 2);
      g2d.rotate(rotation);
      g2d.setColor(Color.blue);
      ImageAid.drawText(g2d, new Point(wordXOffset, wordYOffset), "" + current);
      double percentage = Math.max(0.0,
          Math.min(1.0, 1.0 * (current - min) / (max - min)));
      int clipHeight = (int) (percentage * (foreTo - foreFrom));
      Shape clip = g.getClip();
      g.setClip(0, foreTo - clipHeight, width, clipHeight);
      ImageAid
          .drawImageAt(g, image, 1.0, 1.0, new Point(0, 0), false, false, 0);
      g.setClip(clip);
      g2d.rotate(-rotation);
      g2d.translate(-position.x + width / 2, -position.y + height / 2);
    }
  }

}
