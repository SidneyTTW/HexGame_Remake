/**
 * 
 */
package VAST.HexGame.GameItem;

/**
 * Interface of a progress bar.
 * 
 * @author SidneyTTW
 *
 */
public interface ProgressBarIterface {
  /**
   * @param min
   * The min value to set.
   */
  public void setMin(int min);
  
  /**
   * @return The min value.
   */
  public int getMin();
  
  /**
   * @param current
   * The current value to set.
   */
  public void setCurrent(int current);
  
  /**
   * @return The current value.
   */
  public int getCurrent();
  
  /**
   * @param max
   * The max value to set.
   */
  public void setMax(int max);
  
  /**
   * @return The max value.
   */
  public int getMax();

  /**
   * @return Whether the progress bar is full.
   */
  public boolean full();
}
