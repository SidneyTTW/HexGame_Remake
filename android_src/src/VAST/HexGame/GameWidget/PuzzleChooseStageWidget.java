package VAST.HexGame.GameWidget;

import Aid.MyColor;
import Aid.MyGraphics;
import Aid.MyPoint;
import Aid.PuzzleInfo;
import VAST.HexGame.GameItem.PuzzleStageItem;
import VAST.HexGame.Widgets.AbstractSimpleWidget;

/**
 * Class to choose the stage of the puzzle game.
 * 
 * @author SidneyTTW
 * 
 */
public class PuzzleChooseStageWidget extends AbstractSimpleWidget {

  protected static final int Exchange = 0;
  protected static final int Unite = 1;
  protected static final int Lock = 2;

  protected static final int Exit = 0;
  protected static final int Advance = 1;

  protected int type;

  protected boolean advanced;

  protected int stages;

  protected PuzzleStageItem items[];

  protected static double[][] xRates = { { 0.8, 0.5, 0.35, 0.5, 0.5, 0.65 },
      { 0.8, 0.5, 0.35, 0.35, 0.5, 0.65, 0.65 },
      { 0.8, 0.5, 0.2, 0.35, 0.5, 0.65, 0.8, 0.2, 0.35, 0.5, 0.65, 0.8 } };
  protected static double[][] yRates = { { 0.8, 0.8, 0.35, 0.2, 0.5, 0.35 },
      { 0.8, 0.8, 0.65, 0.35, 0.2, 0.35, 0.65 },
      { 0.5, 0.5, 0.2, 0.35, 0.2, 0.35, 0.2, 0.8, 0.65, 0.8, 0.65, 0.8 } };

  public PuzzleChooseStageWidget(int type, boolean advanced) {
    this.type = type;
    this.advanced = advanced;

    PuzzleStageItem exitItem = new PuzzleStageItem(-2, advanced, false);
    exitItem.setLogicalPosition(new MyPoint((int) (width() * xRates[type][0]),
        (int) (height() * yRates[type][0])));
    addItem(exitItem, AbstractSimpleWidget.ItemType.ButtonItem);

    PuzzleStageItem advancedItem = new PuzzleStageItem(-1, !advanced, false);
    advancedItem.setLogicalPosition(new MyPoint(
        (int) (width() * xRates[type][1]), (int) (height() * yRates[type][1])));
    addItem(advancedItem, AbstractSimpleWidget.ItemType.ButtonItem);

    stages = PuzzleInfo.totalStages(type);
    int[] minSteps = PuzzleInfo.minSteps(type, advanced);
    items = new PuzzleStageItem[stages];
    for (int i = 0; i < stages; ++i) {
      boolean canTry = i == 0;
      if (!canTry)
        canTry = minSteps[i - 1] > 0;
      items[i] = new PuzzleStageItem(i, advanced, !canTry);
      items[i].setLogicalPosition(new MyPoint(
          (int) (width() * xRates[type][i + 2]),
          (int) (height() * yRates[type][i + 2])));
      addItem(items[i], AbstractSimpleWidget.ItemType.ButtonItem);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.WidgetInterface#getFocus()
   */
  @Override
  public void getFocus() {
    int[] minSteps = PuzzleInfo.minSteps(type, advanced);
    for (int i = 0; i < stages; ++i) {
      boolean canTry = i == 0;
      if (!canTry)
        canTry = minSteps[i - 1] > 0;
      items[i].setLocked(!canTry);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.AbstractSimpleWidget#buttonClicked(int)
   */
  @Override
  public void buttonClicked(int indexOfTheButton) {
    switch (indexOfTheButton) {
    case Exit:
      mainWidget.changeControl(null, true);
      break;
    case Advance: {
      PuzzleChooseStageWidget widget = new PuzzleChooseStageWidget(type,
          !advanced);
      mainWidget.changeControl(widget, true);
    }
      break;
    default:
      if (indexOfTheButton < 2 + stages) {
        PuzzleGameWidget widget = new PuzzleGameWidget(type,
            indexOfTheButton - 2, advanced);
        mainWidget.changeControl(widget, false);
      }
      break;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.AbstractSimpleWidget#dragTo(int, java.awt.Point)
   */
  @Override
  public void dragTo(int indexOfTheDraggableItem, MyPoint position) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.AbstractSimpleWidget#dragApplied(int,
   * java.awt.Point)
   */
  @Override
  public void dragApplied(int indexOfTheDraggableItem, MyPoint position) {
  }

  @Override
  public void paint(MyGraphics g) {
    g.setColor(MyColor.black);
    g.fillRect(0, 0, width(), height());
    super.paint(g);
  }
}
