/**
 * 
 */
package VAST.HexGame.GameItem;

import Aid.MyGraphics;

/**
 * Interface of a tab.
 * 
 * @author SidneyTTW
 * 
 */
public interface TabInterface {
  /**
   * Paint the tab.
   * 
   * @param graphics
   *          The graphics to paint.
   * @param frame
   *          The frame to paint.
   */
  public void paintTab(MyGraphics graphics, int frame);

  /**
   * Called when the tab get focus.
   * 
   * @param currentFrame
   *          The current frame.
   */
  public void getFocus(int currentFrame);

  /**
   * Used to advance the tab label.
   */
  public void advance();

  /**
   * Called when the tab lose focus.
   */
  public void loseFocus();
}
