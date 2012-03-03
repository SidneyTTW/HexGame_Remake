package VAST.HexGame.GameItem;

import java.util.Vector;

import VAST.HexGame.Widgets.RectItem;

import Aid.ImageAid;
import Aid.MyColor;
import Aid.MyFont;
import Aid.MyGraphics;
import Aid.MyPoint;

/**
 * Class to paint words into a rectangle area.
 * 
 * @author SidneyTTW
 * 
 */
public class RectWordItem extends RectItem {
  Vector<String> words = new Vector<String>();

  int size = 50;
  int dy = 70;

  public void addWord(String word) {
    words.add(word);
  }

  @Override
  public void paint(MyGraphics g, int frame) {
    super.paint(g, frame);

    g.translate(position.x, position.y);
    g.rotate(rotation);

    MyFont lastFont = g.getFont();
    g.setColor(MyColor.black);
    g.setFont(new MyFont("Default", MyFont.BOLD, size));

    for (int i = 0; i < words.size(); ++i) {
      MyPoint center = new MyPoint(0, dy * (2 * i + 1 - words.size()) / 2);
      ImageAid.drawText(g, center, words.elementAt(i));
    }

    g.setFont(lastFont);

    g.drawRoundRect(-width / 2, -height / 2, width, height, 30, 30);

    g.rotate(-rotation);
    g.translate(-position.x, -position.y);
  }
}
