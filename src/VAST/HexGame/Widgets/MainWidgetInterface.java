/**
 * 
 */
package VAST.HexGame.Widgets;

import java.awt.Point;

/**
 * @author SidneyTTW
 * 
 */
public interface MainWidgetInterface {
  /**
   * Called by the widget to change the control. You can use this function to
   * simply push or pop one widget or displace a widget by another or refocus
   * the top widget.
   * 
   * @author SidneyTTW
   * 
   * @param target
   *          The target to push into the stack(if not NULL).
   * @param popMySelf
   *          Whether to pop from the stack.
   */
  public void changeControl(WidgetInterface target, boolean popMySelf);

  /**
   * Called by others who want to show a item on the top.
   * 
   * @author SidneyTTW
   * 
   * @param item
   *          The item to show.
   * @param startEndPosition
   *          The position to fade in and out.
   * @param stayPosition
   *          The position to stay for a while.
   * @param screenSize
   *          The logical screen size.
   * @param lastTime
   *          The last time.
   */
  public void addAnimItem(ItemInterface item, Point startEndPosition,
      Point stayPosition, Point screenSize, int lastTime);
}
