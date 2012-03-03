package VAST.HexGame.Widgets;

import java.util.LinkedList;
import java.util.Stack;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

import Aid.MathAid;
import Aid.MyGraphics;
import Aid.MyImage;
import Aid.MyPoint;

/**
 * @author SidneyTTW
 * 
 */
public class MainWidget extends View implements SurfaceHolder.Callback,
    MainWidgetInterface {
  public static final int ANIM_STEPS = 10;

  LinkedList<AnimItem> animItems = new LinkedList<AnimItem>();

  private int refreshInterval = 600;

  private RefreshHandler mRedrawHandler = new RefreshHandler();

  class RefreshHandler extends Handler {

    @Override
    public void handleMessage(Message msg) {
      MainWidget.this.invalidate();
    }

    public void sleep(long delayMillis) {
      this.removeMessages(0);
      sendMessageDelayed(obtainMessage(0), delayMillis);
    }
  };

  private class AnimItem {
    ItemInterface item;
    MyPoint startEndPosition;
    MyPoint stayPosition;
    MyPoint screenSize;
    int step;
    int lastTime;

    AnimItem(ItemInterface item, MyPoint startEndPosition,
        MyPoint stayPosition, MyPoint screenSize, int lastTime) {
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
  //MyImage lastImage = null;
  
  WidgetInterface lastWidget=null;

  public MainWidget(Context context, AttributeSet attrs) {
    super(context, attrs);
    setFocusable(true);
    setFocusableInTouchMode(true);
    mRedrawHandler.sleep(refreshInterval);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (animCount > 0)
      return true;
    int pointerCount = event.getPointerCount();
    if (event.getAction() == MotionEvent.ACTION_MOVE) {
      if (widgets.isEmpty())
        return true;
      WidgetInterface topWidget = widgets.peek();
      for (int i = 0; i < pointerCount; ++i) {
        MyPoint logicalPos = topWidget.toLogicalPoint(1.0 * event.getX(i)
            / getWidth(), 1.0 * event.getY(i) / getHeight());
        widgets.peek().mouseDragged(logicalPos, 0, event.getPointerId(i));
      }
    } else if (event.getAction() == MotionEvent.ACTION_UP) {
      if (widgets.isEmpty() || pointerCount != 1)
        return true;
      WidgetInterface topWidget = widgets.peek();
      for (int i = 0; i < pointerCount; ++i) {
        MyPoint logicalPos = topWidget.toLogicalPoint(1.0 * event.getX(0)
            / getWidth(), 1.0 * event.getY(0) / getHeight());
        widgets.peek().mouseReleased(logicalPos, 0, event.getPointerId(0));
      }
    } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
      if (widgets.isEmpty() || pointerCount != 1)
        return true;
      WidgetInterface topWidget = widgets.peek();
      for (int i = 0; i < pointerCount; ++i) {
        MyPoint logicalPos = topWidget.toLogicalPoint(1.0 * event.getX(0)
            / getWidth(), 1.0 * event.getY(0) / getHeight());
        widgets.peek().mousePressed(logicalPos, 0, event.getPointerId(0));
      }
    }
    return true;
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
//    if (!widgets.isEmpty()) {
//      WidgetInterface topWidget = widgets.elementAt(widgets.size() - 1);
//      if (lastImage == null || lastImage.getHeight() != topWidget.height()
//          || lastImage.getWidth() != topWidget.width()) {
//        if (lastImage != null)
//          lastImage.recycle();
//        lastImage = new MyImage(topWidget.width(), topWidget.height(),
//            MyImage.TYPE_4BYTE_ABGR);
//      } else {
//        MyGraphics g = lastImage.getGraphics();
//        g.setColor(new MyColor(0, 0, 0));
//        g.fillRect(0, 0, topWidget.width(), topWidget.height());
//      }
//      topWidget.paint(lastImage.getGraphics());
//    } else {
//      lastImage = null;
//    }

    if (!widgets.isEmpty())
      lastWidget = widgets.peek();
    else
      lastWidget = null;
    // Adjust the stack
    if (popMySelf) {
      //widgets.peek().recycle();
      widgets.pop();
    }
    if (target != null) {
      widgets.push(target);
      target.setMainWidget(this);
    }
    if (popMySelf && target == null && !widgets.isEmpty())
      widgets.peek().resume();

    // Adjust the count of animation
    if (!widgets.isEmpty()) {
      WidgetInterface topWidget = widgets.elementAt(widgets.size() - 1);
      animCount = (600 / topWidget.refreshInterval());

      refreshInterval = topWidget.refreshInterval();
    } else {
      refreshInterval = 600;
    }

    if (widgets.isEmpty()) {
      System.exit(0);
    }
  }

  @Override
  public void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    MyGraphics graphics = new MyGraphics(canvas);

    if (widgets.isEmpty()) {
      mRedrawHandler.sleep(refreshInterval);
      return;
    }

    WidgetInterface topWidget = widgets.peek();

    if (animCount > 0) {
      --animCount;
      // Tell the widget to get focus if necessary
      if (animCount == 0) {
//        if (lastWidget != null)
//          lastWidget.recycle();
        topWidget.getFocus();
      }
    }

    // Main part of the paint
    int width = this.getWidth();
    int height = this.getHeight();
    double xScale = 1.0 * width / topWidget.width();
    double yScale = 1.0 * height / topWidget.height();
    if (animCount > 0) {
      if (lastWidget != null) {
        double xScale2 = 1.0 * width / lastWidget.width();
        double yScale2 = 1.0 * height / lastWidget.height();
        graphics.scale((float) xScale2, (float) yScale2);
        lastWidget.paint(graphics);
        graphics.scale((float) (1 / xScale2), (float) (1 / yScale2));
      }
      int maxCD = (600 / topWidget.refreshInterval());
      graphics.translate(0, -animCount * height / maxCD);
      graphics.scale((float) xScale, (float) yScale);
      topWidget.paint(graphics);
      graphics.scale((float) (1 / xScale), (float) (1 / yScale));
      graphics.translate(0, animCount * height / maxCD);
    } else {
      graphics.scale((float) xScale, (float) yScale);
      topWidget.paint(graphics);
      graphics.scale((float) (1 / xScale), (float) (1 / yScale));
    }

    // Paint the anim item
    if (animItems.isEmpty()) {
      mRedrawHandler.sleep(refreshInterval);
      return;
    }
    AnimItem item = animItems.getFirst();
    if (item.advance()) {
      MyPoint size = item.getScreenSize();
      xScale = 1.0 * width / size.x;
      yScale = 1.0 * height / size.y;
      graphics.scale((float) xScale, (float) yScale);
      item.paint(graphics);
      graphics.scale((float) (1 / xScale), (float) (1 / yScale));
    } else {
      animItems.removeFirst();
    }
    mRedrawHandler.sleep(refreshInterval);
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

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width,
      int height) {
    // TODO Auto-generated method stub

  }

  @Override
  public void surfaceCreated(SurfaceHolder holder) {
    // TODO Auto-generated method stub

  }

  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {
    // TODO Auto-generated method stub

  }
}
