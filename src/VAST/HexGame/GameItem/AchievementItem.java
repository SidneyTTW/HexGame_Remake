/**
 * 
 */
package VAST.HexGame.GameItem;

import java.awt.AlphaComposite;
import java.awt.Composite;
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
  public void paintTab(Graphics graphics, int frame) {
    if (tabItem == null)
      return;
    float alphaFactor = 1.0f * (frame - lastFrame) / ANIM_COUNT;
    if (alphaFactor < 0)
      alphaFactor = 0;
    if (alphaFactor > 1)
      alphaFactor = 1;
    boolean changeAlpha = alphaFactor < 1;
    if (changeAlpha) {
      Graphics2D g2d = (Graphics2D) graphics;
      Composite lastComposite = g2d.getComposite();
      float lastAlpha = 1.0f;
      if (lastComposite instanceof AlphaComposite)
        lastAlpha = ((AlphaComposite) lastComposite).getAlpha();
      AlphaComposite currentComposite = AlphaComposite.getInstance(
          AlphaComposite.SRC_ATOP, lastAlpha * alphaFactor);
      g2d.setComposite(currentComposite);
      tabItem.paint(g2d, frame);
      g2d.setComposite(lastComposite);
    } else
      tabItem.paint(graphics, frame);
  }

}
