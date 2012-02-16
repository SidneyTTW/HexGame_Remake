/**
 * 
 */
package VAST.HexGame.GameItem;

import java.awt.Color;
import java.awt.Point;

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
    setWordsOffset(new Point(0, 5));
    setColor(Color.blue);
  }
}
