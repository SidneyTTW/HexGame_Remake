/**
 * 
 */
package VAST.HexGame.GameItem;

import AidPackage.MyColor;
import VAST.HexGame.Aid.SourceManagement;

/**
 * Class of a star item.
 * 
 * @author SidneyTTW
 * 
 */
public class StarBonusItem extends AbstractBonusItem {
  public StarBonusItem() {
    setImageSeries(SourceManagement.BonusFolder, SourceManagement.StarBonusFile);
    setPressedImageSeries(SourceManagement.BonusFolder,
        SourceManagement.StarBonusFile);
    setRadius(SourceManagement.StandardBonusRadious);
    setColor(MyColor.yellow);
  }
}
