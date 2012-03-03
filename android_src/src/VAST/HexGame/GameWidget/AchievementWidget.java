package VAST.HexGame.GameWidget;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import Aid.MyGraphics;
import Aid.MyPoint;
import VAST.HexGame.Game.BasicPainter;
import VAST.HexGame.Game.Statistics;
import VAST.HexGame.GameItem.AchievementItem;
import VAST.HexGame.GameItem.StandardGameButtonItem;
import VAST.HexGame.Widgets.AbstractSimpleWidget;
import VAST.HexGame.Widgets.ItemInterface;

/**
 * Class to show all the achievements.
 * 
 * @author SidneyTTW
 * 
 */
public class AchievementWidget extends AbstractSimpleWidget {
  private static final int ExitButton = 0;
  public static final int ADVANCE_INTERVAL = 60;
  Vector<AchievementItem> achievemts;
  protected Timer advanceTimer = null;
  AchievementItem lastFocused;

  private static final int DESCRIPTION_X = 700;
  private static final int DESCRIPTION_Y = 275;
  private static final int DESCRIPTION_WIDTH = 550;
  private static final int DESCRIPTION_HEIGHT = 400;

  static final double[] xs = { 0.1, 0.3, 0.1, 0.3, 0.2 };
  static final double[] ys = { 0.2, 0.2, 0.8, 0.8, 0.5 };

  public AchievementWidget() {
    StandardGameButtonItem exitButton = new StandardGameButtonItem();
    exitButton.setText("Exit");
    exitButton.setLogicalPosition(new MyPoint((int) (width() * 0.7),
        (int) (height() * 0.9)));
    addItem(exitButton, AbstractSimpleWidget.ItemType.ButtonItem);

    achievemts = Statistics.getAchievementItems();

    for (int i = 0; i < achievemts.size(); ++i) {
      AchievementItem item = achievemts.elementAt(i);
      item.setLogicalPosition(new MyPoint((int) (width() * xs[i]),
          (int) (height() * ys[i])));
      item.getTabItem().setWidth(DESCRIPTION_WIDTH);
      item.getTabItem().setHeight(DESCRIPTION_HEIGHT);
      item.getTabItem().setLogicalPosition(
          new MyPoint(DESCRIPTION_X, DESCRIPTION_Y));
      addItem(item, AbstractSimpleWidget.ItemType.SimpleItem);
    }

    lastFocused = achievemts.firstElement();
    lastFocused.getFocus(frame);
  }

  /**
   * @author SidneyTTW
   * 
   * @brief The timer task to do the advance.
   */
  public class AdvanceTimerTask extends TimerTask {
    public void run() {
      advance();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.WidgetInterface#getFocus()
   */
  @Override
  public void getFocus() {
    if (advanceTimer == null) {
      AdvanceTimerTask task = new AdvanceTimerTask();
      advanceTimer = new Timer();
      advanceTimer.schedule(task, new Date(), ADVANCE_INTERVAL);
    }
  }

  @Override
  public void itemGetFocus(ItemInterface item) {
    if (item instanceof AchievementItem) {
      lastFocused = (AchievementItem) item;
      lastFocused.getFocus(frame);
    }
  }

  @Override
  public void paint(MyGraphics g) {
    BasicPainter.paintBackGround(BasicPainter.Achievement, g, width(),
        height(), frame);
    super.paint(g);
    lastFocused.paintTab(g, frame);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.AbstractSimpleWidget#buttonClicked(int)
   */
  @Override
  public void buttonClicked(int indexOfTheButton) {
    switch (indexOfTheButton) {
    case ExitButton:
      mainWidget.changeControl(null, true);
      return;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.AbstractSimpleWidget#dragTo(int,
   * AidPackage.MyPoint)
   */
  @Override
  public void dragTo(int indexOfTheDraggableItem, MyPoint position) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.AbstractSimpleWidget#dragApplied(int,
   * AidPackage.MyPoint)
   */
  @Override
  public void dragApplied(int indexOfTheDraggableItem, MyPoint position) {
  }

  public void advance() {
    lastFocused.advance();
  }
}
