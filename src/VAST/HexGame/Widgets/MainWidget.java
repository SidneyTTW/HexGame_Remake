/**
 * 
 */
package VAST.HexGame.Widgets;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Date;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import AidPackage.MathAid;
import AidPackage.MyGraphics;
import AidPackage.MyImage;
import AidPackage.MyPoint;

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
    MyPoint startEndPosition;
    MyPoint stayPosition;
    MyPoint screenSize;
    int step;
    int lastTime;

    AnimItem(ItemInterface item, MyPoint startEndPosition, MyPoint stayPosition,
        MyPoint screenSize, int lastTime) {
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

    void paint(MyGraphics g) {
      item.paint(g, step);
    }

    /**
     * @return The logical size of the screen.
     */
    public MyPoint getScreenSize() {
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
  MyImage lastImage = null;

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
      lastImage = new MyImage(topWidget.width(), topWidget.height(),
          MyImage.TYPE_4BYTE_ABGR);
      topWidget.paint(lastImage.getGraphics());
    } else {
      lastImage = null;
    }

    // Adjust the stack
    if (popMySelf)
      widgets.pop();
    if (target != null) {
      widgets.push(target);
      target.setMainWidget(this);
    }

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
    
    if (widgets.isEmpty()) {
      System.exit(0);
    }
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    MyGraphics graphics = new MyGraphics((Graphics2D) g);
    
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
    if (animCount > 0) {
      if (lastImage != null)
        graphics.drawImage(lastImage, 0, 0, width, height);
      int maxCD = (600 / topWidget.refreshInterval());
      graphics.translate(0, -animCount * height / maxCD);
      graphics.scale(xScale, yScale);
      topWidget.paint(graphics);
      graphics.scale(1 / xScale, 1 / yScale);
      graphics.translate(0, animCount * height / maxCD);
    } else {
      graphics.scale(xScale, yScale);
      topWidget.paint(graphics);
      graphics.scale(1 / xScale, 1 / yScale);
    }

    // Paint the anim item
    if (animItems.isEmpty())
      return;
    AnimItem item = animItems.getFirst();
    if (item.advance()) {
      MyPoint size = item.getScreenSize();
      xScale = 1.0 * width / size.x;
      yScale = 1.0 * height / size.y;
      graphics.scale(xScale, yScale);
      item.paint(graphics);
      graphics.scale(1 / xScale, 1 / yScale);
    } else {
      animItems.removeFirst();
    }
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    if (widgets.isEmpty())
      return;
    WidgetInterface topWidget = widgets.peek();
    MyPoint logicalPos = topWidget.toLogicalPoint(1.0 * e.getX() / getWidth(),
        1.0 * e.getY() / getHeight());
    widgets.peek().mouseDragged(logicalPos, e.getButton(), 0);
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    if (widgets.isEmpty() || animCount > 0)
      return;
    WidgetInterface topWidget = widgets.peek();
    MyPoint logicalPos = topWidget.toLogicalPoint(1.0 * e.getX() / getWidth(),
        1.0 * e.getY() / getHeight());
    widgets.peek().mouseMoved(logicalPos, e.getButton(), 0);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (widgets.isEmpty() || animCount > 0)
      return;
    WidgetInterface topWidget = widgets.peek();
    MyPoint logicalPos = topWidget.toLogicalPoint(1.0 * e.getX() / getWidth(),
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
    MyPoint logicalPos = topWidget.toLogicalPoint(1.0 * e.getX() / getWidth(),
        1.0 * e.getY() / getHeight());
    widgets.peek().mousePressed(logicalPos, e.getButton(), 0);
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    if (widgets.isEmpty() || animCount > 0)
      return;
    WidgetInterface topWidget = widgets.peek();
    MyPoint logicalPos = topWidget.toLogicalPoint(1.0 * e.getX() / getWidth(),
        1.0 * e.getY() / getHeight());
    widgets.peek().mouseReleased(logicalPos, e.getButton(), 0);
  }

  @Override
  public void addAnimItem(ItemInterface item, MyPoint startEndPosition,
      MyPoint stayPosition, MyPoint screenSize, int lastTime) {
    animItems.addLast(new AnimItem(item, startEndPosition, stayPosition,
        screenSize, lastTime));
  }

  @Override
  public MyPoint topWidgetSize() {
    return new MyPoint(widgets.peek().width(), widgets.peek().height());
  }
}
