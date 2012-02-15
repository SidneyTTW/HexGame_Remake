import javax.swing.JFrame;

import VAST.HexGame.GameWidget.AbstractStandardGameWidget;
import VAST.HexGame.GameWidget.ClassicGameWidget;
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
    ClassicGameWidget test = new ClassicGameWidget(AbstractStandardGameWidget.StandardGesture.Swap);
    mainWidget.changeControl(test, false);
    JFrame frame = new JFrame();
    frame.setSize(1024, 600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(mainWidget);
    frame.setVisible(true);
  }
}
