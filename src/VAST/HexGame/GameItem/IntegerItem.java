/**
 * 
 */
package VAST.HexGame.GameItem;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import AidPackage.ImageAid;
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

  Font numberFont = new Font("Default", Font.PLAIN, 20);

  Font descriptionFont = new Font("Default", Font.PLAIN, 20);

  Color numberColor = new Color(115, 0, 0);

  Color descriptionColor = new Color(0, 0, 255);
  
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
  public Font getNumberFont() {
    return numberFont;
  }

  /**
   * @param numberFont
   *          the numberFont to set
   */
  public void setNumberFont(Font numberFont) {
    this.numberFont = numberFont;
  }

  /**
   * @return the descriptionFont
   */
  public Font getDescriptionFont() {
    return descriptionFont;
  }

  /**
   * @param descriptionFont
   *          the descriptionFont to set
   */
  public void setDescriptionFont(Font descriptionFont) {
    this.descriptionFont = descriptionFont;
  }

  /**
   * @return the numberColor
   */
  public Color getNumberColor() {
    return numberColor;
  }

  /**
   * @param numberColor
   *          the numberColor to set
   */
  public void setNumberColor(Color numberColor) {
    this.numberColor = numberColor;
  }

  /**
   * @return the descriptionColor
   */
  public Color getDescriptionColor() {
    return descriptionColor;
  }

  /**
   * @param descriptionColor
   *          the descriptionColor to set
   */
  public void setDescriptionColor(Color descriptionColor) {
    this.descriptionColor = descriptionColor;
  }

  @Override
  public void paint(Graphics g, int frame) {
    super.paint(g, frame);
    Point pos = (Point) position.clone();
    Font lastFont = g.getFont();
    String word = "" + number;
    pos.translate(0, numberYOffset);
    g.setFont(numberFont);
    ImageAid.drawText((Graphics2D) g, pos, word, rotation);
    pos.translate(0, -numberYOffset);
    pos.translate(0, descriptionYOffset);
    g.setFont(descriptionFont);
    ImageAid.drawText((Graphics2D) g, pos, description, rotation);
    g.setFont(lastFont);
  }
}
