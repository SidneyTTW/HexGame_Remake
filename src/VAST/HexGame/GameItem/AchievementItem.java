/**
 * 
 */
package VAST.HexGame.GameItem;

import AidPackage.MyComposite;
import AidPackage.MyGraphics;
import VAST.HexGame.Widgets.RectItem;
import VAST.HexGame.Widgets.RoundItem;

/**
 * Class of achievement items.
 * 
 * @author SidneyTTW
 * 
 */
public class AchievementItem extends RoundItem implements TabInterface {
  public static final int ANIM_COUNT = 10;

  int lastFrame = -1;
  RectItem tabItem = null;

  /**
   * @param tabItem
   *          The tab item to set.
   */
  public void setTabItem(RectItem tabItem) {
    this.tabItem = tabItem;
  }

  @Override
  public void press() {
    super.press();
    position.translate(5, 5);
  }

  @Override
  public void release() {
    super.release();
    position.translate(-5, -5);
  }

  @Override
  public void getFocus(int currentFrame) {
    lastFrame = currentFrame;
  }

  @Override
  public void loseFocus() {
    lastFrame = -1;
  }

  @Override
  public void advance() {
    // Rotate if is selected
    if (lastFrame >= 0)
      rotation += 0.2;
  }

  @Override
  public void paintTab(MyGraphics graphics, int frame) {
    if (tabItem == null)
      return;
    float alphaFactor = 1.0f * (frame - lastFrame) / ANIM_COUNT;
    if (alphaFactor < 0)
      alphaFactor = 0;
    if (alphaFactor > 1)
      alphaFactor = 1;
    boolean changeAlpha = alphaFactor < 1;
    if (changeAlpha) {
      MyComposite lastComposite = graphics.getComposite();
      float lastAlpha = 1.0f;
      if (lastComposite != null)
        lastAlpha = lastComposite.getAlpha();
      MyComposite currentComposite = MyComposite.getInstance(
          MyComposite.SRC_ATOP, lastAlpha * alphaFactor);
      graphics.setComposite(currentComposite);
      tabItem.paint(graphics, frame);
      graphics.setComposite(lastComposite);
    } else
      tabItem.paint(graphics, frame);
  }

}
