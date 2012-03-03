/**
 * 
 */
package VAST.HexGame.Game;

import Aid.MyPoint;

/**
 * Interface to control the gesture.
 * 
 * @author SidneyTTW
 * 
 */
public interface GestureControllerInterface {
  /**
   * State of the gesture.
   */
  enum GestureState {
    NoGesture, ChooseGesture, LocateGesture
  };

  /**
   * Deal with the press event.
   * 
   * @param logicalPos
   *          The logical position of the event.
   * @param button
   *          The button of the mouse.
   * @param mouseId
   *          The id of the mouse. In a simple application, it should only be 0,
   *          but on a tablet, it may be some bigger number.
   */
  public void pressAt(MyPoint logicalPos, int button, int mouseId);

  /**
   * Deal with the release event.
   * 
   * @param logicalPos
   *          The logical position of the event.
   * @param button
   *          The button of the mouse.
   * @param mouseId
   *          The id of the mouse. In a simple application, it should only be 0,
   *          but on a tablet, it may be some bigger number.
   */
  public void releaseAt(MyPoint logicalPos, int button, int mouseId);

  /**
   * Deal with the drag event.
   * 
   * @param logicalPos
   *          The logical position of the event.
   * @param button
   *          The button of the mouse.
   * @param mouseId
   *          The id of the mouse. In a simple application, it should only be 0,
   *          but on a tablet, it may be some bigger number.
   */
  public void dragAt(MyPoint logicalPos, int button, int mouseId);

  /**
   * Interrupt the gesture.
   */
  public void interrupt();

  /**
   * Advance the gesture, mainly used to clear the cd.
   */
  public void advance();
}
