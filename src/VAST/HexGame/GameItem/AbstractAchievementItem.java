/**
 * 
 */
package VAST.HexGame.GameItem;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import VAST.HexGame.Widgets.RectItem;
import VAST.HexGame.Widgets.RoundItem;

/**
 * Class of achievement items.
 * 
 * @author SidneyTTW
 *
 */
public class AbstractAchievementItem extends RoundItem implements TabInterface {
  public static final int ANIM_COUNT = 10;
  
  int lastFrame = -1;
  RectItem tabItem = null;
  
  /**
   * @param tabItem The tab item to set.
   */
  public void setTabItem(RectItem tabItem) {
    this.tabItem = tabItem;
  }

  @Override
  public void getFocus(int currentFrame) {
    lastFrame = currentFrame;
  }

  @Override
  public void loseFocus() {}

  @Override
  public void paintTab(Graphics graphics, int frame) {
    if (tabItem == null)
      return;
    Graphics2D g2d = (Graphics2D) graphics;
    AlphaComposite lastComposite = (AlphaComposite) g2d.getComposite();
    float lastAlpha = lastComposite.getAlpha();
    float alphaFactor = 1.0f * (frame - lastFrame) / ANIM_COUNT;
    if (alphaFactor < 0)
      alphaFactor = 0;
    if (alphaFactor > 1)
      alphaFactor = 1;
    AlphaComposite currentComposite = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, lastAlpha * alphaFactor);
    g2d.setComposite(currentComposite);
    tabItem.paint(graphics, frame);
    g2d.setComposite(lastComposite);
  }

}
