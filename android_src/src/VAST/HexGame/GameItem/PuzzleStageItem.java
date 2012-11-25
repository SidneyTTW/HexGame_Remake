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
  int stage;
  boolean advanced;

  public PuzzleStageItem(int stage, boolean advanced, boolean locked) {
    this.stage = stage;
    this.advanced = advanced;

    setEnabled(!locked);
    setRadius(SourceManagement.PUZZLE_STAGE_IMAGE_RADIUS);
    setImageSeries(SourceManagement.puzzleStageFile(stage, advanced, locked));
  }

  public void setLocked(boolean locked) {
    recycle();
    setEnabled(!locked);
    pressedImages = null;
    setImageSeries(SourceManagement.puzzleStageFile(stage, advanced, locked));
  }
}
