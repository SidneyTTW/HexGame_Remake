package VAST.HexGame.GameWidget;

import Aid.MyColor;
import Aid.MyGraphics;
import Aid.MyPoint;
import Aid.PuzzleInfo;
import VAST.HexGame.GameItem.StandardGameButtonItem;
import VAST.HexGame.Widgets.AbstractSimpleWidget;
import VAST.HexGame.Widgets.ItemInterface;
import VAST.HexGame.Widgets.RectButtonItem;

/**
 * Class of widget to choose continue or not when a puzzle is solved.
 * 
 * @author SidneyTTW
 * 
 */
public class PuzzleNextStageWidget extends AbstractSimpleWidget {
  int type;
  int stage;
  boolean advanced;
  
  public static final int Cancel = 0;
  public static final int Continue = 1;

  public PuzzleNextStageWidget(int type, int stage, boolean advanced) {
    this.type = type;
    this.stage = stage;
    this.advanced = advanced;
    
    ItemInterface item = new StandardGameButtonItem();
    item.setLogicalPosition(new MyPoint((int) (width() * 0.65),
        (int) (height() * 0.55)));
    item.setVisible(true);
    item.setEnabled(true);
    ((RectButtonItem) item).setText("Cancel");
    addItem(item, AbstractSimpleWidget.ItemType.ButtonItem);
    
    item = new StandardGameButtonItem();
    item.setLogicalPosition(new MyPoint((int) (width() * 0.35),
        (int) (height() * 0.55)));
    item.setVisible(true);
    if (stage < PuzzleInfo.totalStages(type) - 1)
    {
      ((RectButtonItem) item).setText("Continue");
      item.setEnabled(true);
    }
    else
    {
      ((RectButtonItem) item).setText("Stage cleared");
      item.setEnabled(false);
    }
    addItem(item, AbstractSimpleWidget.ItemType.ButtonItem);
  }

  @Override
  public void buttonClicked(int indexOfTheButton) {
    switch (indexOfTheButton) {
    case Cancel:
      mainWidget.changeControl(null, true);
      break;
    case Continue:
      PuzzleGameWidget widget = new PuzzleGameWidget(type,
          stage + 1, advanced);
      mainWidget.changeControl(widget, true);
      break;
    }
  }

  @Override
  public void dragTo(int indexOfTheDraggableItem, MyPoint position) {
    // Not supposed to be called
  }

  @Override
  public void dragApplied(int indexOfTheDraggableItem, MyPoint position) {
    // Not supposed to be called
  }
  
  @Override
  public void paint(MyGraphics g) {
    g.setColor(new MyColor(0, 0, 0));
    g.fillRect(0, 0, width(), height());
    super.paint(g);
  }
}
