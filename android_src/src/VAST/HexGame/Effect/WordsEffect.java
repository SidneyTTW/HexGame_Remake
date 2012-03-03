/**
 * 
 */
package VAST.HexGame.Effect;

import Aid.ImageAid;
import Aid.MyColor;
import Aid.MyFont;
import Aid.MyGraphics;
import Aid.MyPoint;

/**
 * Class of an effect to show words.
 * 
 * @author SidneyTTW
 * 
 */
public class WordsEffect extends AbstractTimingEffect {
  /**
   * Constructor.
   */
  public WordsEffect(MyPoint position, String string, int size, int limit) {
    this.info = new WordsEffectPrivateInfo(position, string, size, limit);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Effect.AbstractEffect#paint(java.awt.Graphics)
   */
  @Override
  public void paint(MyGraphics graphics) {
    WordsEffectPrivateInfo myInfo = (WordsEffectPrivateInfo) info;
    MyFont lastFont = graphics.getFont();
    graphics.setFont(new MyFont("Default", MyFont.BOLD, myInfo.size));
    graphics.setColor(new MyColor(255, 255, 255, 100 + 50 * myInfo.getAge()
        / myInfo.getLimit()));
    MyPoint center = new MyPoint(myInfo.position.x, myInfo.position.y
        - myInfo.getAge());
    ImageAid.drawText(graphics, center, myInfo.string);
    graphics.setFont(lastFont);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Effect.AbstractEffect#effectId()
   */
  @Override
  public int effectId() {
    return IdWords;
  }

  /**
   * Class of information of words effect.
   */
  public class WordsEffectPrivateInfo extends AbstractTimingEffectPrivateInfo {
    /**
     * The position.
     */
    private MyPoint position;

    /**
     * The string.
     */
    private String string;

    /**
     * The size.
     */
    private int size;

    public WordsEffectPrivateInfo(MyPoint position, String string, int size,
        int limit) {
      this.position = position;
      this.string = string;
      this.size = size;
      setLimit(limit);
    }

    /**
     * @return The position.
     */
    public MyPoint getPosition() {
      return position;
    }

    /**
     * @param position
     *          The position to set.
     */
    public void setPosition(MyPoint position) {
      this.position = position;
    }

    /**
     * @return the string.
     */
    public String getString() {
      return string;
    }

    /**
     * @param string
     *          The string to set.
     */
    public void setString(String string) {
      this.string = string;
    }
  }
}
