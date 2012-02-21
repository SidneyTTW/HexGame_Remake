/**
 * 
 */
package VAST.HexGame.GameItem;

import AidPackage.ImageAid;
import AidPackage.MyColor;
import AidPackage.MyGraphics;
import AidPackage.MyPoint;
import VAST.HexGame.Widgets.DraggableItemInterface;
import VAST.HexGame.Widgets.RoundItem;

/**
 * Abstract class of bonus items.
 * 
 * @author SidneyTTW
 * 
 */
public abstract class AbstractBonusItem extends RoundItem implements
    DraggableItemInterface {
  /**
   * Current number of the bonus.
   */
  int current = 0;

  /**
   * Max number of the bonus.
   */
  int max = 99;

  /**
   * Color of the number.
   */
  MyColor color = MyColor.black;

  /**
   * Offset of the number.
   */
  MyPoint wordsOffset = new MyPoint();

  /**
   * @return the current
   */
  public int getCurrent() {
    return current;
  }

  /**
   * @param current
   *          the current to set
   */
  public void setCurrent(int current) {
    if (current <= max)
      this.current = current;
    else
      this.current = max;
  }

  /**
   * @return the max
   */
  public int getMax() {
    return max;
  }

  /**
   * @param max
   *          the max to set
   */
  public void setMax(int max) {
    this.max = max;
  }

  /**
   * Add one bonus.
   */
  public void addOne() {
    setCurrent(current + 1);
  }

  /**
   * Minus one bonus.
   */
  public void minusOne() {
    setCurrent(current - 1);
  }

  /**
   * @return Whether the bonus item is empty.
   */
  public boolean isEmpty() {
    return current <= 0;
  }

  /**
   * 
   * @param color
   *          The color to set.
   */
  protected void setColor(MyColor color) {
    this.color = color;
  }

  /**
   * @param wordsOffset
   *          the wordsOffset to set
   */
  public void setWordsOffset(MyPoint wordsOffset) {
    this.wordsOffset = wordsOffset;
  }

  /**
   * Paint the number if the bonus.
   * 
   * @param g
   *          The graphics.
   * @param frame
   *          The frame
   */
  private void paintNumber(MyGraphics g, int frame) {
    MyPoint center = getLogicalPosition();
    MyPoint pos = new MyPoint(center.x + wordsOffset.x, center.y
        + wordsOffset.y);
    g.setColor(color);
    ImageAid.drawText(g, pos, "" + current, rotation);
  }

  @Override
  public void paint(MyGraphics g, int frame) {
    super.paint(g, frame);
    paintNumber(g, frame);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * VAST.HexGame.Widgets.DraggableItemInterface#paintShadow(java.awt.Point,
   * java.awt.Graphics, int)
   */
  @Override
  public void paintShadow(MyPoint position, MyGraphics g, int frame) {
    MyPoint origianlPos = getLogicalPosition();
    g.translate(position.x - origianlPos.x, position.y - origianlPos.y);
    super.paint(g, frame);
    g.translate(origianlPos.x - position.x, origianlPos.y - position.y);
  }

}
