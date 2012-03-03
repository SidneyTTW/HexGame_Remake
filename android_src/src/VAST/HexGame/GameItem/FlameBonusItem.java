/**
 * 
 */
package VAST.HexGame.GameItem;

import Aid.MyColor;
import Aid.MyPoint;
import Aid.SourceManagement;

/**
 * Class of a flame item.
 * 
 * @author SidneyTTW
 * 
 */
public class FlameBonusItem extends AbstractBonusItem {
  public FlameBonusItem() {
    setImageSeries(SourceManagement.FlameBonusFile);
    setPressedImageSeries(SourceManagement.FlameBonusFile);
    setRadius(SourceManagement.StandardBonusRadious);
    setWordsOffset(new MyPoint(0, 5));
    setColor(MyColor.blue);
  }
}
