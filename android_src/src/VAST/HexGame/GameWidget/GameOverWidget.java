package VAST.HexGame.GameWidget;

import Aid.MyPoint;
import VAST.HexGame.GameItem.IntegerItem;
import VAST.HexGame.GameItem.StandardGameButtonItem;
import VAST.HexGame.GameWidget.AbstractStandardGameWidget.StandardGesture;
import VAST.HexGame.Widgets.AbstractSimpleWidget;
import VAST.HexGame.Widgets.ItemInterface;
import VAST.HexGame.Widgets.RectButtonItem;

/**
 * Class of widget to show when a game is over.
 * 
 * @author SidneyTTW
 * 
 */
public class GameOverWidget extends AbstractSimpleWidget {
  ItemInterface newRecordItem;
  IntegerItem integerItem;
  StandardGesture gesture;

  private static final int RestartButton = 0;
  private static final int CancelButton = 1;

  public GameOverWidget(String text, int number, boolean newRecord,
      StandardGesture gesture) {
    integerItem = new IntegerItem(316);
    integerItem.setLogicalPosition(new MyPoint((int) (width() * 0.5),
        (int) (height() * 0.6)));
    integerItem.setNumber(number);
    integerItem.setDescription(text);
    addItem(integerItem, AbstractSimpleWidget.ItemType.SimpleItem);

    ItemInterface item = new StandardGameButtonItem();
    item.setVisible(false);
    item.setEnabled(false);
    ((RectButtonItem) item).setText("Restart");
    addItem(item, AbstractSimpleWidget.ItemType.ButtonItem);

    item = new StandardGameButtonItem();
    item.setVisible(false);
    item.setEnabled(false);
    ((RectButtonItem) item).setText("Cancel");
    addItem(item, AbstractSimpleWidget.ItemType.ButtonItem);
  }

  @Override
  public void getFocus() {
  }

  @Override
  public void buttonClicked(int indexOfTheButton) {
    // TODO Auto-generated method stub

  }

  @Override
  public void dragTo(int indexOfTheDraggableItem, MyPoint position) {
  }

  @Override
  public void dragApplied(int indexOfTheDraggableItem, MyPoint position) {
  }

}
