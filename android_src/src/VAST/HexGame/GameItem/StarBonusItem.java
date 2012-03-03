/**
 * 
 */
package VAST.HexGame.GameItem;

import Aid.MyColor;
import Aid.SourceManagement;

/**
 * Class of a star item.
 * 
 * @author SidneyTTW
 * 
 */
public class StarBonusItem extends AbstractBonusItem {
  public StarBonusItem() {
    setImageSeries(SourceManagement.StarBonusFile);
    setPressedImageSeries(SourceManagement.StarBonusFile);
    setRadius(SourceManagement.StandardBonusRadious);
    setColor(MyColor.yellow);
  }
}
