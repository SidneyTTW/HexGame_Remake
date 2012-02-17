/**
 * 
 */
package VAST.HexGame.Widgets;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import AidPackage.MathAid;

/**
 * @author SidneyTTW
 * 
 */
public class MainWidget extends JPanel implements MouseListener,
    MouseMotionListener, MainWidgetInterface {
  public static final int ANIM_STEPS = 10;

  LinkedList<AnimItem> animItems = new LinkedList<AnimItem>();

  private class AnimItem {
    ItemInterface item;
    Point startEndPosition;
    Point stayPosition;
    Point screenSize;
    int step;
    int lastTime;

    AnimItem(ItemInterface item, Point startEndPosition, Point stayPosition,
        Point screenSize, int lastTime) {
      this.item = item;
      this.startEndPosition = startEndPosition;
      this.stayPosition = stayPosition;
      this.screenSize = screenSize;
      this.lastTime = lastTime;
      step = 0;
    }

    boolean advance() {
      ++step;
      if (step < ANIM_STEPS)
        item.setLogicalPosition(MathAid.midPosition(startEndPosition,
            stayPosition, 1.0 * step / ANIM_STEPS));
      else if (step < ANIM_STEPS + lastTime)
        item.setLogicalPosition(stayPosition);
      else
        item.setLogicalPosition(MathAid
            .midPosition(stayPosition, startEndPosition, 1.0
                * (step - ANIM_STEPS - lastTime) / ANIM_STEPS));
      return step <= 2 * ANIM_STEPS + lastTime;
    }

    void paint(Graphics g) {
      item.paint(g, step);
    }

    /**
     * @return The logical size of the screen.
     */
    public Point getScreenSize() {
      return screenSize;
    }
  }

  /**
   * @see VAST.HexGame.Widgets.MainWidgetInterface#changeControl(VAST.HexGame.Widgets.WidgetInterface,
   *      boolean)
   * 
   * @brief The stack of widgets.
   */
  Stack<WidgetInterface> widgets = new Stack<WidgetInterface>();

  /**
   * @brief The count of the animation.
   */
  int animCount = 0;

  /**
   * @brief The image of the last top widget.
   */
  BufferedImage lastImage = null;

  /**
   * @brief The timer to do the repaint.
   */
  Timer timer = null;

  public MainWidget() {
    addMouseListener(this);
    addMouseMotionListener(this);
  }

  /**
   * @author SidneyTTW
   * 
   * @brief The timer task to do the repaint.
   */
  public class RepaintTimerTask extends TimerTask {
    public void run() {
      repaint();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * VAST.HexGame.Widgets.MainWidgetInterface#changeControl(VAST.HexGame.Widgets
   * .WidgetInterface, boolean)
   */
  @Override
  public void changeControl(WidgetInterface target, boolean popMySelf) {
    // Record the last image
    if (!widgets.isEmpty()) {
      WidgetInterface topWidget = widgets.elementAt(widgets.size() - 1);
      lastImage = new BufferedImage(topWidget.width(), topWidget.height(),
          BufferedImage.TYPE_4BYTE_ABGR);
      topWidget.paint(lastImage.getGraphics());
    } else {
      lastImage = null;
    }

    // Adjust the stack
    if (popMySelf)
      widgets.pop();
    if (target != null)
      widgets.push(target);

    // Adjust the count of animation
    if (!widgets.isEmpty()) {
      WidgetInterface topWidget = widgets.elementAt(widgets.size() - 1);
      animCount = (600 / topWidget.refreshInterval());

      // Adjust the timer
      if (timer != null)
        timer.cancel();
      RepaintTimerTask task = new RepaintTimerTask();
      timer = new Timer();
      timer.schedule(task, new Date(), topWidget.refreshInterval());
    } else {
      if (timer != null)
        timer.cancel();
      timer = null;
      animCount = 0;
    }
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    if (widgets.isEmpty())
      return;

    WidgetInterface topWidget = widgets.peek();

    if (animCount > 0) {
      --animCount;
      // Tell the widget to get focus if necessary
      if (animCount == 0)
        topWidget.getFocus();
    }

    // Main part of the paint
    int width = this.getWidth();
    int height = this.getHeight();
    double xScale = 1.0 * width / topWidget.width();
    double yScale = 1.0 * height / topWidget.height();
    Graphics2D g2d = (Graphics2D) g;
    if (animCount > 0) {
      if (lastImage != null)
        g2d.drawImage(lastImage, 0, 0, width, height, null);
      double maxCD = (600 / topWidget.refreshInterval());
      g2d.translate(0.0, -animCount * height / maxCD);
      g2d.scale(xScale, yScale);
      topWidget.paint(g2d);
      g2d.scale(1 / xScale, 1 / yScale);
      g2d.translate(0.0, animCount * height / maxCD);
    } else {
      g2d.scale(xScale, yScale);
      topWidget.paint(g2d);
      g2d.scale(1 / xScale, 1 / yScale);
    }

    if (animItems.isEmpty())
      return;
    AnimItem item = animItems.getFirst();
    if (item.advance()) {
      Point size = item.getScreenSize();
      xScale = 1.0 * width / size.x;
      yScale = 1.0 * height / size.y;
      g2d.scale(xScale, yScale);
      item.paint(g2d);
      g2d.scale(1 / xScale, 1 / yScale);
    } else {
      animItems.removeFirst();
    }
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    if (widgets.isEmpty())
      return;
    WidgetInterface topWidget = widgets.peek();
    Point logicalPos = topWidget.toLogicalPoint(1.0 * e.getX() / getWidth(),
        1.0 * e.getY() / getHeight());
    widgets.peek().mouseDragged(logicalPos, e.getButton(), 0);
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    if (widgets.isEmpty() || animCount > 0)
      return;
    WidgetInterface topWidget = widgets.peek();
    Point logicalPos = topWidget.toLogicalPoint(1.0 * e.getX() / getWidth(),
        1.0 * e.getY() / getHeight());
    widgets.peek().mouseMoved(logicalPos, e.getButton(), 0);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (widgets.isEmpty() || animCount > 0)
      return;
    WidgetInterface topWidget = widgets.peek();
    Point logicalPos = topWidget.toLogicalPoint(1.0 * e.getX() / getWidth(),
        1.0 * e.getY() / getHeight());
    widgets.peek().mouseClicked(logicalPos, e.getButton(), 0);
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

  @Override
  public void mousePressed(MouseEvent e) {
    if (widgets.isEmpty() || animCount > 0)
      return;
    WidgetInterface topWidget = widgets.peek();
    Point logicalPos = topWidget.toLogicalPoint(1.0 * e.getX() / getWidth(),
        1.0 * e.getY() / getHeight());
    widgets.peek().mousePressed(logicalPos, e.getButton(), 0);
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    if (widgets.isEmpty() || animCount > 0)
      return;
    WidgetInterface topWidget = widgets.peek();
    Point logicalPos = topWidget.toLogicalPoint(1.0 * e.getX() / getWidth(),
        1.0 * e.getY() / getHeight());
    widgets.peek().mouseReleased(logicalPos, e.getButton(), 0);
  }

  @Override
  public void addAnimItem(ItemInterface item, Point startEndPosition,
      Point stayPosition, Point screenSize, int lastTime) {
    animItems.addLast(new AnimItem(item, startEndPosition, stayPosition,
        screenSize, lastTime));
  }
}
