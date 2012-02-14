import javax.swing.JFrame;

import VAST.HexGame.GameWidget.TestGameWidget;
import VAST.HexGame.Widgets.MainWidget;
import VAST.HexGame.Widgets.TestWidget;

/**
 * 
 */

/**
 * @author SidneyTTW
 *
 */
public class main {

  /**
   * @param args
   */
  public static void main(String[] args) {
    MainWidget mainWidget = new MainWidget();
//    TestWidget test = new TestWidget(mainWidget);
    TestGameWidget test = new TestGameWidget();
    mainWidget.changeControl(test, false);
    JFrame frame = new JFrame();
    frame.setSize(1024/2, 600/2);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(mainWidget);
    frame.setVisible(true);
  }
}
