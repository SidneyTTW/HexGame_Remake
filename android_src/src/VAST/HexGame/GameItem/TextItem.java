/**
 * 
 */
package VAST.HexGame.GameItem;

import Aid.ImageAid;
import Aid.MyColor;
import Aid.MyFont;
import Aid.MyGraphics;
import Aid.MyPoint;
import Aid.SourceManagement;
import VAST.HexGame.Widgets.RectItem;

/**
 * Class of an item to show a string.
 * 
 * @author SidneyTTW
 * 
 */
public class TextItem extends RectItem {
  String text = "";

  MyFont font = new MyFont("Default", MyFont.PLAIN, 20);

  MyColor color = new MyColor(115, 0, 0);

  int yOffset = -10;

  public TextItem(int preferredWidth) {
    int index;
    int width;
    if (preferredWidth <= SourceManagement.WordBackgroundWidth182) {
      width = SourceManagement.WordBackgroundWidth182;
      index = SourceManagement.WordBackground182;
    } else if (preferredWidth <= SourceManagement.WordBackgroundWidth280) {
      width = SourceManagement.WordBackgroundWidth280;
      index = SourceManagement.WordBackground280;
    } else {
      width = SourceManagement.WordBackgroundWidth316;
      index = SourceManagement.WordBackground316;
    }
    setWidth(width);
    setHeight(SourceManagement.WordBackgroundHeight);
    setImageSeries(SourceManagement.WordBackgroundFile[index]);
  }

  /**
   * @return the text
   */
  public String getText() {
    return text;
  }

  /**
   * @param text
   *          The text to set.
   */
  public void setText(String text) {
    this.text = text;
  }

  @Override
  public void paint(MyGraphics g, int frame) {
    super.paint(g, frame);
    MyPoint pos = position.clone();
    MyFont lastFont = g.getFont();
    pos.translate(0, yOffset);
    g.setFont(font);
    g.setColor(color);
    ImageAid.drawText(g, pos, text, rotation);
    pos.translate(0, -yOffset);
    g.setFont(lastFont);
  }
}
