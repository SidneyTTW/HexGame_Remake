/**
 * 
 */
package VAST.HexGame.GameWidget;

import java.awt.Graphics;
import java.awt.Point;

import VAST.HexGame.Aid.SourceManagement;
import VAST.HexGame.Game.BasicPainter;
import VAST.HexGame.GameItem.StandardGameButtonItem;
import VAST.HexGame.Widgets.AbstractSimpleWidget;
import VAST.HexGame.Widgets.RectButtonItem;

/**
 * Class to choose the type of puzzle
 * 
 * @author SidneyTTW
 * 
 */
public class PuzzleChooseTypeWidget extends AbstractSimpleWidget {

  protected static final int Exchange = 0;
  protected static final int Unite = 1;
  protected static final int Lock = 2;
  protected static final int Exit = 3;

  protected static final double[] xRates = { 0.2, 0.5, 0.8, 0.5 };
  protected static final double[] yRates = { 0.45, 0.45, 0.45, 0.8 };

  public PuzzleChooseTypeWidget() {
    RectButtonItem item;
    for (int i = 0; i < 3; ++i) {
      item = new RectButtonItem();
      item.setImageSeries(SourceManagement.PuzzleTypeFolder,
          SourceManagement.PuzzleTypeFile[i]);
      item.setLogicalPosition(new Point((int) (width() * xRates[i]),
          (int) (height() * yRates[i])));
      item.setWidth(SourceManagement.PUZZLE_TYPE_IMAGE_SIZE);
      item.setHeight(SourceManagement.PUZZLE_TYPE_IMAGE_SIZE);
      addItem(item, AbstractSimpleWidget.ItemType.ButtonItem);
    }
    
    item = new StandardGameButtonItem();
    item.setText("Exit");
    item.setLogicalPosition(new Point((int) (width() * xRates[3]),
        (int) (height() * yRates[3])));
    addItem(item, AbstractSimpleWidget.ItemType.ButtonItem);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.WidgetInterface#getFocus()
   */
  @Override
  public void getFocus() {}

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.AbstractSimpleWidget#buttonClicked(int)
   */
  @Override
  public void buttonClicked(int indexOfTheButton) {
    switch (indexOfTheButton) {
    case Exchange:
    case Unite:
    case Lock:
      PuzzleChooseStageWidget stageWidget = new PuzzleChooseStageWidget(indexOfTheButton, false);
      mainWidget.changeControl(stageWidget, false);
      break;
    case Exit:
      mainWidget.changeControl(null, true);
      break;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.AbstractSimpleWidget#dragTo(int, java.awt.Point)
   */
  @Override
  public void dragTo(int indexOfTheDraggableItem, Point position) {}

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.AbstractSimpleWidget#dragApplied(int,
   * java.awt.Point)
   */
  @Override
  public void dragApplied(int indexOfTheDraggableItem, Point position) {}
  
  @Override
  public void paint(Graphics g) {
    BasicPainter.paintBackGround(BasicPainter.PuzzleMenu, g, width(), height(), frame);
    super.paint(g);
  }

}
