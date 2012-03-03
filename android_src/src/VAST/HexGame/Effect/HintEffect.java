/**
 * 
 */
package VAST.HexGame.Effect;

import Aid.ImageAid;
import Aid.ImageMapper;
import Aid.MyGraphics;
import Aid.MyPoint;

/**
 * Class of an effect to show hints with given image mapper.
 * 
 * @author SidneyTTW
 * 
 */
public class HintEffect extends AbstractTimingEffect {
  /**
   * Constructor.
   */
  public HintEffect(ImageMapper imageMapper, int limit) {
    info = new HintEffectPrivateInfo(imageMapper, limit);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Effect.AbstractEffect#paint(java.awt.Graphics)
   */
  @Override
  public void paint(MyGraphics graphics) {
    HintEffectPrivateInfo myInfo = (HintEffectPrivateInfo) info;
    MyPoint position = myInfo.imageMapper.getPosition(myInfo.getAge());
    ImageAid.drawImageAt(graphics,
        myInfo.imageMapper.getImage(myInfo.getAge()), 1.0, 1.0, position,
        false, true);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Effect.AbstractEffect#effectId()
   */
  @Override
  public int effectId() {
    return IdHint;
  }

  /**
   * Class of information of hint effect.
   */
  public class HintEffectPrivateInfo extends AbstractTimingEffectPrivateInfo {
    /**
     * The image mapper.
     */
    private ImageMapper imageMapper;

    public HintEffectPrivateInfo(ImageMapper imageMapper, int limit) {
      this.imageMapper = imageMapper;
      setLimit(limit);
    }

    /**
     * Set the image mapper.
     * 
     * @param imageMapper
     *          The image mapper to set.
     */
    public void setImageMapper(ImageMapper imageMapper) {
      this.imageMapper = imageMapper;
    }

    /**
     * @return The image mapper.
     */
    public ImageMapper getImageMapper() {
      return imageMapper;
    }
  }
}
