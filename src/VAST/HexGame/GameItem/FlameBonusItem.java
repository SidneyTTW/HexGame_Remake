/**
 * 
 */
package VAST.HexGame.GameItem;

import AidPackage.MyColor;
import AidPackage.MyPoint;
import VAST.HexGame.Aid.SourceManagement;

/**
 * Class of a flame item.
 * 
 * @author SidneyTTW
 * 
 */
public class FlameBonusItem extends AbstractBonusItem {
  public FlameBonusItem() {
    setImageSeries(SourceManagement.BonusFolder,
        SourceManagement.FlameBonusFile);
    setPressedImageSeries(SourceManagement.BonusFolder,
        SourceManagement.FlameBonusFile);
    setRadius(SourceManagement.StandardBonusRadious);
    setWordsOffset(new MyPoint(0, 5));
    setColor(MyColor.blue);
  }
}
