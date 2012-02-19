import javax.swing.JFrame;

import VAST.HexGame.Game.Statistics;
import VAST.HexGame.GameWidget.*;
import VAST.HexGame.Widgets.MainWidget;

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
    //TestWidget test = new TestWidget(mainWidget);
    Statistics.setMainWidget(mainWidget);
    //ClassicGameWidget test = new ClassicGameWidget(AbstractStandardGameWidget.StandardGesture.Swap);
    SinglePlayerMainWidget test = new SinglePlayerMainWidget();
    mainWidget.changeControl(test, false);
    JFrame frame = new JFrame();
    frame.setSize(1024, 600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(mainWidget);
    frame.setVisible(true);
  }
}
