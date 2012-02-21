/**
 * 
 */
package VAST.HexGame.GameItem;

import AidPackage.ImageAid;
import AidPackage.MyColor;
import AidPackage.MyFont;
import AidPackage.MyGraphics;
import AidPackage.MyPoint;
import VAST.HexGame.Aid.SourceManagement;
import VAST.HexGame.Widgets.RectItem;

/**
 * Class of an item to show an integer and a string to describe it.
 * 
 * @author SidneyTTW
 * 
 */
public class IntegerItem extends RectItem {
  int number = 0;

  String description = "";

  MyFont numberFont = new MyFont("Default", MyFont.PLAIN, 20);

  MyFont descriptionFont = new MyFont("Default", MyFont.PLAIN, 20);

  MyColor numberColor = new MyColor(115, 0, 0);

  MyColor descriptionColor = new MyColor(0, 0, 255);

  int numberYOffset = -10;

  int descriptionYOffset = 15;

  public IntegerItem(int preferredWidth) {
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
    setImageSeries(SourceManagement.WordBackgroundFolder,
        SourceManagement.WordBackgroundFile[index]);
  }

  /**
   * @return the number
   */
  public int getNumber() {
    return number;
  }

  /**
   * @param number
   *          the number to set
   */
  public void setNumber(int number) {
    this.number = number;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description
   *          the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @return the numberFont
   */
  public MyFont getNumberFont() {
    return numberFont;
  }

  /**
   * @param numberFont
   *          the numberFont to set
   */
  public void setNumberFont(MyFont numberFont) {
    this.numberFont = numberFont;
  }

  /**
   * @return the descriptionFont
   */
  public MyFont getDescriptionFont() {
    return descriptionFont;
  }

  /**
   * @param descriptionFont
   *          the descriptionFont to set
   */
  public void setDescriptionFont(MyFont descriptionFont) {
    this.descriptionFont = descriptionFont;
  }

  /**
   * @return the numberColor
   */
  public MyColor getNumberColor() {
    return numberColor;
  }

  /**
   * @param numberColor
   *          the numberColor to set
   */
  public void setNumberColor(MyColor numberColor) {
    this.numberColor = numberColor;
  }

  /**
   * @return the descriptionColor
   */
  public MyColor getDescriptionColor() {
    return descriptionColor;
  }

  /**
   * @param descriptionColor
   *          the descriptionColor to set
   */
  public void setDescriptionColor(MyColor descriptionColor) {
    this.descriptionColor = descriptionColor;
  }

  @Override
  public void paint(MyGraphics g, int frame) {
    super.paint(g, frame);
    MyPoint pos = position.clone();
    MyFont lastFont = g.getFont();
    String word = "" + number;
    pos.translate(0, numberYOffset);
    g.setFont(numberFont);
    g.setColor(numberColor);
    ImageAid.drawText(g, pos, word, rotation);
    pos.translate(0, -numberYOffset);
    pos.translate(0, descriptionYOffset);
    g.setFont(descriptionFont);
    g.setColor(descriptionColor);
    ImageAid.drawText(g, pos, description, rotation);
    g.setFont(lastFont);
  }
}
