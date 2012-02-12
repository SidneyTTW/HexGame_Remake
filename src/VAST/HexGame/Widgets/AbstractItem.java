/**
 * 
 */
package VAST.HexGame.Widgets;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.Vector;

import AidPackage.ImageAid;

/**
 * Abstract class of items, realized all the functions except isIn, dx and dy.
 * 
 * @author SidneyTTW
 *
 */
public abstract class AbstractItem implements ItemInterface {
  public static enum ItemState{Normal, Pressed}
  
  /**
   * The images of the normal state.
   */
  Vector<Image> normalImages = null;
  
  /**
   * The images of the pressed state.
   */
  Vector<Image> pressedImages = null;
  
  /**
   * The state of the item.
   */
  ItemState state = ItemState.Normal;
  
  /**
   * The center position of the item.
   */
  private Point position = new Point(0, 0);

  /**
   * Whether the item is visible.
   */
  private boolean visible = true;

  /**
   * Whether the item is enabled.
   */
  private boolean enabled = true;

  /**
   * @return Whether the item is pressed.
   */
  public boolean isPressed() {
    return state == ItemState.Pressed;
  }

  /* (non-Javadoc)
   * @see VAST.HexGame.Widgets.ItemInterface#isVisible()
   */
  @Override
  public boolean isVisible() {
    return visible;
  }

  /* (non-Javadoc)
   * @see VAST.HexGame.Widgets.ItemInterface#setVisible(boolean)
   */
  @Override
  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  /* (non-Javadoc)
   * @see VAST.HexGame.Widgets.ItemInterface#isEnabled()
   */
  @Override
  public boolean isEnabled() {
    return enabled;
  }

  /* (non-Javadoc)
   * @see VAST.HexGame.Widgets.ItemInterface#setEnabled(boolean)
   */
  @Override
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;

  }

  /* (non-Javadoc)
   * @see VAST.HexGame.Widgets.ItemInterface#getLogicalPosition()
   */
  @Override
  public Point getLogicalPosition() {
    return position;
  }

  /* (non-Javadoc)
   * @see VAST.HexGame.Widgets.ItemInterface#setLogicalPosition(java.awt.Point)
   */
  @Override
  public void setLogicalPosition(Point logicalPosition) {
    this.position = logicalPosition;

  }

  /* (non-Javadoc)
   * @see VAST.HexGame.Widgets.ItemInterface#paint(java.awt.Graphics, int)
   */
  @Override
  public void paint(Graphics g, int frame) {
    if (!isVisible())
      return;
    Image image = null;
    switch (state) {
    case Normal:
      if (normalImages == null || normalImages.isEmpty())
        break;
      image = normalImages.elementAt(frame % normalImages.size());
      break;
    case Pressed:
      if (pressedImages == null || pressedImages.isEmpty())
        break;
      image = pressedImages.elementAt(frame % pressedImages.size());
      break;
    }
    if (image == null)
      return;
    g.drawImage(image,
                (int)(getLogicalPosition().getX() - dx()),
                (int)(getLogicalPosition().getY() - dy()),
                null);
  }

  /* (non-Javadoc)
   * @see VAST.HexGame.Widgets.ItemInterface#press()
   */
  @Override
  public void press() {
    state = ItemState.Pressed;
  }

  /* (non-Javadoc)
   * @see VAST.HexGame.Widgets.ItemInterface#release()
   */
  @Override
  public void release() {
    state = ItemState.Normal;
  }

  /* (non-Javadoc)
   * @see VAST.HexGame.Widgets.ItemInterface#setImageSeries(String, String)
   */
  @Override
  public void setImageSeries(String dir, String file) {
    normalImages = ImageAid.loadFromFile(dir, file);
    if (pressedImages == null)
      pressedImages = normalImages;
  }

  /* (non-Javadoc)
   * @see VAST.HexGame.Widgets.ItemInterface#setPressedImageSeries(String, String)
   */
  @Override
  public void setPressedImageSeries(String dir, String file) {
    pressedImages = ImageAid.loadFromFile(dir, file);
  }

  /**
   * @return The distance from the center to the left edge.
   */
  public abstract int dx();

  /**
   * @return The distance from the center to the top edge.
   */
  public abstract int dy();
}
