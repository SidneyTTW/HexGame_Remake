/**
 * 
 */
package VAST.HexGame.GameItem;

import VAST.HexGame.Widgets.RectItem;

/**
 * Abstract class of progress bar item.
 * 
 * @author SidneyTTW
 * 
 */
public class AbstractProgressBarItem extends RectItem implements
    ProgressBarIterface {
  /**
   * The min value.
   */
  int min = 0;

  /**
   * The current value.
   */
  int current = 0;

  /**
   * The max value.
   */
  int max = 100;

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.GameItem.ProgressBarIterface#setMin(int)
   */
  @Override
  public void setMin(int min) {
    this.min = min;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.GameItem.ProgressBarIterface#getMin()
   */
  @Override
  public int getMin() {
    return min;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.GameItem.ProgressBarIterface#setCurrent(int)
   */
  @Override
  public void setCurrent(int current) {
    this.current = current;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.GameItem.ProgressBarIterface#getCurrent()
   */
  @Override
  public int getCurrent() {
    return current;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.GameItem.ProgressBarIterface#setMax(int)
   */
  @Override
  public void setMax(int max) {
    this.max = max;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.GameItem.ProgressBarIterface#getMax()
   */
  @Override
  public int getMax() {
    return max;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.GameItem.ProgressBarIterface#full()
   */
  @Override
  public boolean full() {
    return current >= max;
  }
}
