/**
 * 
 */
package VAST.HexGame.GameItem;

import Aid.SourceManagement;
import VAST.HexGame.Widgets.RoundButtonItem;

/**
 * Class of items to choose stage.
 * 
 * @author SidneyTTW
 * 
 */
public class PuzzleStageItem extends RoundButtonItem {
  public PuzzleStageItem(int stage, boolean advanced, boolean locked) {
    setEnabled(!locked);
    setRadius(SourceManagement.PUZZLE_STAGE_IMAGE_RADIUS);
    setImageSeries(SourceManagement.puzzleStageFile(stage, advanced, locked));
  }
}
