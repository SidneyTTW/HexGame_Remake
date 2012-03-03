package VAST.HexGame.GameItem;

import Aid.SourceManagement;
import Aid.ImageAid;
import Aid.MyColor;
import Aid.MyGraphics;
import Aid.MyImage;
import Aid.MyPoint;

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
    setImageSeries(SourceManagement.VerticalProgressbarForeFile);
    setPressedImageSeries(SourceManagement.VerticalProgressbarBackFile);
    setWidth(SourceManagement.VerticalProgressbarWidth);
    setHeight(SourceManagement.VerticalProgressbarHeight);
    foreFrom = SourceManagement.VerticalProgressbarForeFrom;
    foreTo = SourceManagement.VerticalProgressbarForeTo;
    wordXOffset = SourceManagement.VerticalProgressbarWordXOffset;
    wordYOffset = SourceManagement.VerticalProgressbarWordYOffset;
  }

  @Override
  public void paint(MyGraphics g, int frame) {
    if (pressedImages != null && !pressedImages.isEmpty())
      ImageAid.drawImageAt(g,
          pressedImages.elementAt(frame % pressedImages.size()), 1.0, 1.0,
          position, false, true, rotation);
    if (normalImages != null && !normalImages.isEmpty()) {
      MyImage image = normalImages.elementAt(frame % normalImages.size());
      int width = image.getWidth();
      int height = image.getHeight();
      g.translate(position.x - width / 2, position.y - height / 2);
      g.rotate((float) rotation);
      g.setColor(MyColor.blue);
      ImageAid.drawText(g, new MyPoint(wordXOffset, wordYOffset), "" + current);
      double percentage = Math.max(0.0,
          Math.min(1.0, 1.0 * (current - min) / (max - min)));
      int clipHeight = (int) (percentage * (foreTo - foreFrom));
      g.save();
      g.setClip(0, foreTo - clipHeight, width, clipHeight);
      ImageAid.drawImageAt(g, image, 1.0, 1.0, new MyPoint(0, 0), false, false,
          0);
      g.restore();
      g.rotate((float) -rotation);
      g.translate(-position.x + width / 2, -position.y + height / 2);
    }
  }

}
