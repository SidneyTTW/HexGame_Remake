/**
 * 
 */
package VAST.HexGame.GameItem;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import AidPackage.ImageAid;
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
  Color color = Color.black;
  
  /**
   * Offset of the number.
   */
  Point wordsOffset = new Point();

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
  protected void setColor(Color color) {
    this.color = color;
  }

  /**
   * @param wordsOffset the wordsOffset to set
   */
  public void setWordsOffset(Point wordsOffset) {
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
  private void paintNumber(Graphics g, int frame) {
    Point center = getLogicalPosition();
    Point pos = new Point(center.x + wordsOffset.x, center.y + wordsOffset.y);
    g.setColor(color);
    ImageAid.drawText((Graphics2D) g, pos, "" + current);
  }
  
  @Override
  public void paint(Graphics g, int frame) {
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
  public void paintShadow(Point position, Graphics g, int frame) {
    Point origianlPos = getLogicalPosition();
    g.translate(position.x - origianlPos.x, position.y - origianlPos.y);
    super.paint(g, frame);
    g.translate(origianlPos.x - position.x, origianlPos.y - position.y);
  }

}
