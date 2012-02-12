/**
 * 
 */
package VAST.HexGame.Widgets;

/**
 * @author SidneyTTW
 *
 */
public interface MainWidgetInterface {
  /**
   * @author SidneyTTW
   *
   * Called by the widget to change the control.
   * You can use this function to simply push or pop one widget
   * or displace a widget by another
   * or refocus the top widget.
   * 
   * @param target The target to push into the stack(if not NULL).
   * @param popMySelf Whether to pop from the stack.
   */
	public void changeControl(WidgetInterface target, boolean popMySelf);
}
