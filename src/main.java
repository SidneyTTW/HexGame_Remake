import javax.swing.JFrame;

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
    TestWidget test = new TestWidget(mainWidget);
    mainWidget.changeControl(test, false);
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(mainWidget);
    frame.setVisible(true);
  }

}
