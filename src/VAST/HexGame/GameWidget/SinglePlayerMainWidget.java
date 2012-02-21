/**
 * 
 */
package VAST.HexGame.GameWidget;

import AidPackage.MyGraphics;
import AidPackage.MyPoint;
import VAST.HexGame.Aid.SourceManagement;
import VAST.HexGame.Game.BasicPainter;
import VAST.HexGame.GameItem.StandardGameButtonItem;
import VAST.HexGame.Widgets.AbstractSimpleWidget;
import VAST.HexGame.Widgets.RoundItem;
import VAST.HexGame.Widgets.WidgetInterface;

/**
 * Class of main widget of single player.
 * 
 * @author SidneyTTW
 * 
 */
public class SinglePlayerMainWidget extends AbstractSimpleWidget {
  public static final int AchievementButton = 0;
  public static final int HelpButton = 1;
  public static final int ExitButton = 2;
  public static final int SwapClassicButton = 3;
  public static final int RotateClassicButton = 4;
  public static final int SwapEndlessButton = 5;
  public static final int RotateEndlessButton = 6;
  public static final int SwapTimingButton = 7;
  public static final int RotateTimingButton = 8;
  public static final int PuzzleButton = 9;

  public SinglePlayerMainWidget() {
    StandardGameButtonItem achivementButton = new StandardGameButtonItem();
    achivementButton.setText("Achivement");
    achivementButton.setLogicalPosition(new MyPoint((int) (width() * 0.1),
        (int) (height() * 0.55)));
    addItem(achivementButton, AbstractSimpleWidget.ItemType.ButtonItem);

    StandardGameButtonItem helpButton = new StandardGameButtonItem();
    helpButton.setText("Help");
    helpButton.setLogicalPosition(new MyPoint((int) (width() * 0.1),
        (int) (height() * 0.65)));
    addItem(helpButton, AbstractSimpleWidget.ItemType.ButtonItem);

    StandardGameButtonItem exitButton = new StandardGameButtonItem();
    exitButton.setText("Exit");
    exitButton.setLogicalPosition(new MyPoint((int) (width() * 0.1),
        (int) (height() * 0.8)));
    addItem(exitButton, AbstractSimpleWidget.ItemType.ButtonItem);

    RoundItem item = new RoundItem();
    item.setRadius(SourceManagement.MAIN_MENU_ITEM_RADIOS);
    item.setLogicalPosition(new MyPoint((int) (width() * 0.375),
        (int) (height() * 0.5)));
    addItem(item, AbstractSimpleWidget.ItemType.ButtonItem);

    item = new RoundItem();
    item.setRadius(SourceManagement.MAIN_MENU_ITEM_RADIOS);
    item.setLogicalPosition(new MyPoint((int) (width() * 0.835),
        (int) (height() * 0.5)));
    addItem(item, AbstractSimpleWidget.ItemType.ButtonItem);

    item = new RoundItem();
    item.setRadius(SourceManagement.MAIN_MENU_ITEM_RADIOS);
    item.setLogicalPosition(new MyPoint((int) (width() * 0.49),
        (int) (height() * 0.16)));
    addItem(item, AbstractSimpleWidget.ItemType.ButtonItem);

    item = new RoundItem();
    item.setRadius(SourceManagement.MAIN_MENU_ITEM_RADIOS);
    item.setLogicalPosition(new MyPoint((int) (width() * 0.72),
        (int) (height() * 0.84)));
    addItem(item, AbstractSimpleWidget.ItemType.ButtonItem);

    item = new RoundItem();
    item.setRadius(SourceManagement.MAIN_MENU_ITEM_RADIOS);
    item.setLogicalPosition(new MyPoint((int) (width() * 0.72),
        (int) (height() * 0.16)));
    addItem(item, AbstractSimpleWidget.ItemType.ButtonItem);

    item = new RoundItem();
    item.setRadius(SourceManagement.MAIN_MENU_ITEM_RADIOS);
    item.setLogicalPosition(new MyPoint((int) (width() * 0.49),
        (int) (height() * 0.84)));
    addItem(item, AbstractSimpleWidget.ItemType.ButtonItem);

    item = new RoundItem();
    item.setRadius(SourceManagement.MAIN_MENU_ITEM_RADIOS);
    item.setLogicalPosition(new MyPoint((int) (width() * 0.605),
        (int) (height() * 0.5)));
    addItem(item, AbstractSimpleWidget.ItemType.ButtonItem);
    
    item = new RoundItem();
    item.setLogicalPosition(new MyPoint((int) (width() * 0.605),
        (int) (height() * 0.5)));
    item.setImageSeries(SourceManagement.MainMenuItemFolder, SourceManagement.MainMenuGameButtonsItemFile);
    addItem(item, AbstractSimpleWidget.ItemType.SimpleItem);
    
    item = new RoundItem();
    item.setLogicalPosition(new MyPoint((int) (width() * 0.6),
        (int) (height() * 0.495)));
    item.setImageSeries(SourceManagement.MainMenuItemFolder, SourceManagement.MainMenuCircleItemFile);
    addItem(item, AbstractSimpleWidget.ItemType.SimpleItem);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.WidgetInterface#getFocus()
   */
  @Override
  public void getFocus() {
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.AbstractSimpleWidget#buttonClicked(int)
   */
  @Override
  public void buttonClicked(int indexOfTheButton) {
    WidgetInterface widget = null;
    boolean pop = false;
    switch (indexOfTheButton) {
    case AchievementButton:
      break;
    case HelpButton:
      break;
    case ExitButton:
      pop = true;
      break;
    case SwapClassicButton:
      widget = new ClassicGameWidget(
          AbstractStandardGameWidget.StandardGesture.Swap);
      break;
    case RotateClassicButton:
      widget = new ClassicGameWidget(
          AbstractStandardGameWidget.StandardGesture.Rotate);
      break;
    case SwapEndlessButton:
      break;
    case RotateEndlessButton:
      break;
    case SwapTimingButton:
      widget = new TimingGameWidget(
          AbstractStandardGameWidget.StandardGesture.Swap);
      break;
    case RotateTimingButton:
      widget = new TimingGameWidget(
          AbstractStandardGameWidget.StandardGesture.Rotate);
      break;
    case PuzzleButton:
      break;
    }
    mainWidget.changeControl(widget, pop);
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
    BasicPainter
        .paintBackGround(BasicPainter.MainMenu, g, width(), height(), 0);
    super.paint(g);
  }

}
