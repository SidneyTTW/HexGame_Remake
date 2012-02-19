/**
 * 
 */
package VAST.HexGame.Widgets;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Vector;

/**
 * A Kind of widget manages the simple items and buttons.
 * 
 * @author SidneyTTW
 * 
 */
public abstract class AbstractSimpleWidget implements WidgetInterface {
  public static final int SIMPLE_WIDGET_WIDTH = 1024;
  public static final int SIMPLE_WIDGET_HEIGHT = 600;
  public static final int SIMPLE_WIDGET_INTERVAL = 60;

  public static enum ItemType {
    SimpleItem, ButtonItem
  }

  /**
   * The items to paint and deal with simple press and release event.
   */
  private Vector<ItemInterface> items = new Vector<ItemInterface>();

  /**
   * The draggable items to paint and deal with click event.
   */
  private Vector<DraggableItemInterface> draggableItems = new Vector<DraggableItemInterface>();

  /**
   * The buttons to paint and deal with click event.
   */
  private Vector<ItemInterface> buttons = new Vector<ItemInterface>();

  /**
   * The main widget.
   */
  protected MainWidgetInterface mainWidget = null;

  /**
   * Count of the frame.
   */
  protected int frame = 0;

  /**
   * The last item.
   */
  private ItemInterface lastItem = null;

  /**
   * The last mouse position.
   */
  Point mousePosition;

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.WidgetInterface#paint(java.awt.Graphics)
   */
  @Override
  public void paint(Graphics g) {
    for (int i = items.size() - 1; i >= 0; --i)
      if (items.elementAt(i).isVisible())
        items.elementAt(i).paint(g, frame);

    for (int i = draggableItems.size() - 1; i >= 0; --i)
      if (draggableItems.elementAt(i).isVisible())
        draggableItems.elementAt(i).paint(g, frame);

    if (lastItem != null && lastItem instanceof DraggableItemInterface)
      ((DraggableItemInterface) lastItem).paintShadow(mousePosition, g, frame);

    for (int i = buttons.size() - 1; i >= 0; --i)
      if (buttons.elementAt(i).isVisible())
        buttons.elementAt(i).paint(g, frame);
    ++frame;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.WidgetInterface#width()
   */
  @Override
  public int width() {
    return SIMPLE_WIDGET_WIDTH;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.WidgetInterface#height()
   */
  @Override
  public int height() {
    return SIMPLE_WIDGET_HEIGHT;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.WidgetInterface#toLogicalPoint(double, double)
   */
  @Override
  public Point toLogicalPoint(double xRate, double yRate) {
    return new Point((int) (xRate * width()), (int) (yRate * height()));
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.WidgetInterface#refreshInterval()
   */
  @Override
  public int refreshInterval() {
    return SIMPLE_WIDGET_INTERVAL;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.WidgetInterface#mousePressed(java.awt.Point, int,
   * int)
   */
  @Override
  public void mousePressed(Point logicalPos, int button, int mouseId) {
    mousePosition = logicalPos;
    lastItem = null;
    for (int i = 0; i < buttons.size(); ++i) {
      ItemInterface theButton = buttons.elementAt(i);
      if (theButton.isIn(logicalPos)) {
        if (!theButton.isEnabled())
          continue;
        lastItem = theButton;
        lastItem.press();
        return;
      }
    }
    for (int i = 0; i < draggableItems.size(); ++i) {
      ItemInterface theItem = draggableItems.elementAt(i);
      if (theItem.isIn(logicalPos)) {
        if (!theItem.isEnabled())
          continue;
        lastItem = theItem;
        lastItem.press();
        return;
      }
    }
    for (int i = 0; i < items.size(); ++i) {
      ItemInterface theItem = items.elementAt(i);
      if (theItem.isIn(logicalPos)) {
        if (!theItem.isEnabled())
          continue;
        lastItem = theItem;
        lastItem.press();
        return;
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.WidgetInterface#mouseReleased(java.awt.Point,
   * int, int)
   */
  @Override
  public void mouseReleased(Point logicalPos, int button, int mouseId) {
    mousePosition = logicalPos;
    if (lastItem != null && lastItem instanceof DraggableItemInterface) {
      for (int i = 0; i < draggableItems.size(); ++i) {
        ItemInterface theItem = draggableItems.elementAt(i);
        if (theItem == lastItem)
          dragApplied(i, logicalPos);
        lastItem = null;
        return;
      }
    }
    for (int i = 0; i < buttons.size(); ++i) {
      if (buttons.elementAt(i).isIn(logicalPos)) {
        ItemInterface theButton = buttons.elementAt(i);
        if (!theButton.isEnabled())
          continue;
        theButton.release();
        if (theButton == lastItem)
          buttonClicked(i);
        lastItem = null;
        return;
      }
    }
    for (int i = 0; i < items.size(); ++i) {
      ItemInterface theItem = items.elementAt(i);
      if (theItem.isIn(logicalPos)) {
        if (!theItem.isEnabled())
          continue;
        theItem.release();
        lastItem = null;
        return;
      }
    }
    lastItem = null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.WidgetInterface#mouseDragged(java.awt.Point, int,
   * int)
   */
  @Override
  public void mouseDragged(Point logicalPos, int button, int mouseId) {
    mousePosition = logicalPos;
    if (lastItem != null && lastItem instanceof DraggableItemInterface) {
      for (int i = 0; i < draggableItems.size(); ++i) {
        ItemInterface theItem = draggableItems.elementAt(i);
        if (theItem == lastItem)
          dragTo(i, logicalPos);
        return;
      }
      return;
    }
    for (int i = 0; i < buttons.size(); ++i) {
      ItemInterface theButton = buttons.elementAt(i);
      if (theButton.isIn(logicalPos)) {
        if (!theButton.isEnabled())
          continue;
        if (theButton != lastItem && lastItem != null) {
          lastItem.release();
          lastItem = null;
          return;
        } else if (theButton != lastItem) {
          lastItem = null;
          return;
        } else if (theButton == lastItem) {
          return;
        }
      }
    }
    for (int i = 0; i < items.size(); ++i) {
      ItemInterface theItem = items.elementAt(i);
      if (theItem.isIn(logicalPos)) {
        if (!theItem.isEnabled())
          continue;
        if (theItem != lastItem && lastItem != null) {
          lastItem.release();
          theItem.press();
          lastItem = theItem;
          return;
        } else if (theItem != lastItem) {
          theItem.press();
          lastItem = theItem;
          return;
        } else if (theItem == lastItem) {
          return;
        }
      }
    }
    if (lastItem != null) {
      lastItem.release();
      lastItem = null;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.WidgetInterface#mouseMoved(java.awt.Point, int,
   * int)
   */
  @Override
  public void mouseMoved(Point logicalPos, int button, int mouseId) {
  }

  /**
   * @see VAST.HexGame.Widgets.WidgetInterface#mouseClicked(java.awt.Point, int,
   *      int)
   */
  @Override
  public void mouseClicked(Point logicalPos, int button, int mouseId) {
    // TODO Currently, it's empty because I haven't found any thing to do here.
    // Maybe sonething will be added later.
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * VAST.HexGame.Widgets.WidgetInterface#setMainWidget(MainWidgetInterface)
   */
  @Override
  public void setMainWidget(MainWidgetInterface mainWidget) {
    this.mainWidget = mainWidget;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.WidgetInterface#getMainWidget()
   */
  @Override
  public MainWidgetInterface getMainWidget() {
    return mainWidget;
  }

  /**
   * Add an item.
   * 
   * @param item
   *          The item to add.
   * @param type
   *          The type of the item to add.
   */
  public void addItem(ItemInterface item, ItemType type) {
    switch (type) {
    case SimpleItem:
      if (item instanceof DraggableItemInterface)
        draggableItems.addElement((DraggableItemInterface) item);
      else
        items.addElement(item);
      break;
    case ButtonItem:
      buttons.addElement(item);
      break;
    }
  }

  /**
   * Called when one of the buttons is clicked.
   * 
   * @param indexOfTheButton
   *          The index of the button clicked.
   */
  public abstract void buttonClicked(int indexOfTheButton);

  /**
   * Called when one of the draggable items is dragged.
   * 
   * @param indexOfTheDraggableItem
   *          The index of draggable item dragged and released.
   * @param position
   *          The position of the release of the mouse.
   */
  public abstract void dragTo(int indexOfTheDraggableItem, Point position);

  /**
   * Called when one of the draggable items is dragged and released.
   * 
   * @param indexOfTheDraggableItem
   *          The index of draggable item dragged and released.
   * @param position
   *          The position of the release of the mouse.
   */
  public abstract void dragApplied(int indexOfTheDraggableItem, Point position);

}
