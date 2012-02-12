/**
 * 
 */
package VAST.HexGame.Widgets;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * @author SidneyTTW
 *
 */
public class TestWidget extends AbstractSimpleWidget {
  
  Color color = null;
  private static java.util.Random r = new java.util.Random(); 
  
  public TestWidget(MainWidgetInterface mainWidget) {
    setMainWidget(mainWidget);
    this.color = new Color(r.nextInt());
    RectButtonItem button = new RectButtonItem();
    button.setImageSeries("j:/tmp/buttons", "buy_intro_norm.png");
    button.setPressedImageSeries("j:/tmp/buttons", "buy_intro_down.png");
    button.setLogicalPosition(new Point(width() / 2, height() / 2));
    button.setWidth(146);
    button.setHeight(41);
    addItem(button, AbstractSimpleWidget.ItemType.ButtonItem);
    
    RectItem item1 = new RectItem();
    item1.setImageSeries("j:/tmp/buttons", "buy_intro_norm.png");
    item1.setPressedImageSeries("j:/tmp/buttons", "buy_intro_down.png");
    item1.setLogicalPosition(new Point(width() / 4, height() / 4));
    item1.setWidth(146);
    item1.setHeight(41);
    addItem(item1, AbstractSimpleWidget.ItemType.SimpleItem);
    
    RectItem item2 = new RectItem();
    item2.setImageSeries("j:/tmp/buttons", "buy_intro_norm.png");
    item2.setPressedImageSeries("j:/tmp/buttons", "buy_intro_down.png");
    item2.setLogicalPosition(new Point(width() / 3, height() / 3));
    item2.setWidth(146);
    item2.setHeight(41);
    addItem(item2, AbstractSimpleWidget.ItemType.SimpleItem);
  }
  
  @Override
  public void paint(Graphics g) {
    g.setColor(color);
    g.fillRect(0, 0, width(), height());
    super.paint(g);
  }

  /**
   * @see VAST.HexGame.Widgets.WidgetInterface#getFocus()
   */
  @Override
  public void getFocus() {}

  /**
   * @see VAST.HexGame.Widgets.WidgetInterface#refreshInterval()
   */
  @Override
  public int refreshInterval() {
    return 30;
  }

  @Override
  public void buttonClicked(int indexOfTheButton) {
    TestWidget anotherOne = new TestWidget(getMainWidget());
    getMainWidget().changeControl(anotherOne, true);
  }

}
