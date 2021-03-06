/**
 * 
 */
package VAST.HexGame.Widgets;

import java.util.Vector;

import Aid.ImageAid;
import Aid.MyGraphics;
import Aid.MyImage;
import Aid.MyPoint;

/**
 * Abstract class of items, realized all the functions except isIn, dx and dy.
 * 
 * @author SidneyTTW
 * 
 */
public abstract class AbstractItem implements ItemInterface {
  public static enum ItemState {
    Normal, Pressed
  }

  protected int normalImageId = 0;

  protected int pressedImageId = 0;

  /**
   * The images of the normal state.
   */
  protected Vector<MyImage> normalImages = null;

  /**
   * The images of the pressed state.
   */
  protected Vector<MyImage> pressedImages = null;

  /**
   * The state of the item.
   */
  protected ItemState state = ItemState.Normal;

  /**
   * The center position of the item.
   */
  protected MyPoint position = new MyPoint(0, 0);

  /**
   * Rotation of the item.
   */
  protected float rotation = 0;

  /**
   * @param rotation
   *          The rotation to set.
   */
  public void setRotation(float rotation) {
    this.rotation = rotation;
  }

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

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.ItemInterface#isVisible()
   */
  @Override
  public boolean isVisible() {
    return visible;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.ItemInterface#setVisible(boolean)
   */
  @Override
  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.ItemInterface#isEnabled()
   */
  @Override
  public boolean isEnabled() {
    return enabled;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.ItemInterface#setEnabled(boolean)
   */
  @Override
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;

  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.ItemInterface#getLogicalPosition()
   */
  @Override
  public MyPoint getLogicalPosition() {
    return position;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.ItemInterface#setLogicalPosition(java.awt.Point)
   */
  @Override
  public void setLogicalPosition(MyPoint logicalPosition) {
    this.position = logicalPosition;

  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.ItemInterface#paint(java.awt.Graphics, int)
   */
  @Override
  public void paint(MyGraphics g, int frame) {
    MyImage image = null;
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
    ImageAid.drawImageAt(g, image, 1.0, 1.0, position, false, true, rotation);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.ItemInterface#press()
   */
  @Override
  public void press() {
    state = ItemState.Pressed;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.ItemInterface#release()
   */
  @Override
  public void release() {
    state = ItemState.Normal;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.ItemInterface#setImageSeries(String, String)
   */
  @Override
  public void setImageSeries(int id) {
    normalImages = ImageAid.loadFromFile(id);
    normalImageId = id;
    if (pressedImages == null)
      pressedImages = normalImages;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.ItemInterface#setPressedImageSeries(String,
   * String)
   */
  @Override
  public void setPressedImageSeries(int id) {
    pressedImages = ImageAid.loadFromFile(id);
    pressedImageId = id;
  }

  @Override
  public void resume() {
    if (normalImageId == 0)
      normalImages = null;
    else
      normalImages = ImageAid.loadFromFile(normalImageId);
    if (pressedImageId == 0)
      pressedImages = normalImages;
    else
      pressedImages = ImageAid.loadFromFile(pressedImageId);
  }

  @Override
  public void recycle() {
    if (normalImages != null)
      for (int i = 0; i < normalImages.size(); ++i)
        normalImages.elementAt(i).recycle();
    if (pressedImages != null)
      for (int i = 0; i < pressedImages.size(); ++i)
        pressedImages.elementAt(i).recycle();
  }
}
