package VAST.HexGame.GameWidget;

import Aid.MyColor;
import Aid.MyGraphics;
import Aid.MyPoint;
import VAST.HexGame.GameItem.IntegerItem;
import VAST.HexGame.GameItem.StandardGameButtonItem;
import VAST.HexGame.GameWidget.AbstractStandardGameWidget.StandardGesture;
import VAST.HexGame.Widgets.AbstractSimpleWidget;
import VAST.HexGame.Widgets.ItemInterface;
import VAST.HexGame.Widgets.RectButtonItem;
import VAST.HexGame.Widgets.WidgetInterface;

/**
 * Class of widget to show when a game is over.
 * 
 * @author SidneyTTW
 * 
 */
public class GameOverWidget extends AbstractSimpleWidget {
  
  // ItemInterface newRecordItem;

  int type;
  StandardGesture gesture;

  public static final int Classic = 0;
  public static final int Endless = 1;
  public static final int Timing = 2;

  private static final String title[] = { "Classic Game", "Endless Game",
      "Timing Game" };

  private static final int RestartButton = 0;
  private static final int CancelButton = 1;

  public GameOverWidget(int type, StandardGesture gesture, int number,
      boolean newRecord) {
    this.type = type;
    this.gesture = gesture;

    IntegerItem integerItem = new IntegerItem(316);
    integerItem.setLogicalPosition(new MyPoint((int) (width() * 0.5),
        (int) (height() * 0.4)));
    integerItem.setNumber(number);
    integerItem.setDescription(title[type]);
    addItem(integerItem, AbstractSimpleWidget.ItemType.SimpleItem);

    ItemInterface item = new StandardGameButtonItem();
    item.setLogicalPosition(new MyPoint((int) (width() * 0.35),
        (int) (height() * 0.6)));
    item.setVisible(true);
    item.setEnabled(true);
    ((RectButtonItem) item).setText("Restart");
    addItem(item, AbstractSimpleWidget.ItemType.ButtonItem);

    item = new StandardGameButtonItem();
    item.setLogicalPosition(new MyPoint((int) (width() * 0.65),
        (int) (height() * 0.6)));
    item.setVisible(true);
    item.setEnabled(true);
    ((RectButtonItem) item).setText("Cancel");
    addItem(item, AbstractSimpleWidget.ItemType.ButtonItem);
  }

  @Override
  public void buttonClicked(int indexOfTheButton) {
    switch (indexOfTheButton) {
    case RestartButton:
      WidgetInterface widget = null;
      switch (type)
      {
      case Classic:
        widget = new ClassicGameWidget(gesture);
        break;
      case Endless:
        widget = new EndlessGameWidget(gesture);
        break;
      case Timing:
        widget = new TimingGameWidget(gesture);
        break;
      }
      mainWidget.changeControl(widget, true);
      break;
    case CancelButton:
      mainWidget.changeControl(null, true);
      break;
    }
  }

  @Override
  public void dragTo(int indexOfTheDraggableItem, MyPoint position) {
  }

  @Override
  public void dragApplied(int indexOfTheDraggableItem, MyPoint position) {
  }
  
  @Override
  public void paint(MyGraphics g) {
    g.setColor(new MyColor(0, 0, 0));
    g.fillRect(0, 0, width(), height());
    super.paint(g);
  }
}
