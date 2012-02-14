/**
 * 
 */
package VAST.HexGame.Effect;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import AidPackage.ImageAid;

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
  public WordsEffect(Point position, String string, int size, int limit) {
    this.info = new WordsEffectPrivateInfo(position, string, size, limit);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Effect.AbstractEffect#paint(java.awt.Graphics)
   */
  @Override
  public void paint(Graphics graphics) {
    WordsEffectPrivateInfo myInfo = (WordsEffectPrivateInfo) info;
    Font lastFont = graphics.getFont();
    graphics.setFont(new Font("Default",Font.PLAIN, myInfo.size));
    graphics.setColor(new Color(255, 255, 255, 100 + 50 * myInfo.getAge()
        / myInfo.getLimit()));
    Point center = new Point(myInfo.position.x, myInfo.position.y
        - myInfo.getAge());
    ImageAid.drawText((Graphics2D) graphics, center, myInfo.string);
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
  public class WordsEffectPrivateInfo extends
      AbstractTimingEffectPrivateInfo {
    /**
     * The position.
     */
    private Point position;

    /**
     * The string.
     */
    private String string;
    
    /**
     * The size.
     */
    private int size;
    
    public WordsEffectPrivateInfo(Point position, String string, int size, int limit) {
      this.position = position;
      this.string = string;
      this.size = size;
      setLimit(limit);
    }

    /**
     * @return The position.
     */
    public Point getPosition() {
      return position;
    }

    /**
     * @param position
     *          The position to set.
     */
    public void setPosition(Point position) {
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
