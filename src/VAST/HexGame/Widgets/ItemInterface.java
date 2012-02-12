/**
 * 
 */
package VAST.HexGame.Widgets;

import java.awt.Graphics;
import java.awt.Point;

/**
 * Interface of the items.
 * 
 * @author SidneyTTW
 * 
 */
public interface ItemInterface {
  /**
   * @return Whether the item is visible.
   */
  public boolean isVisible();

  /**
   * @param visible
   *          Whether the item is visible.
   */
  public void setVisible(boolean visible);

  /**
   * @return Whether the item is enabled.
   */
  public boolean isEnabled();

  /**
   * @param enabled
   *          Whether the item is enabled.
   */
  public void setEnabled(boolean enabled);

  /**
   * @return The logical position of the item.
   */
  public Point getLogicalPosition();

  /**
   * @param logicalPosition
   *          Set the logical position of the item.
   */
  public void setLogicalPosition(Point logicalPosition);

  /**
   * Function to paint the item.
   * 
   * @param g
   *          The graphics to paint on.
   * @param frame
   *          The frame to paint.
   */
  public void paint(Graphics g, int frame);

  /**
   * Whether the given point is in the item.
   * 
   * @param logicalPosition
   *          The logical position.
   * @return Whether the given logical position is in the item.
   */
  public boolean isIn(Point logicalPosition);

  /**
   * Press.
   */
  public void press();

  /**
   * Release.
   */
  public void release();

  /**
   * Set normal image series. And will set the pressed image series to the same
   * one if it's null.
   * 
   * @param dir
   *          The dir of the image files.
   * @param file
   *          The name of the image files.
   */
  public void setImageSeries(String dir, String file);

  /**
   * Set pressed image series.
   * 
   * @param dir
   *          The dir of the image files.
   * @param file
   *          The name of the image files.
   */
  public void setPressedImageSeries(String dir, String file);
}
