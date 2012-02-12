/**
 * 
 */
package VAST.HexGame.Widgets;

import java.awt.Graphics;
import java.awt.Point;


/**
 * @author SidneyTTW
 *
 * The interface of the widgets.
 */
public interface WidgetInterface {
  /**
   * Called by the main widget when the widget gets focus.
   */
  public void getFocus();
  
  /**
   * Called by the main widget when paint is needed.
   *
   * @param g The graphics of the paintable device.
   */
  public void paint(Graphics g);
  
  /**
   * Called by the main widget when paint is needed, in order to adjust the graphics.
   *
   * @return The logical width of the widget.
   */
  public int width();
  
  /**
   * Called by the main widget when paint is needed, in order to adjust the graphics.
   *
   * @return The logical height of the widget.
   */
  public int height();
  
  /**
   * Called by the main widget to know the logical position in the widget.
   *
   * @param xRate The rate in x direction.
   * @param yRate The rate in y direction.
   * @return The logical point in the widget.
   */
  public Point toLogicalPoint(double xRate, double yRate);
  
  /**
   * Called by the main widget when setting the refresh timer.
   *
   * @return The suggested refresh interval in milliseconds.
   */
  public int refreshInterval();
  
  /**
   * Deal with the press event.
   * 
   * @param logicalPos The logical position of the event.
   * @param button The button of the mouse.
   * @param mouseId The id of the mouse.
   *  In a simple application, it should only be 0, but on a tablet, it may be some bigger number.
   */
  public void mousePressed(Point logicalPos, int button, int mouseId);
  
  /**
   * Deal with the release event.
   * 
   * @param logicalPos The logical position of the event.
   * @param button The button of the mouse.
   * @param mouseId The id of the mouse.
   *  In a simple application, it should only be 0, but on a tablet, it may be some bigger number.
   */
  public void mouseReleased(Point logicalPos, int button, int mouseId);
  
  /**
   * Deal with the drag event.
   * 
   * @param logicalPos The logical position of the event.
   * @param button The button of the mouse.
   * @param mouseId The id of the mouse.
   *  In a simple application, it should only be 0, but on a tablet, it may be some bigger number.
   */
  public void mouseDragged(Point logicalPos, int button, int mouseId);
  
  /**
   * Deal with the move event.
   * 
   * @param logicalPos The logical position of the event.
   * @param button The button of the mouse.
   * @param mouseId The id of the mouse.
   *  In a simple application, it should only be 0, but on a tablet, it may be some bigger number.
   */
  public void mouseMoved(Point logicalPos, int button, int mouseId);
  
  /**
   * Deal with the click event.
   * 
   * @param logicalPos The logical position of the event.
   * @param button The button of the mouse.
   * @param mouseId The id of the mouse.
   *  In a simple application, it should only be 0, but on a tablet, it may be some bigger number.
   */
  public void mouseClicked(Point logicalPos, int button, int mouseId);

  /**
   * Set the main widget.
   * 
   * @param mainWidget The main widget to set.
   */
  public void setMainWidget(MainWidgetInterface mainWidget);

  /**
   * Get the main widget.
   * 
   * @return The main widget.
   */
  public MainWidgetInterface getMainWidget();
}
