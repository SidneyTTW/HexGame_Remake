/**
 * 
 */
package VAST.HexGame.Effect;


import AidPackage.MyColor;
import AidPackage.MyGraphics;
import AidPackage.MyRectangle;

/**
 * 
 * 
 * @author SidneyTTW
 * 
 */
public class FlashEffect extends AbstractTimingEffect {
  public static final int FlashLimit = 12;

  /**
   * Constructor.
   */
  public FlashEffect(MyRectangle area) {
    info = new FlashEffectPrivateInfo(area, FlashLimit);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Effect.AbstractEffect#paint(java.awt.Graphics)
   */
  @Override
  public void paint(MyGraphics graphics) {
    FlashEffectPrivateInfo myInfo = (FlashEffectPrivateInfo) info;
    graphics.setColor(new MyColor(255, 255, 255, myInfo.getAge() % 4 * 30));
    graphics.fillRect(myInfo.area.x, myInfo.area.y, myInfo.area.width,
        myInfo.area.height);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Effect.AbstractEffect#effectId()
   */
  @Override
  public int effectId() {
    return IdFlash;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Effect.AbstractEffect#oneAtATime()
   */
  @Override
  public boolean oneAtATime() {
    return true;
  }

  /**
   * Class of information of flash effect.
   */
  public class FlashEffectPrivateInfo extends
      AbstractTimingEffectPrivateInfo {
    /**
     * The area of the flash
     */
    private MyRectangle area;
    
    public FlashEffectPrivateInfo(MyRectangle area, int limit) {
      this.area = area;
      setLimit(limit);
    }

    /**
     * @return The area.
     */
    public MyRectangle getArea() {
      return area;
    }

    /**
     * @param area
     *          The area to set.
     */
    public void setArea(MyRectangle area) {
      this.area = area;
    }
  }
}
