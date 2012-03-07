package VAST.HexGame.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import Aid.FileProcessor;
import Aid.SourceManagement;
import VAST.HexGame.Game.Statistics;
import VAST.HexGame.GameWidget.SinglePlayerMainWidget;
import VAST.HexGame.Widgets.MainWidget;
import VAST.HexGame.main.R;

/**
 * @author SidneyTTW
 * 
 */
public class main extends Activity {
  MainWidget mainWidget;
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.main);

    mainWidget = (MainWidget) findViewById(R.id.mainWidget1);
    Statistics.setMainWidget(mainWidget);
    SourceManagement.r = getResources();
    FileProcessor.activity = this;
    SinglePlayerMainWidget test = new SinglePlayerMainWidget();
    mainWidget.changeControl(test, false);
  }

}
